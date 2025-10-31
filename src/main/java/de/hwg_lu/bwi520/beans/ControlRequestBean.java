package de.hwg_lu.bwi520.beans;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import de.hwg_lu.bwi520.classes.ControlRequestDao;

/**
 * Bean für Control Request Management
 */
public class ControlRequestBean implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<ControlRequestRow> controlRequests;
    private String errorMessage;
    
    // Konstruktor
    public ControlRequestBean() {}
    
    /**
     * Lädt die letzten Steuerbefehle aus der Datenbank
     */
    public void loadRecentControlRequests() {
        try {
            ControlRequestDao dao = new ControlRequestDao();
            this.controlRequests = dao.getRecentControlRequests();
            this.errorMessage = null;
        } catch (SQLException e) {
            this.errorMessage = "Fehler beim Laden der Steuerbefehle: " + e.getMessage();
            e.printStackTrace();
        }
    }
    
    /**
     * Generiert HTML für die Control Request Tabelle
     */
    public String getControlRequestsHtml() {
        if (controlRequests == null || controlRequests.isEmpty()) {
            return "<tr><td colspan='5'>Keine Steuerbefehle gefunden.</td></tr>";
        }
        
        StringBuilder html = new StringBuilder();
        for (ControlRequestRow request : controlRequests) {
            html.append("<tr>");
            html.append("<td>").append(request.getId()).append("</td>");
            html.append("<td>").append(request.getSensorId()).append(" — ")
                .append(request.getSensorLabel()).append(" (")
                .append(request.getSensorTypeKey()).append(")</td>");
            html.append("<td>").append(request.getRequestedValue()).append("</td>");
            html.append("<td>").append(request.getUsername() != null ? request.getUsername() : "Unbekannt").append("</td>");
            html.append("<td>").append(request.getCreatedAt()).append("</td>");
            html.append("</tr>");
        }
        return html.toString();
    }
    
    // Getter und Setter
    public List<ControlRequestRow> getControlRequests() { return controlRequests; }
    public void setControlRequests(List<ControlRequestRow> controlRequests) { this.controlRequests = controlRequests; }
    
    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
    
    public boolean hasError() { return errorMessage != null && !errorMessage.isEmpty(); }
}
