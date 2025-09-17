package de.hwg_lu.bwi520.classes;
import de.hwg_lu.bwi520.beans.Building; import de.hwg_lu.bwi520.jdbc.Db;
import java.sql.*; import java.util.*;

public class BuildingDao {
  public List<Building> findAll() throws SQLException {
    String sql = "SELECT id,name FROM building ORDER BY name";
    try (PreparedStatement ps = Db.get().prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {
      List<Building> list = new ArrayList<>();
      while (rs.next()) list.add(new Building(rs.getInt(1), rs.getString(2)));
      return list;
    }
  }
}
