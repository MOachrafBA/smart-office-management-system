<%@ page import="de.hwg_lu.bwi520.beans.*" %>
<%@ page import="java.sql.SQLException" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myBuilding" class="de.hwg_lu.bwi520.beans.BuildingBean" scope="session" />
<jsp:useBean id="myRoom" class="de.hwg_lu.bwi520.beans.RoomBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

<%
// ========== PARAMETER EINLESEN ==========
String btnBack = request.getParameter("btnBack");
String btnRefresh = request.getParameter("btnRefresh");
String btnSet = request.getParameter("btnSet");
String sensorId = request.getParameter("sensorId");
String desiredValue = request.getParameter("desiredValue");
String roomId = request.getParameter("roomId");
String roomName = request.getParameter("roomName");

// ========== NULL-CHECK ==========
if (btnBack == null) btnBack = "";
if (btnRefresh == null) btnRefresh = "";
if (btnSet == null) btnSet = "";
if (sensorId == null) sensorId = "";
if (desiredValue == null) desiredValue = "";
if (roomId == null) roomId = "";
if (roomName == null) roomName = "";

// ========== RAUM AUSWÄHLEN ==========
if (!roomId.isEmpty() && btnSet.isEmpty()) {
    try {
        // Raum in SmartOfficeBean setzen
        mySmartOffice.setCurrentRoom(roomId);
        
        // Raum in RoomBean setzen f�rr Namen-Anzeige
        myRoom.selectRoom(roomId);
        
        // Weiterleitung zur Sensoren-Ansicht
        response.sendRedirect("SensorsView.jsp");
        return;
    } catch (Exception e) {
        myMessage.setAnyError("Fehler beim Laden des Raumes: " + e.getMessage());
        response.sendRedirect("RoomsView.jsp");
        return;
    }
}

// ========== AKTIONSWEICHE ==========
try {
    if (btnSet.equals("Setzen")) {
        // ========== SENSOR WERT SETZEN ==========
        System.err.println("DEBUG: Sensor-Steuerung - sensorId: " + sensorId + ", desiredValue: " + desiredValue);
        
        if (sensorId.isEmpty() || desiredValue.isEmpty()) {
            String errorMessage = "Sensor-ID oder Wert fehlt! sensorId=" + sensorId + ", desiredValue=" + desiredValue;
            response.sendRedirect("SensorsView.jsp?message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
            return;
        }
        
        // Echte Sensor-Steuerung implementieren
        try {
            int sensorIdInt = Integer.parseInt(sensorId);
            double valueDouble = Double.parseDouble(desiredValue);
            
            // Sensor-Wert in der Datenbank setzen
            boolean success = mySmartOffice.setSensorValue(sensorIdInt, valueDouble);
            
            if (success) {
                String successMessage = "Sensor " + sensorId + " auf " + desiredValue + " gesetzt!";
                response.sendRedirect("SensorsView.jsp?message=" + java.net.URLEncoder.encode(successMessage, "UTF-8") + "&type=success");
            } else {
                String errorMessage = "Fehler beim Setzen des Sensor-Werts!";
                response.sendRedirect("SensorsView.jsp?message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
            }
            
        } catch (NumberFormatException e) {
            String errorMessage = "Ungültige Sensor-ID oder Wert!";
            response.sendRedirect("SensorsView.jsp?message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
        } catch (Exception e) {
            String errorMessage = "Fehler beim Setzen des Sensors: " + e.getMessage();
            response.sendRedirect("SensorsView.jsp?message=" + java.net.URLEncoder.encode(errorMessage, "UTF-8") + "&type=error");
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
