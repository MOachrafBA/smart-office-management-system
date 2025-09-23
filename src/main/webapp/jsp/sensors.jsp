<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <title>Smart Office - Sensoren</title>
  <link rel="stylesheet" href="/BWI520/css/app.css">
  <link rel="stylesheet" href="/BWI520/css/common.css">
  <script src="/BWI520/js/auto-refresh.js"></script>
</head>
<body>

  <!-- Banner -->
  <div class="banner">
    <h1>🏢 Smart Office Management System</h1>
    <p>Intelligente Gebäudeüberwachung und -steuerung</p>
  </div>

  <!-- Hauptinhalt -->
  <div class="main-content">
<%
  String roomId     = request.getParameter("roomId");
  String floorId    = request.getParameter("floorId");
  String buildingId = request.getParameter("buildingId");
  String roomLabel  = (String) request.getAttribute("roomLabel");

  // für sichere Verwendung in Attributen:
  String fParam = (floorId == null)    ? "" : floorId;
  String bParam = (buildingId == null) ? "" : buildingId;
%>

<h2>Sensoren im Raum <%= (roomLabel != null && !roomLabel.isBlank()) ? roomLabel : "#" + roomId %></h2>

<table class="tbl">
  <tr><th>Label</th><th>Typ</th><th>Wert</th><th>Zeit</th><th>Aktion</th></tr>
<%
  var list = (java.util.List<de.hwg_lu.bwi520.beans.SensorRow>) request.getAttribute("sensors");
  for (var s : list) {
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
        <input type="hidden" name="roomId"     value="<%= roomId %>"/>
        <input type="hidden" name="floorId"    value="<%= fParam %>"/>
        <input type="hidden" name="buildingId" value="<%= bParam %>"/>
        <input type="hidden" name="sensorId"   value="<%= s.getId() %>"/>

        <%--Range-Slider: 0-100% Helligkeit/Verschattung Werte: 0% = aus/geschlossen, 100% = an/offen --%>
        <% if ("light".equals(k) || "blind_position".equals(k)) { %>
          <input type="range"  name="desiredValue" min="0" max="100" step="1"
                 value="<%= (s.getValue()==null) ? "0" : s.getValue() %>"/>
        
        <%--Dezimalstellen für Temperatur. Präzision: 0.1°C Schritte --%>
        <% } else if ("temperature_setpoint".equals(k)) { %>
          <input type="number" name="desiredValue" step="0.1"
                 value="<%= (s.getValue()==null) ? "21.0" : s.getValue() %>"/>
        
        <%---Checkbox: Ein/Aus-Schalter --%>
        <% } else if ("plug_switch".equals(k)) { %>
          <input type="hidden" name="desiredValue" value="0"/>
          <label><input type="checkbox" name="desiredValue" value="1"
                 <%= "1".equals(s.getValue()) ? "checked" : "" %> /> an/aus</label>
        
        <%--für alle andere Sensoren --%>
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

  </div>

  <!-- Untere Navigation - Ganz unten auf der Webseite -->
  <nav class="bottom-nav">
    <a href="<%=request.getContextPath()%>/rooms?floorId=<%= fParam %>&buildingId=<%= bParam %>">🏠 Zurück zu Räumen</a>
    <a href="<%=request.getContextPath()%>/jsp/test-sensors.jsp">🧪 Test-Version</a>
    <span>| Smart Office Management System v1.0</span>
  </nav>

  <!-- Padding für fixed Navigation -->
  <div class="nav-padding"></div>

</body>
</html>
