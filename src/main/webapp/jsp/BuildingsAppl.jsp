<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.beans.*" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myBuilding" class="de.hwg_lu.bwi520.beans.BuildingBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

<%
// ========== PARAMETER EINLESEN ==========
String btnRefresh = request.getParameter("btnRefresh");

// ========== NULL-CHECK ==========
if (btnRefresh == null) btnRefresh = "";

// ========== AKTIONSWEICHE ==========
try {
    if (btnRefresh.equals("Seite neu laden")) {
        // ========== SEITE NEU LADEN ==========
        myMessage.clearMessage();
        response.sendRedirect("BuildingsView.jsp");
        
    } else {
        // ========== ELSE-ZWEIG ==========
        // Standard-Willkommensnachricht
        myMessage.clearMessage();
        response.sendRedirect("BuildingsView.jsp");
    }
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("BuildingsView.jsp");
}
%>