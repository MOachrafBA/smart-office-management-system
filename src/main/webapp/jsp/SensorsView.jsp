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
    <title>Smart Office - Sensoren</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
    <script src="<%=request.getContextPath()%>/js/auto-refresh.js"></script>
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

        <h2>🏢 <jsp:getProperty name="myBuilding" property="selectedBuildingName" /> - Sensoren im <jsp:getProperty name="mySmartOffice" property="currentRoom" /></h2>
        <p>Steuern Sie die Sensoren im ausgewählten Raum:</p>

        <!-- Sensoren-Tabelle -->
        <table class="tbl">
            <tr><th>Label</th><th>Typ</th><th>Wert</th><th>Zeit</th><th>Aktion</th></tr>
            <tr>
                <td>Licht</td>
                <td>light % [steuerbar]</td>
                <td class="val">75</td>
                <td class="ts">2024-01-15 14:30:00</td>
                <td>
                    <form method="post" action="<%=request.getContextPath()%>/jsp/SensorsAppl.jsp" class="inline">
                        <input type="hidden" name="roomId" value="1"/>
                        <input type="hidden" name="floorId" value="1"/>
                        <input type="hidden" name="buildingId" value="<jsp:getProperty name="mySmartOffice" property="currentBuilding" />"/>
                        <input type="hidden" name="sensorId" value="1"/>
                        <input type="range" name="desiredValue" min="0" max="100" step="1" value="75"/>
                        <button type="submit" name="btnSet" value="Setzen">Setzen</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>Temperatur</td>
                <td>temperature °C</td>
                <td class="val">22.5</td>
                <td class="ts">2024-01-15 14:30:00</td>
                <td>—</td>
            </tr>
            <tr>
                <td>Jalousien</td>
                <td>blind_position % [steuerbar]</td>
                <td class="val">0</td>
                <td class="ts">2024-01-15 14:30:00</td>
                <td>
                    <form method="post" action="<%=request.getContextPath()%>/jsp/SensorsAppl.jsp" class="inline">
                        <input type="hidden" name="roomId" value="1"/>
                        <input type="hidden" name="floorId" value="1"/>
                        <input type="hidden" name="buildingId" value="<jsp:getProperty name="mySmartOffice" property="currentBuilding" />"/>
                        <input type="hidden" name="sensorId" value="3"/>
                        <input type="range" name="desiredValue" min="0" max="100" step="1" value="0"/>
                        <button type="submit" name="btnSet" value="Setzen">Setzen</button>
                    </form>
                </td>
            </tr>
            <tr>
                <td>Steckdose</td>
                <td>plug_switch [steuerbar]</td>
                <td class="val">1</td>
                <td class="ts">2024-01-15 14:30:00</td>
                <td>
                    <form method="post" action="<%=request.getContextPath()%>/jsp/SensorsAppl.jsp" class="inline">
                        <input type="hidden" name="roomId" value="1"/>
                        <input type="hidden" name="floorId" value="1"/>
                        <input type="hidden" name="buildingId" value="<jsp:getProperty name="mySmartOffice" property="currentBuilding" />"/>
                        <input type="hidden" name="sensorId" value="4"/>
                        <select name="desiredValue">
                            <option value="0">aus</option>
                            <option value="1" selected>an</option>
                        </select>
                        <button type="submit" name="btnSet" value="Setzen">Setzen</button>
                    </form>
                </td>
            </tr>
        </table>
    </div>

    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/jsp/RoomsView.jsp">🏠 Zurück zu Räumen</a>
        <a href="<%=request.getContextPath()%>/jsp/test-sensors.jsp">🧪 Test-Version</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>

</body>
</html>
