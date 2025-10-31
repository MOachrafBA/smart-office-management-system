package de.hwg_lu.bwi520.classes;

import de.hwg_lu.bwi520.beans.Room;
import de.hwg_lu.bwi520.jdbc.PostgreSQLAccess;
import de.hwg_lu.bwi520.jdbc.NoConnectionException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO für Raum-bezogene Datenbankoperationen
 */
public class RoomDao {

    /**
     * Lädt alle Räume einer Etage
     * @param floorId ID der Etage
     * @return Liste der Räume
     * @throws SQLException bei Datenbankfehlern
     */
    public List<Room> findByFloorId(int floorId) throws SQLException {
        String sql = "SELECT id, floor_id, code, name FROM bwi520.room WHERE floor_id = ? ORDER BY code";

        List<Room> rooms = new ArrayList<>();

        try (PreparedStatement ps = new PostgreSQLAccess().getConnection().prepareStatement(sql)) {
            ps.setInt(1, floorId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    rooms.add(mapResultSetToRoom(rs));
                }
            }
        }
        return rooms;
    }

    /**
     * Lädt einen Raum nach ID
     * @param roomId ID des Raumes
     * @return Room-Objekt oder null
     * @throws SQLException bei Datenbankfehlern
     */
    public Room findById(int roomId) throws SQLException {
        String sql = "SELECT id, floor_id, code, name FROM bwi520.room WHERE id = ?";

        try (PreparedStatement ps = new PostgreSQLAccess().getConnection().prepareStatement(sql)) {
            ps.setInt(1, roomId);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToRoom(rs);
                }
            }
        }
        return null;
    }

    /**
     * Lädt alle Räume
     * @return Liste aller Räume
     * @throws SQLException bei Datenbankfehlern
     */
    public List<Room> findAll() throws SQLException {
        String sql = "SELECT id, floor_id, code, name FROM bwi520.room ORDER BY floor_id, code";

        List<Room> rooms = new ArrayList<>();

        try (PreparedStatement ps = new PostgreSQLAccess().getConnection().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                rooms.add(mapResultSetToRoom(rs));
            }
        }
        return rooms;
    }

    /**
     * Mappt ein ResultSet zu einem Room-Objekt
     * @param rs ResultSet
     * @return Room-Objekt
     * @throws SQLException bei Datenbankfehlern
     */
    private Room mapResultSetToRoom(ResultSet rs) throws SQLException {
        Room room = new Room();
        room.setId(rs.getInt("id"));
        room.setFloorId(rs.getInt("floor_id"));
        room.setCode(rs.getString("code"));
        room.setName(rs.getString("name"));
        return room;
    }
}