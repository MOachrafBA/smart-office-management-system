<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*, de.hwg_lu.bwi520.beans.Room, de.hwg_lu.bwi520.util.ImageResolver" %>
<!DOCTYPE html>
<html>
<head>
  <title>Smart Office - Räume</title>
  <meta name="viewport" content="width=device-width, initial-scale=1" />
  <link rel="stylesheet" href="/BWI520/css/app.css">
  <link rel="stylesheet" href="/BWI520/css/common.css">
  <link rel="stylesheet" href="/BWI520/css/plan.css">
</head>
<body>

  <!-- Banner -->
  <div class="banner">
    <h1>🏢 Smart Office Management System</h1>
    <p>Intelligente Gebäudeüberwachung und -steuerung</p>
  </div>

  <!-- Hauptinhalt -->
  <div class="main-content">
    <h2>Räume</h2>

<%
  @SuppressWarnings("unchecked")
  List<Room> rooms = (List<Room>) request.getAttribute("rooms");
  String floorId    = request.getParameter("floorId");
  String buildingId = request.getParameter("buildingId");
  String ctx        = request.getContextPath();
%>

<div class="room-grid">
<% for (Room r : rooms) {
     String img = ImageResolver.roomPhoto(request, r.getCode(), null); // <— hier null
%>
  <a class="room-card"
     href="<%=ctx%>/sensors?roomId=<%=r.getId()%>&floorId=<%=floorId%>&buildingId=<%=buildingId%>">
    <img class="room-photo" src="<%=img%>" alt="<%=r.getCode()%> — <%=r.getName()%>">
    <div class="room-title"><%=r.getCode()%> — <%=r.getName()%></div>
  </a>
<% } %>
</div>

  </div>

  <!-- Untere Navigation - Ganz unten auf der Webseite -->
  <nav class="bottom-nav">
    <a href="<%=request.getContextPath()%>/building?buildingId=<%=request.getParameter("buildingId")%>">🏠 Zurück zu Etagen</a>
    <a href="<%=request.getContextPath()%>/jsp/test-rooms.jsp">🧪 Test-Version</a>
    <span>| Smart Office Management System v1.0</span>
  </nav>

  <!-- Padding für fixed Navigation -->
  <div class="nav-padding"></div>

</body>
</html>
