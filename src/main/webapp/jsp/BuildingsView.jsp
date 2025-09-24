<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.SQLException" %>
<%@ page import="de.hwg_lu.bwi520.beans.*" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Gebäude</title>
    <link rel="stylesheet" href="/BWI520/css/app.css">
    <link rel="stylesheet" href="/BWI520/css/buildings.css">
    <link rel="stylesheet" href="/BWI520/css/common.css">
    <link rel="stylesheet" href="/BWI520/css/messages.css">
</head>
<body>
    <%-- Beans einbinden --%>
    <jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
    <jsp:useBean id="myBuilding" class="de.hwg_lu.bwi520.beans.BuildingBean" scope="session" />
    <jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

    <%-- Login-Check --%>
    <jsp:getProperty name="mySmartOffice" property="loginCheckRedirectHtml" />

    <%
        // Gebäude laden, falls noch nicht geschehen
        if (!myBuilding.isDataLoaded()) {
            try {
                myBuilding.loadBuildings();
            } catch (SQLException e) {
                myMessage.setAnyError("Datenbankfehler beim Laden der Gebäude: " + e.getMessage());
            }
        }
    %>

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
        <%-- Nachrichten anzeigen - nur bei Anmeldung/Registrierung --%>
        <% if (mySmartOffice.isLoggedIn()) { %>
            <jsp:getProperty name="myMessage" property="messageHtml" />
        <% } %>

        <h2>🏢 Gebäude-Übersicht</h2>
        <p>Wählen Sie ein Gebäude aus, um die Etagen anzuzeigen:</p>

        <%= myBuilding.getBuildingsHtml() %>
    </div>

    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/jsp/BuildingsView.jsp">🏠 Startseite</a>
        <a href="<%=request.getContextPath()%>/jsp/test-buildings.jsp">🧪 Test-Version</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>

</body>
</html>