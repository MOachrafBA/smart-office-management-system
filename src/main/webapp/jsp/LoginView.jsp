<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="de.hwg_lu.bwi520.beans.*" %>

<%
// ========== BEANS EINBINDEN ==========
%>
<jsp:useBean id="mySmartOffice" class="de.hwg_lu.bwi520.beans.SmartOfficeBean" scope="session" />
<jsp:useBean id="myMessage" class="de.hwg_lu.bwi520.beans.MessageBean" scope="session" />

<!DOCTYPE html>
<html>
<head>
    <title>Smart Office - Anmeldung</title>
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/app.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/common.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/css/messages.css">
</head>
<body>
    
    <!-- 1. Import von Beans (bereits oben) -->
    
    <!-- Banner -->
    <div class="banner">
        <h1>🏢 Smart Office Management System</h1>
        <p>Intelligente Geb&auml;ude&uuml;berwachung und -steuerung</p>
    </div>
    
    <!-- Hauptinhalt -->
    <div class="main-content">
        <h2>🔐 Anmeldung</h2>
        
        <!-- Nachricht anzeigen -->
        <jsp:getProperty name="myMessage" property="messageHtml" />
        
        <!-- Anmelde-Formular -->
        <form method="post" action="LoginAppl.jsp" class="login-form">
            <div class="form-group">
                <label for="username">Benutzername:</label>
                <input type="text" id="username" name="username" required placeholder="Ihr Benutzername">
            </div>
            
            <div class="form-group">
                <label for="password">Passwort:</label>
                <input type="password" id="password" name="password" required placeholder="Ihr Passwort">
            </div>
            
            <div class="form-actions">
                <button type="submit" name="btnLogin" value="Anmelden" class="btn-primary">
                    Anmelden
                </button>
            </div>
        </form>
        
        <div class="form-links">
            <p>Noch kein Konto? <a href="RegisterView.jsp">Hier registrieren</a></p>
        </div>
    </div>
    
    <!-- Untere Navigation -->
    <nav class="bottom-nav">
        <a href="LoginView.jsp">🏠 Startseite</a>
        <a href="RegisterView.jsp">📝 Registrieren</a>
        <span>| Smart Office Management System v1.0</span>
    </nav>

    <!-- Padding für fixed Navigation -->
    <div class="nav-padding"></div>
    
</body>
</html>
