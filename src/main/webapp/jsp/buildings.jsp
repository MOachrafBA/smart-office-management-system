<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><title>Gebäude</title></head><body>
<h2>Gebäude</h2>
<ul>
<% var list = (java.util.List<de.hwg_lu.bwi520.beans.Building>) request.getAttribute("buildings");
   for (var b : list) { %>
  <li><a href="<%=request.getContextPath()%>/building?buildingId=<%=b.getId()%>">
  <%= b.getName() %></a></li>
<% } %>
</ul>
</body></html>