<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<jsp:useBean id="myControlRequest" class="de.hwg_lu.bwi520.beans.ControlRequestBean" scope="request" />
<%
  // Lade die Steuerbefehle
  myControlRequest.loadRecentControlRequests();
%>
<!DOCTYPE html>
<html>
<head>
  <title>Letzte Steuerbefehle</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/sensors.css">
</head>
<body>
<h2>Letzte Steuerbefehle</h2>

<% if (myControlRequest.hasError()) { %>
  <div class="error-message">
    <%= myControlRequest.getErrorMessage() %>
  </div>
<% } %>

<table class="tbl">
  <tr>
    <th>ID</th>
    <th>Sensor</th>
    <th>Wert</th>
    <th>Benutzer</th>
    <th>Angefordert</th>
  </tr>
  <%= myControlRequest.getControlRequestsHtml() %>
</table>

<!-- Untere Navigation -->
<nav class="bottom-nav">
    <a href="<%=request.getContextPath()%>/jsp/SensorsView.jsp">🏠 Zurück zu Sensoren</a>
    <a href="<%=request.getContextPath()%>/jsp/RoomsView.jsp">🏢 Zurück zu Räumen</a>
    <span>| Smart Office Management System v1.0</span>
</nav>

<!-- Padding für fixed Navigation -->
<div class="nav-padding"></div>

</body>
</html>
