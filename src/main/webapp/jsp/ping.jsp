<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.sql.*" %>
<%@ page import="de.hwg_lu.bwi520.jdbc.Db" %>

<%--DB-Test --%>

<%
try (Connection c = Db.get();
     Statement s = c.createStatement();
     ResultSet r = s.executeQuery("SELECT current_schema(), version()")) {
  r.next();
  out.println("DB OK — schema=" + r.getString(1) + "<br/>" + r.getString(2));
} catch (Exception e) {
	e.printStackTrace(new java.io.PrintWriter(out));
}
%>
