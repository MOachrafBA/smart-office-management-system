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
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/messages.css">
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
        
        <!-- Zentrales Popup für alle anderen Nachrichten -->
        <div id="messagePopup" class="message-popup" style="display: none;">
            <div class="popup-content">
                <span id="popupMessage"></span>
                <button onclick="closeMessagePopup()" class="popup-close">×</button>
            </div>
        </div>

        <h2>🏢 <jsp:getProperty name="myBuilding" property="selectedBuildingName" /> - Sensoren im <jsp:getProperty name="myRoom" property="selectedRoomName" /></h2>
        <p>Steuern Sie die Sensoren im ausgewählten Raum:</p>

        <!-- Sensoren-Tabelle -->
        <table class="tbl">
            <tr><th>Label</th><th>Typ</th><th>Wert</th><th>Zeit</th><th>Aktion</th></tr>
            <%
            // Echte Sensoren aus der Datenbank laden
            java.util.List<de.hwg_lu.bwi520.beans.SensorRow> sensors = mySmartOffice.getSensorsForCurrentRoom();
            String currentRoom = mySmartOffice.getCurrentRoom();
            System.err.println("DEBUG: SensorsView - currentRoom: " + currentRoom + ", sensors: " + sensors.size());
            if (sensors.isEmpty()) {
            %>
            <tr>
                <td colspan="5" style="text-align: center; color: #666;">
                    Keine Sensoren für diesen Raum gefunden.
                </td>
            </tr>
            <%
            } else {
                for (de.hwg_lu.bwi520.beans.SensorRow sensor : sensors) {
                    String currentValue = sensor.getValue() != null ? sensor.getValue() : "0";
                    String timestamp = sensor.getTs() != null ? sensor.getTs() : "N/A";
                    String unit = sensor.getUnit() != null ? sensor.getUnit() : "";
                    String typeInfo = sensor.getTypeKey() + " " + unit;
                    if (sensor.isWritable()) {
                        typeInfo += " [steuerbar]";
                    }
            %>
            <tr>
                <td><%=sensor.getLabel()%></td>
                <td><%=typeInfo%></td>
                <td class="val"><%=currentValue%></td>
                <td class="ts"><%=timestamp%></td>
                <td>
                    <% if (sensor.isWritable()) { %>
                        <form method="post" action="<%=request.getContextPath()%>/jsp/SensorsAppl.jsp" class="inline">
                            <input type="hidden" name="roomId" value="<jsp:getProperty name="mySmartOffice" property="currentRoom" />"/>
                            <input type="hidden" name="sensorId" value="<%=sensor.getId()%>"/>
                            <% if ("light".equals(sensor.getTypeKey()) || "blind_position".equals(sensor.getTypeKey())) { %>
                                <input type="range" name="desiredValue" min="0" max="100" step="1" value="<%=currentValue%>"/>
                            <% } else if ("plug_switch".equals(sensor.getTypeKey())) { %>
                                <select name="desiredValue">
                                    <option value="0" <%=currentValue.equals("0") ? "selected" : ""%>>aus</option>
                                    <option value="1" <%=currentValue.equals("1") ? "selected" : ""%>>an</option>
                                </select>
                            <% } else { %>
                                <input type="number" name="desiredValue" step="0.1" value="<%=currentValue%>"/>
                            <% } %>
                            <button type="submit" name="btnSet" value="Setzen">Setzen</button>
                        </form>
                    <% } else { %>
                        —
                    <% } %>
                </td>
            </tr>
            <%
                }
            }
            %>
        </table>
    </div>

    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/jsp/RoomsView.jsp">🏠 Zurück zu Räumen</a>
        <a href="<%=request.getContextPath()%>/jsp/requests.jsp">📋 Steuerbefehle</a>
        <a href="<%=request.getContextPath()%>/jsp/test-sensors.jsp">🧪 Test-Version</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>

    <script>
        // Popup-Funktionen
        function showMessagePopup(message, type) {
            const popup = document.getElementById('messagePopup');
            const messageSpan = document.getElementById('popupMessage');
            
            messageSpan.textContent = message;
            messageSpan.className = 'message-' + type;
            popup.style.display = 'block';
            
            // Auto-close nach 5 Sekunden
            setTimeout(closeMessagePopup, 5000);
        }
        
        function closeMessagePopup() {
            document.getElementById('messagePopup').style.display = 'none';
        }
        
        // Prüfe URL-Parameter für Nachrichten
        window.onload = function() {
            const urlParams = new URLSearchParams(window.location.search);
            const message = urlParams.get('message');
            const type = urlParams.get('type');
            
            if (message) {
                showMessagePopup(message, type || 'info');
            }
        };
    </script>

</body>
</html>
