package de.hwg_lu.bwi520.classes;
import de.hwg_lu.bwi520.beans.Room; import de.hwg_lu.bwi520.jdbc.Db;
import java.sql.*; import java.util.*;
public class RoomDao {
  public List<Room> byFloor(int floorId) throws SQLException {
    String sql="SELECT id,floor_id,code,name FROM room WHERE floor_id=? ORDER BY code";
    try (PreparedStatement ps=Db.get().prepareStatement(sql)) {
      // SQL-Abfrage, um alle Räume für diese Etage aus der Datenbank zu holen
      ps.setInt(1,floorId); ResultSet rs=ps.executeQuery();
      // Ergebnis in Liste umwandeln
      List<Room> list=new ArrayList<>();
      while (rs.next()) list.add(new Room(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getString(4)));
      // Liste zurückgeben
      return list;
    }
  }
}
