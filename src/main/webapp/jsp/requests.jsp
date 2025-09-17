<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.sql.*, de.hwg_lu.bwi520.jdbc.Db" %>
<!DOCTYPE html>
<html>
<head>
  <title>Letzte Steuerbefehle</title>
  <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
</head>
<body>
<a class="back" href="<%=request.getContextPath()%>/buildings">&laquo; zurück</a>
<h2>Letzte Steuerbefehle</h2>

<table class="tbl">
  <tr>
    <th>ID</th>
    <th>Sensor</th>
    <th>Wert</th>
    <th>Angefordert</th>
  </tr>
<%
  String sql =
    "SELECT cr.id, cr.requested_value, cr.created_at, " +
    "       s.id AS sensor_id, s.label, st.key " +
    "FROM control_request cr " +
    "JOIN sensor s       ON s.id = cr.sensor_id " +
    "JOIN sensor_type st ON st.id = s.type_id " +
    "ORDER BY cr.created_at DESC " +
    "LIMIT 50";

  try (Connection c = Db.get();
       PreparedStatement ps = c.prepareStatement(sql);
       ResultSet rs = ps.executeQuery()) {
    while (rs.next()) {
%>
  <tr>
    <td><%= rs.getLong("id") %></td>
    <td><%= rs.getInt("sensor_id") %> — <%= rs.getString("label") %> (<%= rs.getString("key") %>)</td>
    <td><%= rs.getObject("requested_value") %></td>
    <td><%= rs.getTimestamp("created_at") %></td>
  </tr>
<%
    }
  } catch (Exception e) {
    e.printStackTrace(new java.io.PrintWriter(out));
  }
%>
</table>
</body>
</html>
