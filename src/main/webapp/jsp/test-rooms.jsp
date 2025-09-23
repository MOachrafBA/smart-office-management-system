<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*, de.hwg_lu.bwi520.beans.Room, de.hwg_lu.bwi520.util.ImageResolver" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test - Räume</title>
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/plan.css">
</head>
<body>
<a class="back" href="<%=request.getContextPath()%>/building?buildingId=1">&laquo; zurück</a>
<h2>Test - Räume</h2>

<%
    // Statische Test-Daten für JSP-Test
    List<Room> rooms = new ArrayList<>();
    rooms.add(new Room(1, 1, "1-101", "Büro 101"));
    rooms.add(new Room(2, 1, "1-102", "Meeting-Raum"));
    rooms.add(new Room(3, 1, "1-CR2", "Konferenzraum"));
    rooms.add(new Room(4, 1, "1-F1", "Flur"));
    
    String floorId = "1";
    String buildingId = "1";
    String ctx = request.getContextPath();
%>

<div class="room-grid">
<% for (Room r : rooms) {
     // ImageResolver testen
     String img = ImageResolver.roomPhoto(request, r.getCode(), null);
%>
    <a class="room-card"
       href="<%=ctx%>/sensors?roomId=<%=r.getId()%>&floorId=<%=floorId%>&buildingId=<%=buildingId%>">
        <img class="room-photo" src="<%=img%>" alt="<%=r.getCode()%> — <%=r.getName()%>">
        <div class="room-title"><%=r.getCode()%> — <%=r.getName()%></div>
    </a>
<% } %>
</div>

<!-- Debug-Informationen -->
<div style="margin-top: 20px; padding: 10px; background: #f0f0f0; border-radius: 5px;">
    <h3>Debug-Informationen:</h3>
    <p><strong>Anzahl Räume:</strong> <%=rooms.size()%></p>
    <p><strong>Floor ID:</strong> <%=floorId%></p>
    <p><strong>Building ID:</strong> <%=buildingId%></p>
    <p><strong>Context Path:</strong> <%=ctx%></p>
    
    <h4>ImageResolver Test:</h4>
    <% for (Room r : rooms) { %>
        <p><strong><%=r.getCode()%>:</strong> <%=ImageResolver.roomPhoto(request, r.getCode(), null)%></p>
    <% } %>
</div>
</body>
</html>
