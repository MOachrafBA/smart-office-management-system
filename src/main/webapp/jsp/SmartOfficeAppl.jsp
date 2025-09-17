<%@page import="de.hwg_lu.bwi520.beans.SmartOfficeBean"%>
<jsp:useBean id="sob" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session"/>
<%
  // 1) Query-Parameter lesen (Navigation + Formdaten)
  String show = request.getParameter("show"); if (show==null) show="";
  String buildingId = request.getParameter("buildingId");
  String floorId    = request.getParameter("floorId");
  String roomId     = request.getParameter("roomId");
  String sensorId   = request.getParameter("sensorId");
  // desiredValue kann als hidden+checkbox doppelt ankommen -> letztes nehmen
  String[] arr = request.getParameterValues("desiredValue");
  String desired = (arr==null||arr.length==0) ? null : arr[arr.length-1];

  // 2) Zustand in der Session-Bean merken (damit Views darauf zugreifen)
  if (buildingId != null) sob.setBuildingId(buildingId);
  if (floorId    != null) sob.setFloorId(floorId);
  if (roomId     != null) sob.setRoomId(roomId);

  // 3) Routing per show=...
  if ("set".equals(show)) {
    // Schreiben (Mock): neuer Wert in sensor_value + Log in control_request
    sob.setValue(sensorId, desired);
    // PRG-Pattern: Redirect zurück zur Anzeige (verhindert Doppel-Submit)
    response.sendRedirect("./SmartOfficeAppl.jsp?show=sensors&buildingId="+buildingId+"&floorId="+floorId+"&roomId="+roomId);
  } else if ("floors".equals(show)) {
    out.print("<a class='back' href='./SmartOfficeAppl.jsp'>&laquo; zurück</a>");
    out.print("<h2>Etagen</h2>");
    // HTML wird von der Bean erzeugt (DB-Lesen + Stringbuilder)
    out.print(sob.getFloorsHtml());
  } else if ("rooms".equals(show)) {
    out.print("<a class='back' href='./SmartOfficeAppl.jsp?show=floors&buildingId="+buildingId+"'>&laquo; zurück</a>");
    out.print("<h2>Räume</h2>");
    out.print(sob.getRoomsHtml());
  } else if ("sensors".equals(show)) {
    out.print(sob.getSensorsTableHtml());
  } else {
    // Default: Gebäudeübersicht
    out.print("<h2>Gebäude</h2>");
    out.print(sob.getBuildingsHtml());
  }
%>
