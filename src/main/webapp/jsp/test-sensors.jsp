<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Test - Sensoren</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <script src="<%=request.getContextPath()%>/js/auto-refresh.js"></script>
</head>
<body>
<%
    // Statische Test-Daten für JSP-Test
    String roomId = "1";
    String floorId = "1";
    String buildingId = "1";
    String roomLabel = "Test-Raum 1-101";
    
    // Test-Sensoren
    java.util.List<de.hwg_lu.bwi520.beans.SensorRow> sensors = new java.util.ArrayList<>();
    sensors.add(new de.hwg_lu.bwi520.beans.SensorRow(1, "Licht", "light", "%", true, "75", "2024-01-15 14:30:00"));
    sensors.add(new de.hwg_lu.bwi520.beans.SensorRow(2, "Temperatur", "temperature", "°C", false, "22.5", "2024-01-15 14:30:00"));
    sensors.add(new de.hwg_lu.bwi520.beans.SensorRow(3, "Jalousien", "blind_position", "%", true, "0", "2024-01-15 14:30:00"));
    sensors.add(new de.hwg_lu.bwi520.beans.SensorRow(4, "Steckdose", "plug_switch", "", true, "1", "2024-01-15 14:30:00"));
%>

<a class="back" href="<%=request.getContextPath()%>/rooms?floorId=<%=floorId%>&buildingId=<%=buildingId%>">&laquo; zurück</a>
<a class="back" href="<%=request.getContextPath()%>/jsp/requests.jsp">Letzte Steuerbefehle</a>

<h2>Test - Sensoren im Raum <%=roomLabel%></h2>

<table class="tbl">
    <tr><th>Label</th><th>Typ</th><th>Wert</th><th>Zeit</th><th>Aktion</th></tr>
<%
    for (de.hwg_lu.bwi520.beans.SensorRow s : sensors) {
        String k = s.getTypeKey();
        boolean writable = s.isWritable();
        String unit = (s.getUnit()==null) ? "" : (" " + s.getUnit());
%>
    <tr>
        <td><%= s.getLabel() %></td>
        <td><%= k %><%= unit %> <%= writable ? "[steuerbar]" : "" %></td>
        <td class="val"><%= (s.getValue()==null) ? "—" : s.getValue() %></td>
        <td class="ts"><%= (s.getTs()==null) ? "—" : s.getTs() %></td>
        <td>
        <% if (writable) { %>
            <form method="post" action="<%=request.getContextPath()%>/sensors" class="inline">
                <input type="hidden" name="roomId" value="<%= roomId %>"/>
                <input type="hidden" name="floorId" value="<%= floorId %>"/>
                <input type="hidden" name="buildingId" value="<%= buildingId %>"/>
                <input type="hidden" name="sensorId" value="<%= s.getId() %>"/>

                <% if ("light".equals(k) || "blind_position".equals(k)) { %>
                    <input type="range" name="desiredValue" min="0" max="100" step="1"
                           value="<%= (s.getValue()==null) ? "0" : s.getValue() %>"/>
                <% } else if ("temperature_setpoint".equals(k)) { %>
                    <input type="number" name="desiredValue" step="0.1"
                           value="<%= (s.getValue()==null) ? "21.0" : s.getValue() %>"/>
                <% } else if ("plug_switch".equals(k)) { %>
                    <input type="hidden" name="desiredValue" value="0"/>
                    <label><input type="checkbox" name="desiredValue" value="1"
                           <%= "1".equals(s.getValue()) ? "checked" : "" %> /> an/aus</label>
                <% } else { %>
                    <input type="number" name="desiredValue" step="0.1"
                           value="<%= (s.getValue()==null) ? "0" : s.getValue() %>"/>
                <% } %>

                <button type="submit">Setzen</button>
            </form>
        <% } %>
        </td>
    </tr>
<% } %>
</table>

<!-- Debug-Informationen -->
<div style="margin-top: 20px; padding: 10px; background: #f0f0f0; border-radius: 5px;">
    <h3>Debug-Informationen:</h3>
    <p><strong>Room ID:</strong> <%=roomId%></p>
    <p><strong>Floor ID:</strong> <%=floorId%></p>
    <p><strong>Building ID:</strong> <%=buildingId%></p>
    <p><strong>Room Label:</strong> <%=roomLabel%></p>
    <p><strong>Anzahl Sensoren:</strong> <%=sensors.size()%></p>
    <p><strong>Auto-Refresh:</strong> Aktiv (alle 10 Sekunden)</p>
</div>
</body>
</html>
