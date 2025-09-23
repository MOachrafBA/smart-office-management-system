package de.hwg_lu.bwi520.classes;

import de.hwg_lu.bwi520.beans.User;
import de.hwg_lu.bwi520.jdbc.Db;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * UserDaoPublic - Datenbankzugriff für Benutzer im public Schema
 * 
 * Diese Klasse verwaltet alle Datenbankoperationen für Benutzer,
 * einschließlich Anmeldung, Registrierung und Benutzerverwaltung.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class UserDaoPublic {
    
    /**
     * Prüft Benutzeranmeldung
     * @param username Benutzername
     * @param password Passwort (plain text)
     * @return User-Objekt bei erfolgreicher Anmeldung, null sonst
     * @throws SQLException bei Datenbankfehlern
     */
    public User authenticate(String username, String password) throws SQLException {
        String sql = "SELECT id, username, email, first_name, last_name, role, is_active, created_at, last_login " +
                     "FROM users WHERE username = ? AND is_active = true";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRole(rs.getString("role"));
                    user.setActive(rs.getBoolean("is_active"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setLastLogin(rs.getTimestamp("last_login"));
                    
                    // Passwort prüfen (vereinfacht für Demo)
                    if (password.equals("admin123") && username.equals("admin")) {
                        return user;
                    } else if (password.equals("user123") && username.equals("user")) {
                        return user;
                    } else if (password.equals("manager123") && username.equals("manager")) {
                        return user;
                    }
                }
            }
        }
        return null;
    }
    
    /**
     * Registriert einen neuen Benutzer
     * @param user User-Objekt mit Benutzerdaten
     * @return true bei erfolgreicher Registrierung, false sonst
     * @throws SQLException bei Datenbankfehlern
     */
    public boolean register(User user) throws SQLException {
        String sql = "INSERT INTO users (username, email, password_hash, first_name, last_name, role) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPasswordHash()); // In echter Anwendung: gehashtes Passwort
            ps.setString(4, user.getFirstName());
            ps.setString(5, user.getLastName());
            ps.setString(6, user.getRole());
            
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }
    }
    
    /**
     * Prüft ob ein Benutzername bereits existiert
     * @param username Benutzername
     * @return true wenn existiert, false sonst
     * @throws SQLException bei Datenbankfehlern
     */
    public boolean usernameExists(String username) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE username = ?";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setString(1, username);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Prüft ob eine E-Mail bereits existiert
     * @param email E-Mail-Adresse
     * @return true wenn existiert, false sonst
     * @throws SQLException bei Datenbankfehlern
     */
    public boolean emailExists(String email) throws SQLException {
        String sql = "SELECT COUNT(*) FROM users WHERE email = ?";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setString(1, email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }
    
    /**
     * Aktualisiert den letzten Login-Zeitpunkt
     * @param userId Benutzer-ID
     * @throws SQLException bei Datenbankfehlern
     */
    public void updateLastLogin(int userId) throws SQLException {
        String sql = "UPDATE users SET last_login = CURRENT_TIMESTAMP WHERE id = ?";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.executeUpdate();
        }
    }
    
    /**
     * Lädt alle Benutzer (nur für Admins)
     * @return Liste aller Benutzer
     * @throws SQLException bei Datenbankfehlern
     */
    public List<User> findAll() throws SQLException {
        String sql = "SELECT id, username, email, first_name, last_name, role, is_active, created_at, last_login " +
                     "FROM users ORDER BY username";
        
        List<User> users = new ArrayList<>();
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setFirstName(rs.getString("first_name"));
                user.setLastName(rs.getString("last_name"));
                user.setRole(rs.getString("role"));
                user.setActive(rs.getBoolean("is_active"));
                user.setCreatedAt(rs.getTimestamp("created_at"));
                user.setLastLogin(rs.getTimestamp("last_login"));
                
                users.add(user);
            }
        }
        
        return users;
    }
    
    /**
     * Lädt einen Benutzer nach ID
     * @param userId Benutzer-ID
     * @return User-Objekt oder null
     * @throws SQLException bei Datenbankfehlern
     */
    public User findById(int userId) throws SQLException {
        String sql = "SELECT id, username, email, first_name, last_name, role, is_active, created_at, last_login " +
                     "FROM users WHERE id = ?";
        
        try (PreparedStatement ps = Db.get().prepareStatement(sql)) {
            ps.setInt(1, userId);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    User user = new User();
                    user.setId(rs.getInt("id"));
                    user.setUsername(rs.getString("username"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("first_name"));
                    user.setLastName(rs.getString("last_name"));
                    user.setRole(rs.getString("role"));
                    user.setActive(rs.getBoolean("is_active"));
                    user.setCreatedAt(rs.getTimestamp("created_at"));
                    user.setLastLogin(rs.getTimestamp("last_login"));
                    
                    return user;
                }
            }
        }
        return null;
    }
}
