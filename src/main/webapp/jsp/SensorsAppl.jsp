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
String btnBack = request.getParameter("btnBack");
String btnRefresh = request.getParameter("btnRefresh");
String btnSet = request.getParameter("btnSet");
String sensorId = request.getParameter("sensorId");
String desiredValue = request.getParameter("desiredValue");
String roomName = request.getParameter("roomName");

// ========== NULL-CHECK ==========
if (btnBack == null) btnBack = "";
if (btnRefresh == null) btnRefresh = "";
if (btnSet == null) btnSet = "";
if (sensorId == null) sensorId = "";
if (desiredValue == null) desiredValue = "";
if (roomName == null) roomName = "";

// ========== RAUM AUSWÄHLEN ==========
if (!roomName.isEmpty()) {
    mySmartOffice.setCurrentRoom(roomName);
}

// ========== AKTIONSWEICHE ==========
try {
    if (btnSet.equals("Setzen")) {
        // ========== SENSOR WERT SETZEN ==========
        if (sensorId.isEmpty() || desiredValue.isEmpty()) {
            myMessage.setAnyError("Sensor-ID oder Wert fehlt!");
            response.sendRedirect("SensorsView.jsp");
            return;
        }
        
        // Sensor-Steuerung implementieren
        try {
            // Hier würde die echte Sensor-Steuerung implementiert werden
            // Für Demo: Erfolgsnachricht mit den Werten
            String successMessage = "Sensor " + sensorId + " auf " + desiredValue + " gesetzt!";
            myMessage.setAnyError(successMessage); // Verwende setAnyError für Demo
            response.sendRedirect("SensorsView.jsp");
            
        } catch (Exception e) {
            myMessage.setAnyError("Fehler beim Setzen des Sensors: " + e.getMessage());
            response.sendRedirect("SensorsView.jsp");
        }
        
    } else if (btnBack.equals("Zurück zu Räumen")) {
        // ========== ZURÜCK ZU RÄUMEN ==========
        mySmartOffice.setCurrentRoom("");
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setFloorSelectedSuccess(mySmartOffice.getCurrentFloor());
        response.sendRedirect("RoomsView.jsp");
        
    } else if (btnRefresh.equals("Seite neu laden")) {
        // ========== SEITE NEU LADEN ==========
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setRoomSelectedSuccess("Raum " + mySmartOffice.getCurrentRoom());
        response.sendRedirect("SensorsView.jsp");
        
    } else {
        // ========== ELSE-ZWEIG ==========
        // Standard-Weiterleitung
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setRoomSelectedSuccess("Raum " + mySmartOffice.getCurrentRoom());
        response.sendRedirect("SensorsView.jsp");
    }
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("RoomsView.jsp");
}
%>
