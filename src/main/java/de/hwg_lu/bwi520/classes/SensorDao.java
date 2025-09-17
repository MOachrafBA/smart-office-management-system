package de.hwg_lu.bwi520.classes;
import de.hwg_lu.bwi520.beans.SensorRow; import de.hwg_lu.bwi520.jdbc.Db;
import java.sql.*; import java.util.*;
public class SensorDao {
  public List<SensorRow> byRoom(int roomId) throws SQLException {
    String sql = """
      SELECT s.id, s.label, st.key, st.unit, st.writable,
             COALESCE(sv.value_numeric::text, sv.value_text) AS val,
             to_char(sv.ts,'YYYY-MM-DD HH24:MI:SS') AS ts
      FROM sensor s
      JOIN sensor_type st ON st.id = s.type_id
      LEFT JOIN LATERAL (
        SELECT value_numeric, value_text, ts
        FROM sensor_value v WHERE v.sensor_id = s.id
        ORDER BY ts DESC LIMIT 1
      ) sv ON TRUE
      WHERE s.room_id = ?
      ORDER BY s.id
    """;
    try (PreparedStatement ps=Db.get().prepareStatement(sql)) {
      ps.setInt(1, roomId); ResultSet rs=ps.executeQuery();
      List<SensorRow> list=new ArrayList<>();
      while (rs.next()) {
        list.add(new SensorRow(
          rs.getInt("id"), rs.getString("label"), rs.getString("key"),
          rs.getString("unit"), rs.getBoolean("writable"),
          rs.getString("val"), rs.getString("ts")));
      }
      return list;
    }
  }
  public boolean isWritable(int sensorId) throws SQLException {
    String sql="SELECT st.writable FROM sensor s JOIN sensor_type st ON st.id=s.type_id WHERE s.id=?";
    try (PreparedStatement ps=Db.get().prepareStatement(sql)) {
      ps.setInt(1,sensorId); ResultSet rs=ps.executeQuery();
      return rs.next() && rs.getBoolean(1);
    }
  }
  public void insertValue(int sensorId,double value) throws SQLException {
    String sql="INSERT INTO sensor_value(sensor_id,value_numeric) VALUES (?,?)";
    try (PreparedStatement ps=Db.get().prepareStatement(sql)) {
      ps.setInt(1,sensorId); ps.setDouble(2,value); ps.executeUpdate();
    }
  }
}
