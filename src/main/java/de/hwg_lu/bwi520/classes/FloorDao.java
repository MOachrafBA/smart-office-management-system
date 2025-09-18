package de.hwg_lu.bwi520.classes;
import de.hwg_lu.bwi520.beans.Floor; import de.hwg_lu.bwi520.jdbc.Db;
import java.sql.*; import java.util.*;
public class FloorDao {
  public List<Floor> byBuilding(int buildingId) throws SQLException {
    String sql="SELECT id,building_id,name,index_no FROM floor WHERE building_id=? ORDER BY index_no";
    try (PreparedStatement ps=Db.get().prepareStatement(sql)) {
      // SQL-Abfrage, um alle Etagen des Gebäudes aus der Datenbank zu holen
      ps.setInt(1, buildingId); ResultSet rs=ps.executeQuery();
      // Ergebnis in Liste umwandeln
      List<Floor> list=new ArrayList<>();
      while (rs.next()) list.add(new Floor(rs.getInt(1),rs.getInt(2),rs.getString(3),rs.getInt(4)));
      // Liste zurückgeben
      return list;
    }
  }
}
