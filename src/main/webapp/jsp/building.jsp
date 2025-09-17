<%@ page contentType="text/html;charset=UTF-8" %>
<%@ page import="java.util.*" %>
<%@ page import="de.hwg_lu.bwi520.beans.Floor" %>
<!DOCTYPE html>
<html>
<head>
  <title>Etage wählen</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/building.css">
</head>
<body>

<a class="back" href="<%=request.getContextPath()%>/buildings">&laquo; zurück</a>
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

</body>
</html>
