<%@ page import="de.hwg_lu.bwi520.beans.*" %>
<%@ page import="de.hwg_lu.bwi520.classes.UserDao" %>
<%@ page import="java.sql.SQLException" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />
<jsp:useBean id="myUser" class="de.hwg_lu.bwi520.beans.User" scope="session" />

<%
// ========== PARAMETER EINLESEN ==========
String btnLogin = request.getParameter("btnLogin");
String btnRegister = request.getParameter("btnRegister");
String username = request.getParameter("username");
String password = request.getParameter("password");

// ========== NULL-CHECK ==========
if (btnLogin == null) btnLogin = "";
if (btnRegister == null) btnRegister = "";
if (username == null) username = "";
if (password == null) password = "";

// ========== AKTIONSWEICHE ==========
try {
    if (btnLogin.equals("Anmelden")) {
        // ========== ANMELDUNG DURCHFÜHREN ==========
        if (username.isEmpty() || password.isEmpty()) {
            myMessage.setLoginFailed();
            response.sendRedirect("LoginView.jsp");
            return;
        }
        
        try {
            // Datenbankanmeldung prüfen
            UserDao userDao = new UserDao();
            User authenticatedUser = userDao.authenticate(username, password);
            
            if (authenticatedUser != null) {
                // Anmeldung erfolgreich
                mySmartOffice.login();
                mySmartOffice.setCurrentUser(username);
                
                // User-Daten in Session speichern
                myUser.setId(authenticatedUser.getId());
                myUser.setUsername(authenticatedUser.getUsername());
                myUser.setEmail(authenticatedUser.getEmail());
                myUser.setFirstName(authenticatedUser.getFirstName());
                myUser.setLastName(authenticatedUser.getLastName());
                myUser.setRole(authenticatedUser.getRole());
                myUser.setActive(authenticatedUser.isActive());
                
                // Letzten Login aktualisieren
                userDao.updateLastLogin(authenticatedUser.getId());
                
                // Erfolgsnachricht
                myMessage.setLoginSuccess(username);
                response.sendRedirect("BuildingsView.jsp");
            } else {
                // Anmeldung fehlgeschlagen
                myMessage.setLoginFailed();
                response.sendRedirect("LoginView.jsp");
            }
        } catch (SQLException e) {
            // Datenbankfehler
            myMessage.setAnyError("Datenbankfehler bei der Anmeldung: " + e.getMessage());
            response.sendRedirect("LoginView.jsp");
        }
        
    } else if (btnRegister.equals("Registrieren")) {
        // ========== ZUR REGISTRIERUNG ==========
        myMessage.setRegistrationWelcome();
        response.sendRedirect("RegisterView.jsp");
        
    } else {
        // ========== ELSE-ZWEIG ==========
        // Standard-Willkommensnachricht
        myMessage.setLoginWelcome();
        response.sendRedirect("LoginView.jsp");
    }
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("LoginView.jsp");
}
%>
