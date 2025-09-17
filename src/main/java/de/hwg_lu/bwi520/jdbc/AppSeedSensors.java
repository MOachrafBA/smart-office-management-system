package de.hwg_lu.bwi520.jdbc;

import java.sql.*;
import java.util.Collections;

public class AppSeedSensors {

  Connection dbConn;

  public static void main(String[] args) throws Exception {
    AppSeedSensors app = new AppSeedSensors();
    app.dbConn = new PostgreSQLAccess().getConnection();

    // Beispiele:
    // 1) Nur einen Raum per ID befüllen
    // app.seedRoom(10);

    // 2) Räume per Code (z. B. 1-CR2, 1-F1)
    app.seedByRoomCode("1-CR2", "1-F1");

    // 3) Alle Räume einer Etage
    // app.seedByFloor(3);

    // 4) Alle Räume eines Gebäudes
    // app.seedByBuilding(1);

    // 5) Alle Räume, die noch gar keine Sensoren haben
    // app.seedAllRoomsWithoutSensors();

    System.out.println("FERTIG.");
  }

  /* ========== PUBLIC APIS ========== */

  /** Legt Standardsensoren + Initialwerte für EINEN Raum an. */
  public void seedRoom(int roomId) throws SQLException {
    insertSensorsForRoom(roomId);
    insertInitialValuesForRoom(roomId);
  }

  /** Räume anhand ihrer Codes (z. B. "1-CR2") befüllen. */
  public void seedByRoomCode(String... codes) throws SQLException {
    if (codes == null || codes.length == 0) return;
    String placeholders = String.join(",", Collections.nCopies(codes.length, "?"));
    String sql = "SELECT id FROM room WHERE code IN (" + placeholders + ") ORDER BY id";
    try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
      for (int i = 0; i < codes.length; i++) ps.setString(i + 1, codes[i]);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) seedRoom(rs.getInt(1));
      }
    }
  }

  /** Alle Räume einer Etage befüllen. */
  public void seedByFloor(int floorId) throws SQLException {
    try (PreparedStatement ps = dbConn.prepareStatement(
            "SELECT id FROM room WHERE floor_id=? ORDER BY id")) {
      ps.setInt(1, floorId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) seedRoom(rs.getInt(1));
      }
    }
  }

  /** Alle Räume eines Gebäudes befüllen. */
  public void seedByBuilding(int buildingId) throws SQLException {
    String sql = """
      SELECT r.id
      FROM room r
      JOIN floor f ON f.id = r.floor_id
      WHERE f.building_id = ?
      ORDER BY r.id
    """;
    try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
      ps.setInt(1, buildingId);
      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) seedRoom(rs.getInt(1));
      }
    }
  }

  /** Alle Räume, die aktuell keine Sensoren haben, befüllen. */
  public void seedAllRoomsWithoutSensors() throws SQLException {
    String sql = """
      SELECT r.id
      FROM room r
      LEFT JOIN sensor s ON s.room_id = r.id
      GROUP BY r.id
      HAVING COUNT(s.id) = 0
    """;
    try (PreparedStatement ps = dbConn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) seedRoom(rs.getInt(1));
    }
  }

  /** Gehe alle Räume durch und füge fehlende Sensoren + Startwerte hinzu. */
  public void seedAllRooms() throws SQLException {
    try (PreparedStatement ps = dbConn.prepareStatement("SELECT id FROM room ORDER BY id");
         ResultSet rs = ps.executeQuery()) {
      while (rs.next()) seedRoom(rs.getInt(1));
    }
  }

  /* ========== INTERNAL HELPERS ========== */

  /** Sensoren (falls noch nicht vorhanden) für einen Raum anlegen. */
  private void insertSensorsForRoom(int roomId) throws SQLException {
    String sql =
      "INSERT INTO sensor (room_id, type_id, label)\n" +
      "SELECT ?, st.id,\n" +
      "       CASE st.key\n" +
      "         WHEN 'temperature'          THEN 'Raumtemp'\n" +
      "         WHEN 'temperature_setpoint' THEN 'Solltemp'\n" +
      "         WHEN 'co2'                  THEN 'CO₂'\n" +
      "         WHEN 'light'                THEN 'Licht'\n" +
      "         WHEN 'blind_position'       THEN 'Verschattung'\n" +
      "       END\n" +
      "FROM sensor_type st\n" +
      "WHERE st.key IN ('temperature','temperature_setpoint','co2','light','blind_position')\n" +
      "  AND NOT EXISTS (\n" +
      "        SELECT 1 FROM sensor s WHERE s.room_id = ? AND s.type_id = st.id\n" +
      "      )";
    try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
      ps.setInt(1, roomId);
      ps.setInt(2, roomId);
      int ins = ps.executeUpdate();
      System.out.println("insertSensorsForRoom(" + roomId + "): " + ins + " Sensor(en) angelegt");
    }
  }

  /** Initialwerte schreiben (nur für Sensoren ohne Werte). */
  private void insertInitialValuesForRoom(int roomId) throws SQLException {
    String sql =
      "INSERT INTO sensor_value (sensor_id, value_numeric, ts)\n" +
      "SELECT s.id,\n" +
      "       CASE st.key\n" +
      "         WHEN 'temperature'          THEN 22.0\n" +
      "         WHEN 'temperature_setpoint' THEN 21.0\n" +
      "         WHEN 'co2'                  THEN 600.0\n" +
      "         WHEN 'light'                THEN 30.0\n" +
      "         WHEN 'blind_position'       THEN 0.0\n" +
      "       END,\n" +
      "       now()\n" +
      "FROM sensor s\n" +
      "JOIN sensor_type st ON st.id = s.type_id\n" +
      "WHERE s.room_id = ?\n" +
      "  AND st.key IN ('temperature','temperature_setpoint','co2','light','blind_position')\n" +
      "  AND NOT EXISTS (SELECT 1 FROM sensor_value v WHERE v.sensor_id = s.id)";
    try (PreparedStatement ps = dbConn.prepareStatement(sql)) {
      ps.setInt(1, roomId);
      int ins = ps.executeUpdate();
      System.out.println("insertInitialValuesForRoom(" + roomId + "): " + ins + " Wert(e) angelegt");
    }
  }
}
