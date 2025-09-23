<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.beans.*" %>
<%@ page import="java.sql.SQLException" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myBuilding" class="de.hwg_lu.bwi520.beans.BuildingBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

<%
// ========== PARAMETER EINLESEN ==========
String buildingId = request.getParameter("buildingId");

// ========== NULL-CHECK ==========
if (buildingId == null) buildingId = "";

// ========== AKTIONSWEICHE ==========
try {
    if (!buildingId.isEmpty()) {
        // ========== GEBÄUDE AUSWÄHLEN ==========
        boolean success = myBuilding.selectBuilding(buildingId);
        if (success) {
            // SmartOffice Bean aktualisieren
            mySmartOffice.setCurrentBuilding(buildingId);
            
            // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
            // String buildingName = myBuilding.getSelectedBuildingName();
            // myMessage.setBuildingSelectedSuccess(buildingName);
            
            // Weiterleitung zur Etagen-Ansicht
            response.sendRedirect("BuildingView.jsp");
        } else {
            myMessage.setBuildingSelectionFailed();
            response.sendRedirect("BuildingsView.jsp");
        }
    } else {
        // ========== ELSE-ZWEIG ==========
        // Keine Gebäude-ID angegeben
        myMessage.setBuildingSelectionFailed();
        response.sendRedirect("BuildingsView.jsp");
    }
    
} catch (NumberFormatException e) {
    // ========== FEHLERBEHANDLUNG ==========
    myMessage.setAnyError("Ungültige Gebäude-ID: " + e.getMessage());
    response.sendRedirect("BuildingsView.jsp");
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("BuildingsView.jsp");
}
%>
