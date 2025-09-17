package de.hwg_lu.bwi520.beans;

import de.hwg_lu.bwi520.jdbc.PostgreSQLAccess;
import de.hwg_lu.bwi520.jdbc.NoConnectionException;
import java.sql.*;
import java.util.*;

public class SmartOfficeBean {
  private String buildingId, floorId, roomId;

  public void setBuildingId(String s){ this.buildingId = s; }
  public void setFloorId(String s){ this.floorId = s; }
  public void setRoomId(String s){ this.roomId = s; }

  //erzeugt eine HTML-Liste aller Gebäude aus der Datenbank.
  public String getBuildingsHtml() throws SQLException, NoConnectionException {
    String sql = "SELECT id, name FROM building ORDER BY id";
    StringBuilder sb = new StringBuilder("<ul>");
    try (Connection c = new PostgreSQLAccess().getConnection();
         Statement st = c.createStatement();
         ResultSet rs = st.executeQuery(sql)) {
      while (rs.next()) {
        sb.append("<li><a href='./SmartOfficeAppl.jsp?show=floors&buildingId=")
          .append(rs.getInt(1)).append("'>").append(rs.getString(2)).append("</a></li>");
      }
    }
    return sb.append("</ul>").toString();
  }

  public String getFloorsHtml() throws SQLException, NoConnectionException {
    String sql = "SELECT id, name FROM floor WHERE building_id=? ORDER BY index_no";
    StringBuilder sb = new StringBuilder("<ul>");
    try (Connection c = new PostgreSQLAccess().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, Integer.parseInt(buildingId));
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          sb.append("<li><a href='./SmartOfficeAppl.jsp?show=rooms&buildingId=")
            .append(buildingId).append("&floorId=").append(rs.getInt(1)).append("'>")
            .append(rs.getString(2)).append("</a></li>");
        }
      }
    }
    return sb.append("</ul>").toString();
  }

  public String getRoomsHtml() throws SQLException, NoConnectionException {
    String sql = "SELECT id, code, name FROM room WHERE floor_id=? ORDER BY id";
    StringBuilder sb = new StringBuilder("<ul>");
    try (Connection c = new PostgreSQLAccess().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, Integer.parseInt(floorId));
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          sb.append("<li><a href='./SmartOfficeAppl.jsp?show=sensors&buildingId=")
            .append(buildingId).append("&floorId=").append(floorId)
            .append("&roomId=").append(rs.getInt(1)).append("'>")
            .append(rs.getString(2)).append(" — ").append(rs.getString(3)).append("</a></li>");
        }
      }
    }
    return sb.append("</ul>").toString();
  }

  public String getSensorsTableHtml() throws SQLException, NoConnectionException {
    String sql =
      "SELECT s.id, s.label, st.key, st.unit, st.writable, " +
      "       COALESCE(sv.value_numeric::text, sv.value_text) AS val, " +
      "       to_char(sv.ts,'YYYY-MM-DD HH24:MI:SS') AS ts " +
      "FROM sensor s " +
      "JOIN sensor_type st ON st.id = s.type_id " +
      "LEFT JOIN LATERAL ( " +
      "   SELECT value_numeric, value_text, ts FROM sensor_value v " +
      "   WHERE v.sensor_id = s.id ORDER BY ts DESC LIMIT 1 " +
      ") sv ON TRUE " +
      "WHERE s.room_id = ? ORDER BY s.id";

    StringBuilder sb = new StringBuilder();
    sb.append("<a href='./SmartOfficeAppl.jsp?show=rooms&buildingId=")
      .append(buildingId).append("&floorId=").append(floorId)
      .append("'>&laquo; zurück</a>");
    sb.append("<table class='tbl'><tr><th>Label</th><th>Typ</th><th>Wert</th><th>Zeit</th><th>Aktion</th></tr>");

    try (Connection c = new PostgreSQLAccess().getConnection();
         PreparedStatement ps = c.prepareStatement(sql)) {
      ps.setInt(1, Integer.parseInt(roomId));
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          int id = rs.getInt("id");
          String key = rs.getString("key");
          boolean wr = rs.getBoolean("writable");
          String unit = rs.getString("unit"); unit = (unit == null) ? "" : (" " + unit);
          String val = rs.getString("val");
          String ts  = rs.getString("ts");

          sb.append("<tr><td>").append(rs.getString("label")).append("</td>")
            .append("<td>").append(key).append(unit).append(wr ? " [steuerbar]" : "").append("</td>")
            .append("<td>").append(val == null ? "—" : val).append("</td>")
            .append("<td>").append(ts  == null ? "—" : ts ).append("</td>")
            .append("<td>");

          if (wr) {
            sb.append("<form method='get' action='./SmartOfficeAppl.jsp'>")
              .append("<input type='hidden' name='show' value='set'/>")
              .append("<input type='hidden' name='buildingId' value='").append(buildingId).append("'/>")
              .append("<input type='hidden' name='floorId' value='").append(floorId).append("'/>")
              .append("<input type='hidden' name='roomId' value='").append(roomId).append("'/>")
              .append("<input type='hidden' name='sensorId' value='").append(id).append("'/>");

            if ("light".equals(key) || "blind_position".equals(key)) {
              sb.append("<input type='range' name='desiredValue' min='0' max='100' step='1'/>");
            } else if ("temperature_setpoint".equals(key)) {
              sb.append("<input type='number' name='desiredValue' step='0.1' value='21.0'/>");
            } else if ("plug_switch".equals(key)) {
              sb.append("<input type='hidden' name='desiredValue' value='0'/>")
                .append("<label><input type='checkbox' name='desiredValue' value='1'/> an/aus</label>");
            } else {
              sb.append("<input type='number' name='desiredValue' step='0.1'/>");
            }
            sb.append("<button type='submit' name='btnSetzen' value='setzen'>Setzen</button></form>");
          }
          sb.append("</td></tr>");
        }
      }
    }
    return sb.append("</table>").toString();
  }

  public void setValue(String sensorId, String raw)
      throws SQLException, NoConnectionException {
    double desired = 0.0;
    if (raw != null && !raw.isBlank()) {
      try { desired = Double.parseDouble(raw.replace(',', '.')); } catch (NumberFormatException ignore) {}
    }
    try (Connection c = new PostgreSQLAccess().getConnection()) {
      try (PreparedStatement ps = c.prepareStatement(
             "INSERT INTO sensor_value(sensor_id, value_numeric, ts) VALUES (?,?, now())")) {
        ps.setInt(1, Integer.parseInt(sensorId));
        ps.setDouble(2, desired);
        ps.executeUpdate();
      }
      try (PreparedStatement ps = c.prepareStatement(
             "INSERT INTO control_request(sensor_id, requested_value) VALUES (?,?)")) {
        ps.setInt(1, Integer.parseInt(sensorId));
        ps.setDouble(2, desired);
        ps.executeUpdate();
      }
    }
  }
}
