<%@ page import="de.hwg_lu.bwi520.beans.*" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

<%
// ========== LOGOUT DURCHFÜHREN ==========
try {
    // SmartOffice Bean zurücksetzen
    mySmartOffice.logout();
    
    // Keine Abmelde-Nachricht anzeigen
    myMessage.clearMessage();
    
    // Weiterleitung zur Login-Seite
    response.sendRedirect("LoginView.jsp");
    
} catch (Exception e) {
    // ========== FEHLERBEHANDLUNG ==========
    myMessage.setAnyError("Fehler beim Abmelden: " + e.getMessage());
    response.sendRedirect("LoginView.jsp");
}
%>
