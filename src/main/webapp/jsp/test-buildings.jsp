<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="de.hwg_lu.bwi520.beans.Building" %>
<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Gebäude</title>
    <link rel="stylesheet" href="/BWI520/css/app.css">
    <link rel="stylesheet" href="/BWI520/css/buildings.css">
</head>
<body>
    
    <!-- 2. Banner -->
    <div class="banner">
        <h1>🏢 Smart Office Management System</h1>
        <p>Intelligente Gebäudeüberwachung und -steuerung</p>
    </div>
        
    <!-- 4. Hauptinhalt -->
    <div class="main-content">
        <h2>🏢 Gebäude-Übersicht</h2>
        <p>Wählen Sie ein Gebäude aus, um die Etagen anzuzeigen:</p>
        
        <ul class="building-list">
            <!-- Statische Test-Daten für JSP-Test -->
            <li class="building-item">
                <a href="<%=request.getContextPath()%>/building?buildingId=1" 
                   class="building-link building-blue">
                    🏢 Hauptgebäude
                </a>
            </li>
            <li class="building-item">
                <a href="<%=request.getContextPath()%>/building?buildingId=2" 
                   class="building-link building-green">
                    🏬 Nebengebäude
                </a>
            </li>
            <li class="building-item">
                <a href="<%=request.getContextPath()%>/building?buildingId=3" 
                   class="building-link building-orange">
                    🔬 Laborgebäude
                </a>
            </li>
        </ul>
        
        <!-- Debug-Informationen -->
        <div style="margin-top: 30px; padding: 15px; background: #f0f0f0; border-radius: 8px;">
            <h3>🔧 Debug-Informationen:</h3>
            <p><strong>Context Path:</strong> <%=request.getContextPath()%></p>
            <p><strong>Request URI:</strong> <%=request.getRequestURI()%></p>
            <p><strong>Server Name:</strong> <%=request.getServerName()%></p>
            <p><strong>Server Port:</strong> <%=request.getServerPort()%></p>
        </div>
    </div>
    
    <!-- 5. Untere Navigation - Ganz unten auf der Webseite -->
    <nav class="bottom-nav">
        <a href="<%=request.getContextPath()%>/buildings">🏠 Zurück zur Startseite</a>
        <a href="<%=request.getContextPath()%>/jsp/test-buildings.jsp">🔄 Seite neu laden</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>
</body>
</html>
