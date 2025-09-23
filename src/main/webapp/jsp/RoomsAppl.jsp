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
String floorId = request.getParameter("floorId");

// ========== NULL-CHECK ==========
if (btnBack == null) btnBack = "";
if (btnRefresh == null) btnRefresh = "";
if (floorId == null) floorId = "";

// ========== AKTIONSWEICHE ==========
try {
    if (btnBack.equals("Zurück zu Etagen")) {
        // ========== ZURÜCK ZU ETAGEN ==========
        mySmartOffice.setCurrentFloor("");
        myMessage.setBuildingSelectedSuccess(myBuilding.getSelectedBuildingName());
        response.sendRedirect("BuildingView.jsp");
        
    } else if (btnRefresh.equals("Seite neu laden")) {
        // ========== SEITE NEU LADEN ==========
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setFloorSelectedSuccess(mySmartOffice.getCurrentFloor());
        response.sendRedirect("RoomsView.jsp");
        
    } else if (!floorId.isEmpty()) {
        // ========== ETAGE AUSGEWÄHLT ==========
        // Aktuelle Etage in SmartOfficeBean setzen
        mySmartOffice.setCurrentFloor(floorId);
        
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setFloorSelectedSuccess(mySmartOffice.getCurrentFloor());
        response.sendRedirect("RoomsView.jsp");
        
    } else {
        // ========== ELSE-ZWEIG ==========
        // Standard-Weiterleitung
        // Keine Nachricht setzen - nur bei Anmeldung/Registrierung
        // myMessage.setFloorSelectedSuccess(mySmartOffice.getCurrentFloor());
        response.sendRedirect("RoomsView.jsp");
    }
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("BuildingView.jsp");
}
%>
