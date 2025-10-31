package de.hwg_lu.bwi520.classes;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.hwg_lu.bwi520.beans.ControlRequestRow;
import de.hwg_lu.bwi520.jdbc.PostgreSQLAccess;

/**
 * DAO für Control Request Operationen
 */
public class ControlRequestDao {
    
    /**
     * Holt die letzten Steuerbefehle aus der Datenbank
     */
    public List<ControlRequestRow> getRecentControlRequests() throws SQLException {
        List<ControlRequestRow> requests = new ArrayList<>();
        
        String sql = "SELECT cr.id, cr.requested_value, cr.created_at, cr.username, " +
                    "       s.id AS sensor_id, s.label, st.key " +
                    "FROM control_request cr " +
                    "JOIN sensor s ON s.id = cr.sensor_id " +
                    "JOIN sensor_type st ON st.id = s.type_id " +
                    "ORDER BY cr.created_at DESC " +
                    "LIMIT 50";
        
        try (Connection conn = new PostgreSQLAccess().getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                ControlRequestRow request = new ControlRequestRow();
                request.setId(rs.getLong("id"));
                request.setRequestedValue(rs.getDouble("requested_value"));
                request.setCreatedAt(rs.getTimestamp("created_at"));
                request.setUsername(rs.getString("username"));
                request.setSensorId(rs.getInt("sensor_id"));
                request.setSensorLabel(rs.getString("label"));
                request.setSensorTypeKey(rs.getString("key"));
                
                requests.add(request);
            }
        }
        
        return requests;
    }
}
