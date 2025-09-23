package de.hwg_lu.bwi520.beans;

import java.sql.Timestamp;

/**
 * User Bean - Benutzerdaten
 * 
 * Diese Bean repräsentiert einen Benutzer im Smart Office System
 * und enthält alle relevanten Benutzerinformationen.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class User {
    
    // ========== ATTRIBUTE ==========
    
    /**
     * Eindeutige Benutzer-ID
     */
    private int id;
    
    /**
     * Benutzername (eindeutig)
     */
    private String username;
    
    /**
     * E-Mail-Adresse (eindeutig)
     */
    private String email;
    
    /**
     * Gehashtes Passwort
     */
    private String passwordHash;
    
    /**
     * Vorname
     */
    private String firstName;
    
    /**
     * Nachname
     */
    private String lastName;
    
    /**
     * Benutzerrolle (admin, user, manager)
     */
    private String role;
    
    /**
     * Aktiv-Status
     */
    private boolean active;
    
    /**
     * Erstellungsdatum
     */
    private Timestamp createdAt;
    
    /**
     * Letzter Login
     */
    private Timestamp lastLogin;
    
    // ========== KONSTRUKTOR ==========
    
    /**
     * Standard-Konstruktor
     */
    public User() {
        this.id = 0;
        this.username = "";
        this.email = "";
        this.passwordHash = "";
        this.firstName = "";
        this.lastName = "";
        this.role = "user";
        this.active = true;
        this.createdAt = null;
        this.lastLogin = null;
    }
    
    /**
     * Konstruktor mit Benutzername und E-Mail
     * @param username Benutzername
     * @param email E-Mail-Adresse
     */
    public User(String username, String email) {
        this();
        this.username = username;
        this.email = email;
    }
    
    // ========== GETTER UND SETTER ==========
    
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPasswordHash() {
        return passwordHash;
    }
    
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }
    
    public String getFirstName() {
        return firstName;
    }
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
    public String getLastName() {
        return lastName;
    }
    
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
    }
    
    public boolean isActive() {
        return active;
    }
    
    public void setActive(boolean active) {
        this.active = active;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }
    
    public Timestamp getLastLogin() {
        return lastLogin;
    }
    
    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }
    
    // ========== HELPER METHODEN ==========
    
    /**
     * Gibt den vollständigen Namen zurück
     * @return "Vorname Nachname" oder "Benutzername"
     */
    public String getFullName() {
        if (firstName != null && !firstName.isEmpty() && lastName != null && !lastName.isEmpty()) {
            return firstName + " " + lastName;
        }
        return username;
    }
    
    /**
     * Prüft ob der Benutzer ein Admin ist
     * @return true wenn Admin, false sonst
     */
    public boolean isAdmin() {
        return "admin".equals(role);
    }
    
    /**
     * Prüft ob der Benutzer ein Manager ist
     * @return true wenn Manager, false sonst
     */
    public boolean isManager() {
        return "manager".equals(role);
    }
    
    /**
     * Prüft ob der Benutzer ein normaler User ist
     * @return true wenn normaler User, false sonst
     */
    public boolean isUser() {
        return "user".equals(role);
    }
    
    /**
     * Gibt eine benutzerfreundliche Rollenbeschreibung zurück
     * @return Rollenbeschreibung
     */
    public String getRoleDescription() {
        switch (role) {
            case "admin":
                return "Administrator";
            case "manager":
                return "Manager";
            case "user":
                return "Benutzer";
            default:
                return "Unbekannt";
        }
    }
    
    /**
     * Gibt den Status als String zurück
     * @return "Aktiv" oder "Inaktiv"
     */
    public String getStatusText() {
        return active ? "Aktiv" : "Inaktiv";
    }
    
    /**
     * Gibt eine Zusammenfassung der Benutzerdaten zurück
     * @return String mit Benutzerinformationen
     */
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", role='" + role + '\'' +
                ", active=" + active +
                '}';
    }
}
