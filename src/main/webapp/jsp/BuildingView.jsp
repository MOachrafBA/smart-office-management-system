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
// ========== DATEN LADEN ==========
try {
    if (!myBuilding.isDataLoaded()) {
        myBuilding.loadBuildings();
    }
} catch (SQLException e) {
    myMessage.setAnyError("Fehler beim Laden der Gebäude: " + e.getMessage());
}
%>

<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Etage wählen</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/building.css">
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

        <h2>🏢 <jsp:getProperty name="myBuilding" property="selectedBuildingName" /> - Etage wählen</h2>
        <p>Wählen Sie eine Etage aus, um die Räume anzuzeigen:</p>

        <!-- Gebäude-Bild mit Hotspots -->
        <div class="img-wrap">
            <img src="<%=request.getContextPath()%>/img/building.png" alt="Gebäude"> <!-- context = http://localhost:8080/BWI520 -->
            <!-- Etagen-Hotspots (statisch für Demo) -->
            <a class="hotspot" style="top:66%;height:18%" href="<%=request.getContextPath()%>/jsp/RoomsAppl.jsp?floorId=1&buildingId=<jsp:getProperty name="mySmartOffice" property="currentBuilding" />" title="Erdgeschoss">
                <span class="hs-label">Erdgeschoss</span>
            </a>
            <a class="hotspot" style="top:43%;height:18%" href="<%=request.getContextPath()%>/jsp/RoomsAppl.jsp?floorId=2&buildingId=<jsp:getProperty name="mySmartOffice" property="currentBuilding" />" title="1. Obergeschoss">
                <span class="hs-label">1. Obergeschoss</span>
            </a>
            <a class="hotspot" style="top:20%;height:18%" href="<%=request.getContextPath()%>/jsp/RoomsAppl.jsp?floorId=3&buildingId=<jsp:getProperty name="mySmartOffice" property="currentBuilding" />" title="2. Obergeschoss">
                <span class="hs-label">2. Obergeschoss</span>
            </a>
        </div>
    </div>

    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/jsp/BuildingsView.jsp">🏠 Zurück zu Gebäuden</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>

</body>
</html>
