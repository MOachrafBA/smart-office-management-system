<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="de.hwg_lu.bwi520.beans.Floor" %>
<!DOCTYPE html>
<html>
<head>
    <title>Test - Etage wählen</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/building.css">
</head>
<body>

<a class="back" href="<%=request.getContextPath()%>/buildings">&laquo; zurück</a>
<h2>Test - Etage wählen</h2>

<%
    // Statische Test-Daten für JSP-Test
    List<Floor> floors = new ArrayList<>();
    floors.add(new Floor(1, 1, "Erdgeschoss", 0));
    floors.add(new Floor(2, 1, "1. Obergeschoss", 1));
    floors.add(new Floor(3, 1, "2. Obergeschoss", 2));
    
    Integer buildingId = 1;
    // Positionen pro Etage in % (von oben): EG, 1. OG, 2. OG
    double[] tops = {66, 43, 20};
    double bandH = 18;
%>

<div class="img-wrap">
    <img src="<%=request.getContextPath()%>/img/building.png" alt="Gebäude">
    <% for (int i=0; i<floors.size() && i<tops.length; i++) {
         Floor f = floors.get(i);
    %>
        <a class="hotspot"
           style="top:<%=tops[i]%>%;height:<%=bandH%>%"
           href="<%=request.getContextPath()%>/rooms?floorId=<%=f.getId()%>&buildingId=<%=buildingId%>"
           title="<%=f.getName()%>">
            <span class="hs-label"><%=f.getName()%></span>
        </a>
    <% } %>
</div>

<!-- Debug-Informationen -->
<div style="margin-top: 20px; padding: 10px; background: #f0f0f0; border-radius: 5px;">
    <h3>Debug-Informationen:</h3>
    <p><strong>Anzahl Etagen:</strong> <%=floors.size()%></p>
    <p><strong>Building ID:</strong> <%=buildingId%></p>
    <p><strong>Context Path:</strong> <%=request.getContextPath()%></p>
</div>
</body>
</html>
