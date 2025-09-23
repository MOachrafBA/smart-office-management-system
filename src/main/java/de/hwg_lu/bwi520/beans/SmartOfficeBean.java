package de.hwg_lu.bwi520.beans;

/**
 * SmartOfficeBean - Haupt-Controller für die Smart Office Anwendung
 * 
 * Diese Bean verwaltet den globalen Zustand der Anwendung und koordiniert
 * die Navigation zwischen den verschiedenen Komponenten.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class SmartOfficeBean {
    
    // ========== SESSION MANAGEMENT ==========
    
    /**
     * Flag für Anmeldestatus
     */
    private boolean loggedIn = false;
    
    /**
     * Aktuell ausgewähltes Gebäude
     */
    private String currentBuilding = "";
    
    /**
     * Aktuell ausgewählte Etage
     */
    private String currentFloor = "";
    
    /**
     * Aktuell ausgewählter Raum
     */
    private String currentRoom = "";
    
    /**
     * Aktueller Benutzer
     */
    private String currentUser = "";
    
    // ========== KONSTRUKTOR ==========
    
    /**
     * Standard-Konstruktor
     * Initialisiert die Bean mit Standardwerten
     */
    public SmartOfficeBean() {
        this.loggedIn = false;
        this.currentBuilding = "";
        this.currentFloor = "";
        this.currentRoom = "";
    }
    
    // ========== SESSION MANAGEMENT METHODEN ==========
    
    /**
     * Prüft ob der Benutzer angemeldet ist
     * @return true wenn angemeldet, false sonst
     */
    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    /**
     * Setzt den Anmeldestatus
     * @param status true für angemeldet, false für abgemeldet
     */
    public void setLoggedIn(boolean status) {
        this.loggedIn = status;
    }
    
    /**
     * Meldet den Benutzer an
     */
    public void login() {
        this.loggedIn = true;
    }
    
    /**
     * Meldet den Benutzer ab
     */
    public void logout() {
        this.loggedIn = false;
        this.currentBuilding = "";
        this.currentFloor = "";
        this.currentRoom = "";
        this.currentUser = "";
    }
    
    // ========== NAVIGATION MANAGEMENT ==========
    
    /**
     * Setzt das aktuell ausgewählte Gebäude
     * @param buildingId ID des Gebäudes
     */
    public void setCurrentBuilding(String buildingId) {
        this.currentBuilding = buildingId;
        // Bei Gebäude-Wechsel: Etage und Raum zurücksetzen
        this.currentFloor = "";
        this.currentRoom = "";
    }
    
    /**
     * Gibt die ID des aktuell ausgewählten Gebäudes zurück
     * @return Gebäude-ID
     */
    public String getCurrentBuilding() {
        return currentBuilding;
    }
    
    /**
     * Setzt die aktuell ausgewählte Etage
     * @param floorId ID der Etage
     */
    public void setCurrentFloor(String floorId) {
        this.currentFloor = floorId;
        // Bei Etagen-Wechsel: Raum zurücksetzen
        this.currentRoom = "";
    }
    
    /**
     * Gibt die ID der aktuell ausgewählten Etage zurück
     * @return Etagen-ID
     */
    public String getCurrentFloor() {
        return currentFloor;
    }
    
    /**
     * Setzt den aktuell ausgewählten Raum
     * @param roomId ID des Raumes
     */
    public void setCurrentRoom(String roomId) {
        this.currentRoom = roomId;
    }
    
    /**
     * Gibt die ID des aktuell ausgewählten Raumes zurück
     * @return Raum-ID
     */
    public String getCurrentRoom() {
        return currentRoom;
    }
    
    // ========== NAVIGATION HELPER METHODEN ==========
    
    /**
     * Prüft ob ein Gebäude ausgewählt ist
     * @return true wenn Gebäude ausgewählt, false sonst
     */
    public boolean hasBuildingSelected() {
        return currentBuilding != null && !currentBuilding.isEmpty();
    }
    
    /**
     * Prüft ob eine Etage ausgewählt ist
     * @return true wenn Etage ausgewählt, false sonst
     */
    public boolean hasFloorSelected() {
        return currentFloor != null && !currentFloor.isEmpty();
    }
    
    /**
     * Prüft ob ein Raum ausgewählt ist
     * @return true wenn Raum ausgewählt, false sonst
     */
    public boolean hasRoomSelected() {
        return currentRoom != null && !currentRoom.isEmpty();
    }
    
    /**
     * Gibt den aktuellen Navigationspfad zurück
     * @return String mit dem aktuellen Pfad
     */
    public String getCurrentPath() {
        StringBuilder path = new StringBuilder();
        
        if (hasBuildingSelected()) {
            path.append("Gebäude ").append(currentBuilding);
        }
        if (hasFloorSelected()) {
            path.append(" → Etage ").append(currentFloor);
        }
        if (hasRoomSelected()) {
            path.append(" → Raum ").append(currentRoom);
        }
        
        return path.toString();
    }
    
    // ========== SECURITY METHODEN ==========
    
    /**
     * Prüft ob der Benutzer Zugriff auf die Anwendung hat
     * @return true wenn Zugriff erlaubt, false sonst
     */
    public boolean hasAccess() {
        return loggedIn;
    }
    
    /**
     * Setzt den aktuellen Benutzer
     * @param user Benutzername
     */
    public void setCurrentUser(String user) {
        this.currentUser = user;
    }
    
    /**
     * Gibt den aktuellen Benutzer zurück
     * @return Benutzername
     */
    public String getCurrentUser() {
        return currentUser;
    }
    
    /**
     * Gibt HTML für Login-Check weiterleitung zurück
     * @return HTML-Meta-Tag für Weiterleitung oder leerer String
     */
    public String getLoginCheckRedirectHtml() {
        if (!hasAccess()) {
            return "<meta http-equiv=\"refresh\" content=\"0; URL=LoginAppl.jsp\">";
        }
        return "";
    }
}
