package de.hwg_lu.bwi520.beans;

import de.hwg_lu.bwi520.classes.RoomDao;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

/**
 * RoomBean verwaltet die Geschäftslogik und Daten für Räume.
 * Diese Bean wird in der Session gespeichert.
 */
public class RoomBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private final RoomDao dao = new RoomDao();
    private List<Room> rooms;
    private Room selectedRoom;
    private boolean dataLoaded = false;

    // ========== KONSTRUKTOR ==========

    /**
     * Standard-Konstruktor
     */
    public RoomBean() {
        // Initialisierung
    }

    // ========== GETTER ==========

    public List<Room> getRooms() {
        return rooms;
    }

    public Room getSelectedRoom() {
        return selectedRoom;
    }

    public String getSelectedRoomName() {
        return selectedRoom != null ? selectedRoom.getName() : "Unbekannt";
    }

    public boolean isDataLoaded() {
        return dataLoaded;
    }

    // ========== DATENBANK-OPERATIONEN ==========

    /**
     * Lädt alle Räume einer Etage aus der Datenbank
     * @param floorId ID der Etage
     * @throws SQLException bei Datenbankfehlern
     */
    public void loadRoomsByFloor(int floorId) throws SQLException {
        try {
            this.rooms = dao.findByFloorId(floorId);
            this.dataLoaded = true;
        } catch (SQLException e) {
            this.dataLoaded = false;
            throw new SQLException("Fehler beim Laden der Räume: " + e.getMessage());
        }
    }

    /**
     * Lädt einen spezifischen Raum nach ID
     * @param roomId ID des Raumes
     * @throws SQLException bei Datenbankfehlern
     */
    public void loadRoomById(int roomId) throws SQLException {
        try {
            this.selectedRoom = dao.findById(roomId);
        } catch (SQLException e) {
            throw new SQLException("Fehler beim Laden des Raumes: " + e.getMessage());
        }
    }

    // ========== RAUM-AUSWAHL ==========

    /**
     * Wählt einen Raum nach ID aus
     * @param roomIdStr ID des Raumes als String
     * @return true, wenn Raum erfolgreich ausgewählt, sonst false
     * @throws SQLException bei Datenbankfehlern
     * @throws NumberFormatException wenn roomIdStr keine gültige Zahl ist
     */
    public boolean selectRoom(String roomIdStr) throws SQLException, NumberFormatException {
        if (roomIdStr == null || roomIdStr.isEmpty()) {
            this.selectedRoom = null;
            return false;
        }
        int roomId = Integer.parseInt(roomIdStr);
        loadRoomById(roomId);
        return this.selectedRoom != null;
    }

    // ========== HTML-GENERIERUNG ==========

    /**
     * Generiert HTML für die Raum-Liste
     * @return HTML-String für die Raum-Liste
     */
    public String getRoomsHtml() {
        if (rooms == null || rooms.isEmpty()) {
            return "<p>Keine Räume verfügbar.</p>";
        }

        StringBuilder html = new StringBuilder();
        html.append("<div class=\"room-grid\">");

        for (Room room : rooms) {
            // Vollständige URL mit allen notwendigen Parametern
            html.append("<a class=\"room-card\" href=\"/BWI520/jsp/SensorsAppl.jsp?roomId=")
                .append(room.getId())
                .append("&floorId=").append(room.getFloorId())
                .append("&buildingId=1") // Hardcodiert für Demo
                .append("&roomName=").append(room.getName())
                .append("\">");
            
            // Bild-Pfad mit room.getCode()
            html.append("<img class=\"room-photo\" src=\"/BWI520/img/rooms/room-")
                .append(room.getCode().toLowerCase().replace("-", "-"))
                .append(".jpg\" alt=\"")
                .append(room.getCode()).append(" — ").append(room.getName())
                .append("\">");
            
            html.append("<div class=\"room-title\">")
                .append(room.getCode()).append(" — ").append(room.getName())
                .append("</div>");
            html.append("</a>");
        }

        html.append("</div>");
        return html.toString();
    }
}
