<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><head><title>Etagen</title></head><body>
<h2>Etagen</h2>
<ul>
<% var list=(java.util.List<de.hwg_lu.bwi520.beans.Floor>)request.getAttribute("floors");
   for (var f: list) { %>
  <li><a href="<%=request.getContextPath()%>/rooms?floorId=<%=f.getId()%>"><%=f.getName()%></a></li>
<% } %>
</ul>
</body></html>