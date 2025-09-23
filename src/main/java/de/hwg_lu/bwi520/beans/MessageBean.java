package de.hwg_lu.bwi520.beans;

/**
 * MessageBean - Verwaltung von Systemnachrichten und Fehlermeldungen
 * 
 * Diese Bean verwaltet alle Nachrichten, die dem Benutzer angezeigt werden,
 * einschließlich Erfolgsmeldungen, Fehlermeldungen und Willkommensnachrichten.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class MessageBean {
    
    // ========== ATTRIBUTE ==========
    
    /**
     * Aktuelle Nachricht
     */
    private String message = "";
    
    /**
     * Typ der Nachricht (success, error, warning, info)
     */
    private String messageType = "info";
    
    /**
     * CSS-Klasse für die Nachricht
     */
    private String messageClass = "message-info";
    
    // ========== KONSTRUKTOR ==========
    
    /**
     * Standard-Konstruktor
     * Keine automatische Nachricht setzen
     */
    public MessageBean() {
        // Keine automatische Nachricht - wird bei Bedarf gesetzt
    }
    
    // ========== GETTER UND SETTER ==========
    
    /**
     * Gibt die aktuelle Nachricht zurück
     * @return Nachricht als String
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Setzt eine benutzerdefinierte Nachricht
     * @param message Nachricht
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Gibt den Nachrichtentyp zurück
     * @return Typ der Nachricht
     */
    public String getMessageType() {
        return messageType;
    }
    
    /**
     * Gibt die CSS-Klasse für die Nachricht zurück
     * @return CSS-Klasse
     */
    public String getMessageClass() {
        return messageClass;
    }
    
    // ========== WILLKOMMENSNACHRICHTEN ==========
    
    /**
     * Setzt Willkommensnachricht für Login
     */
    public void setLoginWelcome() {
        this.message = "Bitte melden Sie sich an, um auf das Smart Office System zuzugreifen.";
        this.messageType = "info";
        this.messageClass = "message-info";
    }
    
    
    /**
     * Setzt Willkommensnachricht für Registrierung
     */
    public void setRegistrationWelcome() {
        this.message = "Erstellen Sie ein neues Konto für das Smart Office System.";
        this.messageType = "info";
        this.messageClass = "message-info";
    }
    
    /**
     * Setzt Erfolgsnachricht für Login
     */
    public void setLoginSuccess(String username) {
        this.message = "Willkommen " + username + " im Smart Office Portal!";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    
    // ========== ERFOLGSNACHRICHTEN ==========
    
    /**
     * Setzt Erfolgsnachricht für Gebäude-Auswahl
     * @param buildingName Name des ausgewählten Gebäudes
     */
    public void setBuildingSelectedSuccess(String buildingName) {
        this.message = "Gebäude '" + buildingName + "' wurde erfolgreich ausgewählt.";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    /**
     * Setzt Erfolgsnachricht für Etagen-Auswahl
     * @param floorName Name der ausgewählten Etage
     */
    public void setFloorSelectedSuccess(String floorName) {
        this.message = "Etage '" + floorName + "' wurde erfolgreich ausgewählt.";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    /**
     * Setzt Erfolgsnachricht für Raum-Auswahl
     * @param roomName Name des ausgewählten Raumes
     */
    public void setRoomSelectedSuccess(String roomName) {
        this.message = "Raum '" + roomName + "' wurde erfolgreich ausgewählt.";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    /**
     * Setzt Erfolgsnachricht für Sensor-Steuerung
     * @param sensorName Name des Sensors
     * @param value Neuer Wert
     */
    public void setSensorControlSuccess(String sensorName, String value) {
        this.message = "Sensor '" + sensorName + "' wurde auf '" + value + "' gesetzt.";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    // ========== FEHLERNACHRICHTEN ==========
    
    /**
     * Setzt Fehlermeldung für Anmeldung
     */
    public void setLoginFailed() {
        this.message = "Anmeldung fehlgeschlagen. Bitte überprüfen Sie Ihre Eingaben.";
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    /**
     * Setzt Fehlermeldung für Gebäude-Auswahl
     */
    public void setBuildingSelectionFailed() {
        this.message = "Gebäude konnte nicht ausgewählt werden. Bitte versuchen Sie es erneut.";
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    /**
     * Setzt Fehlermeldung für Etagen-Auswahl
     */
    public void setFloorSelectionFailed() {
        this.message = "Etage konnte nicht ausgewählt werden. Bitte versuchen Sie es erneut.";
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    /**
     * Setzt Fehlermeldung für Raum-Auswahl
     */
    public void setRoomSelectionFailed() {
        this.message = "Raum konnte nicht ausgewählt werden. Bitte versuchen Sie es erneut.";
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    /**
     * Setzt Fehlermeldung für Sensor-Steuerung
     */
    public void setSensorControlFailed() {
        this.message = "Sensor konnte nicht gesteuert werden. Bitte versuchen Sie es erneut.";
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    /**
     * Setzt allgemeine Fehlermeldung
     * @param error Fehlerbeschreibung
     */
    public void setAnyError(String error) {
        this.message = "Unerwarteter Fehler: " + error;
        this.messageType = "error";
        this.messageClass = "message-error";
    }
    
    // ========== WARNUNGSNACHRICHTEN ==========
    
    /**
     * Setzt Warnung für unzureichende Berechtigung
     */
    public void setAccessDenied() {
        this.message = "Zugriff verweigert. Bitte melden Sie sich an.";
        this.messageType = "warning";
        this.messageClass = "message-warning";
    }
    
    /**
     * Setzt Warnung für Session-Timeout
     */
    public void setSessionTimeout() {
        this.message = "Ihre Session ist abgelaufen. Bitte melden Sie sich erneut an.";
        this.messageType = "warning";
        this.messageClass = "message-warning";
    }
    
    /**
     * Setzt Erfolgsnachricht für Registrierung
     */
    public void setRegistrationSuccess() {
        this.message = "Registrierung erfolgreich! Sie können sich jetzt anmelden.";
        this.messageType = "success";
        this.messageClass = "message-success";
    }
    
    // ========== HELPER METHODEN ==========
    
    /**
     * Prüft ob eine Nachricht gesetzt ist
     * @return true wenn Nachricht vorhanden, false sonst
     */
    public boolean hasMessage() {
        return message != null && !message.isEmpty();
    }
    
    /**
     * Löscht die aktuelle Nachricht
     */
    public void clearMessage() {
        this.message = "";
        this.messageType = "info";
        this.messageClass = "message-info";
    }
    
    /**
     * Gibt HTML für die Nachrichtendarstellung zurück
     * @return HTML-String für die Nachricht
     */
    public String getMessageHtml() {
        if (hasMessage()) {
            return "<div class=\"" + messageClass + "\">" + message + "</div>";
        }
        return "";
    }
}
