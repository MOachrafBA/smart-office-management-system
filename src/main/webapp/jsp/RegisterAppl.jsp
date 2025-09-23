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
String btnRegister = request.getParameter("btnRegister");
String username = request.getParameter("username");
String email = request.getParameter("email");
String password = request.getParameter("password");
String confirmPassword = request.getParameter("confirmPassword");

// ========== NULL-CHECK ==========
if (btnRegister == null) btnRegister = "";
if (username == null) username = "";
if (email == null) email = "";
if (password == null) password = "";
if (confirmPassword == null) confirmPassword = "";

// ========== AKTIONSWEICHE ==========
try {
    if (btnRegister.equals("Registrieren")) {
        // ========== REGISTRIERUNG DURCHFÜHREN ==========
        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            myMessage.setAnyError("Bitte füllen Sie alle Felder aus.");
            response.sendRedirect("RegisterView.jsp");
            return;
        }
        
        if (!password.equals(confirmPassword)) {
            myMessage.setAnyError("Passwörter stimmen nicht überein.");
            response.sendRedirect("RegisterView.jsp");
            return;
        }
        
        try {
            // Datenbankregistrierung
            UserDao userDao = new UserDao();
            
            // Prüfen ob Benutzername bereits existiert
            if (userDao.usernameExists(username)) {
                myMessage.setAnyError("Benutzername bereits vergeben.");
                response.sendRedirect("RegisterView.jsp");
                return;
            }
            
            // Prüfen ob E-Mail bereits existiert
            if (userDao.emailExists(email)) {
                myMessage.setAnyError("E-Mail-Adresse bereits vergeben.");
                response.sendRedirect("RegisterView.jsp");
                return;
            }
            
            // Neuen Benutzer erstellen
            User newUser = new User(username, email);
            newUser.setPasswordHash(password); // In echter Anwendung: gehashtes Passwort
            newUser.setRole("user"); // Standard-Rolle
            newUser.setActive(true);
            
            // Benutzer in Datenbank speichern
            if (userDao.register(newUser)) {
                myMessage.setRegistrationSuccess();
                response.sendRedirect("LoginView.jsp");
            } else {
                myMessage.setAnyError("Registrierung fehlgeschlagen. Bitte versuchen Sie es erneut.");
                response.sendRedirect("RegisterView.jsp");
            }
        } catch (SQLException e) {
            // Datenbankfehler
            myMessage.setAnyError("Datenbankfehler bei der Registrierung: " + e.getMessage());
            response.sendRedirect("RegisterView.jsp");
        }
        
    } else {
        // ========== ELSE-ZWEIG ==========
        // Standard-Willkommensnachricht
        myMessage.setRegistrationWelcome();
        response.sendRedirect("RegisterView.jsp");
    }
    
} catch (Exception e) {
    // ========== ALLGEMEINER FEHLER ==========
    myMessage.setAnyError("Unerwarteter Fehler: " + e.getMessage());
    response.sendRedirect("RegisterView.jsp");
}
%>
