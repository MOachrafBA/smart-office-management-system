package de.hwg_lu.bwi520.beans;

import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;
import de.hwg_lu.bwi520.classes.BuildingDao;

/**
 * BuildingBean - Geschäftslogik für Gebäude-Verwaltung
 * 
 * Diese Bean verwaltet die Gebäude-Daten und stellt Methoden für
 * die Gebäude-Auswahl und -Anzeige zur Verfügung.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class BuildingBean {
    
    // ========== ATTRIBUTE ==========
    
    /**
     * Liste aller verfügbaren Gebäude
     */
    private List<Building> buildings = new ArrayList<>();
    
    /**
     * Aktuell ausgewähltes Gebäude
     */
    private Building selectedBuilding = null;
    
    /**
     * DAO für Datenbankzugriff
     */
    private BuildingDao dao = new BuildingDao();
    
    /**
     * Flag für erfolgreiche Datenladung
     */
    private boolean dataLoaded = false;
    
    // ========== KONSTRUKTOR ==========
    
    /**
     * Standard-Konstruktor
     * Initialisiert die Bean mit leeren Listen
     */
    public BuildingBean() {
        this.buildings = new ArrayList<>();
        this.selectedBuilding = null;
        this.dataLoaded = false;
    }
    
    // ========== GETTER UND SETTER ==========
    
    /**
     * Gibt die Liste aller Gebäude zurück
     * @return Liste der Gebäude
     */
    public List<Building> getBuildings() {
        return buildings;
    }
    
    /**
     * Setzt die Liste der Gebäude
     * @param buildings Liste der Gebäude
     */
    public void setBuildings(List<Building> buildings) {
        this.buildings = buildings;
    }
    
    /**
     * Gibt das aktuell ausgewählte Gebäude zurück
     * @return Ausgewähltes Gebäude oder null
     */
    public Building getSelectedBuilding() {
        return selectedBuilding;
    }
    
    /**
     * Setzt das ausgewählte Gebäude
     * @param building Gebäude-Objekt
     */
    public void setSelectedBuilding(Building building) {
        this.selectedBuilding = building;
    }
    
    /**
     * Prüft ob Daten geladen wurden
     * @return true wenn Daten geladen, false sonst
     */
    public boolean isDataLoaded() {
        return dataLoaded;
    }
    
    // ========== DATENLADUNG ==========
    
    /**
     * Lädt alle Gebäude aus der Datenbank
     * @throws SQLException bei Datenbankfehlern
     */
    public void loadBuildings() throws SQLException {
        try {
            this.buildings = dao.findAll();
            this.dataLoaded = true;
        } catch (SQLException e) {
            this.dataLoaded = false;
            throw new SQLException("Fehler beim Laden der Gebäude: " + e.getMessage());
        }
    }
    
    /**
     * Lädt ein spezifisches Gebäude nach ID
     * @param buildingId ID des Gebäudes
     * @throws SQLException bei Datenbankfehlern
     */
    public void loadBuildingById(int buildingId) throws SQLException {
        try {
            // Alle Gebäude laden und das gewünschte finden
            List<Building> allBuildings = dao.findAll();
            for (Building building : allBuildings) {
                if (building.getId() == buildingId) {
                    this.selectedBuilding = building;
                    return;
                }
            }
            // Gebäude nicht gefunden
            this.selectedBuilding = null;
        } catch (SQLException e) {
            throw new SQLException("Fehler beim Laden des Gebäudes: " + e.getMessage());
        }
    }
    
    // ========== GEBÄUDE-AUSWAHL ==========
    
    /**
     * Wählt ein Gebäude nach ID aus
     * @param buildingId ID des Gebäudes
     * @return true wenn erfolgreich, false sonst
     */
    public boolean selectBuilding(int buildingId) {
        try {
            // Gebäude in der Liste finden
            for (Building building : buildings) {
                if (building.getId() == buildingId) {
                    this.selectedBuilding = building;
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
    
    /**
     * Wählt ein Gebäude nach ID aus (String-Version)
     * @param buildingId ID des Gebäudes als String
     * @return true wenn erfolgreich, false sonst
     */
    public boolean selectBuilding(String buildingId) {
        try {
            int id = Integer.parseInt(buildingId);
            return selectBuilding(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Gibt die ID des ausgewählten Gebäudes zurück
     * @return Gebäude-ID oder -1 wenn keins ausgewählt
     */
    public int getSelectedBuildingId() {
        if (selectedBuilding != null) {
            return selectedBuilding.getId();
        }
        return -1;
    }
    
    /**
     * Gibt den Namen des ausgewählten Gebäudes zurück
     * @return Gebäude-Name oder leerer String
     */
    public String getSelectedBuildingName() {
        if (selectedBuilding != null) {
            return selectedBuilding.getName();
        }
        return "";
    }
    
    // ========== HTML-GENERIERUNG ==========
    
    /**
     * Generiert HTML für die Gebäude-Liste
     * @return HTML-String für die Gebäude-Liste
     */
    public String getBuildingsHtml() {
        if (buildings == null || buildings.isEmpty()) {
            return "<p>Keine Gebäude verfügbar.</p>";
        }
        
        StringBuilder html = new StringBuilder();
        html.append("<ul class=\"building-list\">");
        
        int colorIndex = 0;
        String[] colorClasses = {"building-blue", "building-green", "building-orange", "building-pink", "building-purple"};
        String[] icons = {"🏢", "🏬", "🔬", "🏭", "🏛️"};
        
        for (Building building : buildings) {
            String colorClass = colorClasses[colorIndex % colorClasses.length];
            String icon = icons[colorIndex % icons.length];
            colorIndex++;
            
            html.append("<li class=\"building-item\">");
            html.append("<a href=\"").append("/BWI520/jsp/BuildingAppl.jsp?buildingId=").append(building.getId()).append("\" ");
            html.append("class=\"building-link ").append(colorClass).append("\">");
            html.append(icon).append(" ").append(building.getName());
            html.append("</a>");
            html.append("</li>");
        }
        
        html.append("</ul>");
        return html.toString();
    }
    
    /**
     * Generiert HTML für die Gebäude-Auswahl (vereinfacht)
     * @return HTML-String für die Gebäude-Auswahl
     */
    public String getBuildingsSelectHtml() {
        if (buildings == null || buildings.isEmpty()) {
            return "<p>Keine Gebäude verfügbar.</p>";
        }
        
        StringBuilder html = new StringBuilder();
        html.append("<select name=\"buildingId\" required>");
        html.append("<option value=\"\">-- Gebäude auswählen --</option>");
        
        for (Building building : buildings) {
            html.append("<option value=\"").append(building.getId()).append("\">");
            html.append(building.getName());
            html.append("</option>");
        }
        
        html.append("</select>");
        return html.toString();
    }
    
    // ========== VALIDIERUNG ==========
    
    /**
     * Prüft ob ein Gebäude mit der gegebenen ID existiert
     * @param buildingId ID des Gebäudes
     * @return true wenn existiert, false sonst
     */
    public boolean buildingExists(int buildingId) {
        if (buildings == null || buildings.isEmpty()) {
            return false;
        }
        
        for (Building building : buildings) {
            if (building.getId() == buildingId) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Prüft ob ein Gebäude mit der gegebenen ID existiert (String-Version)
     * @param buildingId ID des Gebäudes als String
     * @return true wenn existiert, false sonst
     */
    public boolean buildingExists(String buildingId) {
        try {
            int id = Integer.parseInt(buildingId);
            return buildingExists(id);
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    // ========== HELPER METHODEN ==========
    
    /**
     * Gibt die Anzahl der verfügbaren Gebäude zurück
     * @return Anzahl der Gebäude
     */
    public int getBuildingCount() {
        if (buildings == null) {
            return 0;
        }
        return buildings.size();
    }
    
    /**
     * Prüft ob ein Gebäude ausgewählt ist
     * @return true wenn ausgewählt, false sonst
     */
    public boolean hasSelectedBuilding() {
        return selectedBuilding != null;
    }
    
    /**
     * Setzt die Auswahl zurück
     */
    public void clearSelection() {
        this.selectedBuilding = null;
    }
    
    /**
     * Gibt Informationen über das ausgewählte Gebäude zurück
     * @return String mit Gebäude-Informationen
     */
    public String getSelectedBuildingInfo() {
        if (selectedBuilding != null) {
            return "Ausgewähltes Gebäude: " + selectedBuilding.getName() + " (ID: " + selectedBuilding.getId() + ")";
        }
        return "Kein Gebäude ausgewählt";
    }
}
