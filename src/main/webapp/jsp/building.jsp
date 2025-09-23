<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="de.hwg_lu.bwi520.beans.Floor" %>
<!DOCTYPE html>
<html>
<head>
  <title>Smart Office - Etage wählen</title>
  <link rel="stylesheet" href="/BWI520/css/app.css">
  <link rel="stylesheet" href="/BWI520/css/common.css">
  <link rel="stylesheet" href="/BWI520/css/building.css">
</head>
<body>

  <!-- Banner -->
  <div class="banner">
    <h1>🏢 Smart Office Management System</h1>
    <p>Intelligente Gebäudeüberwachung und -steuerung</p>
  </div>

  <!-- Hauptinhalt -->
  <div class="main-content">
    <h2>Etage wählen</h2>

<%
  @SuppressWarnings("unchecked")
  List<Floor> floors = (List<Floor>) request.getAttribute("floors");
  Integer buildingId = (Integer) request.getAttribute("buildingId");
  // Fallback, falls als Query-Param übergeben
  if (buildingId == null && request.getParameter("buildingId") != null) {
    buildingId = Integer.valueOf(request.getParameter("buildingId"));
  }
  // Positionen pro Etage in % (von oben): EG, 1. OG, 2. OG – bei Bedarf feinjustieren
  double[] tops = {66, 43, 20};
  double bandH  = 18;
%>

<div class="img-wrap">
  <!-- Bild des Gebäudes anzeigen -->
  <img src="<%=request.getContextPath()%>/img/building.png" alt="Gebäude">
  <% for (int i=0; i<floors.size() && i<tops.length; i++) {
       Floor f = floors.get(i);
  %>
    <!-- Etage anzeigen via Hotspot + klickbare Bereiche für jede Etage+ Link zu den Räumen der Etage-->
    <a class="hotspot"
       style="top:<%=tops[i]%>%;height:<%=bandH%>%"
       href="<%=request.getContextPath()%>/rooms?floorId=<%=f.getId()%>&buildingId=<%=buildingId%>"
       title="<%=f.getName()%>">
      <span class="hs-label"><%=f.getName()%></span>
    </a>
  <% } %>
</div>

  </div>

  <!-- Untere Navigation - Ganz unten auf der Webseite -->
  <nav class="bottom-nav">
    <a href="<%=request.getContextPath()%>/buildings">🏠 Zurück zu Gebäuden</a>
    <a href="<%=request.getContextPath()%>/jsp/test-building.jsp">🧪 Test-Version</a>
    <span>| Smart Office Management System v1.0</span>
  </nav>

  <!-- Padding für fixed Navigation -->
  <div class="nav-padding"></div>

</body>
</html>
