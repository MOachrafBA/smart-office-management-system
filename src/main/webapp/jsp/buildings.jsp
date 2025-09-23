<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="de.hwg_lu.bwi520.beans.Building" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Gebäude</title>
    <link rel="stylesheet" href="/BWI520/css/app.css">
    <link rel="stylesheet" href="/BWI520/css/common.css">
    <link rel="stylesheet" href="/BWI520/css/buildings.css">
</head>
<body>
    
    <!-- Banner -->
    <div class="banner">
        <h1>🏢 Smart Office Management System</h1>
        <p>Intelligente Gebäudeüberwachung und -steuerung</p>
    </div>
        
    <!--  Hauptinhalt -->
    <div class="main-content">
        <h2>🏢 Gebäude-Übersicht</h2>
        <p>Wählen Sie ein Gebäude aus, um die Etagen anzuzeigen:</p>
        
        <ul class="building-list">
            <!-- Liste der Gebäude anzeigen -->
            <% var list = (java.util.List<de.hwg_lu.bwi520.beans.Building>) request.getAttribute("buildings");
               int colorIndex = 0;
               String[] colorClasses = {"building-blue", "building-green", "building-orange", "building-pink", "building-purple"};
               String[] icons = {"🏢", "🏬", "🔬", "🏭", "🏛️"};
               
               for (var b : list) { 
                   String colorClass = colorClasses[colorIndex % colorClasses.length];
                   String icon = icons[colorIndex % icons.length];
                   colorIndex++;
            %>
                <li class="building-item">
                    <a href="<%=request.getContextPath()%>/building?buildingId=<%=b.getId()%>" 
                       class="building-link <%=colorClass%>">
                        <%=icon%> <%= b.getName() %>
                    </a>
                </li>
            <% } %>
        </ul>
    </div>
    
    <!-- Untere Navigation - Ganz unten auf der Webseite -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/buildings">🏠 Seite neu laden</a>
        <a href="<%=request.getContextPath()%>/jsp/test-buildings.jsp">🧪 Test-Version</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>
    
</body>
</html>