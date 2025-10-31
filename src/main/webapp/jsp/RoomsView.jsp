<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
// ========== DATEN LADEN ==========
try {
    if (!myBuilding.isDataLoaded()) {
        myBuilding.loadBuildings();
    }
    
    // Räume für aktuelle Etage aus Datenbank laden
    String currentFloor = mySmartOffice.getCurrentFloor();
    if (currentFloor == null || currentFloor.isEmpty()) {
        currentFloor = "1"; // Standard: Erdgeschoss
    }
    
    // Räume für diese Etage aus Datenbank laden
    myRoom.loadRoomsByFloor(Integer.parseInt(currentFloor));
    
} catch (SQLException e) {
    myMessage.setAnyError("Fehler beim Laden der Daten: " + e.getMessage());
} catch (NumberFormatException e) {
    myMessage.setAnyError("Ungültige Etagen-ID: " + e.getMessage());
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Räume</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/rooms.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/messages.css">
</head>
<body>

    <!-- Banner -->
    <div class="banner">
        <h1>🏢 Smart Office Management System</h1>
        <p>Intelligente Gebäudeüberwachung und -steuerung</p>
        <div class="top-nav">
            <% if (mySmartOffice.isLoggedIn()) { %>
                <span class="user-info">👤 <jsp:getProperty name="mySmartOffice" property="currentUser" /></span>
                <a href="<%=request.getContextPath()%>/jsp/LogoutAppl.jsp" class="logout-btn">🚪 Abmelden</a>
            <% } else { %>
                <a href="<%=request.getContextPath()%>/jsp/LoginView.jsp" class="login-btn">🔐 Anmelden</a>
                <a href="<%=request.getContextPath()%>/jsp/RegisterView.jsp" class="register-btn">📝 Registrieren</a>
            <% } %>
        </div>
    </div>

    <!-- Hauptinhalt -->
    <div class="main-content">
        <!-- Nachricht anzeigen - nur bei Anmeldung/Registrierung -->
        <% if (mySmartOffice.isLoggedIn()) { %>
            <jsp:getProperty name="myMessage" property="messageHtml" />
        <% } %>

        <h2>🏢 <jsp:getProperty name="myBuilding" property="selectedBuildingName" /> - Etage <jsp:getProperty name="mySmartOffice" property="currentFloor" /> - Räume</h2>
        <p>Wählen Sie einen Raum aus, um die Sensoren anzuzeigen:</p>

        <!-- Raum-Grid -->
        <%= myRoom.getRoomsHtml() %>
    </div>

    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/jsp/BuildingView.jsp">🏠 Zurück zu Etagen</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>

</body>
</html>
