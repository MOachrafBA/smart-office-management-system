package de.hwg_lu.bwi520.jdbc;

import java.io.InputStream;
import java.util.Properties;

/**
 * PostgreSQL-spezifische Datenbankverbindung
 * 
 * Diese Klasse erweitert JDBCAccess und konfiguriert die Verbindungsparameter
 * für eine PostgreSQL-Datenbank. Die Konfiguration wird aus der db.properties
 * Datei geladen, um Sicherheit und Flexibilität zu gewährleisten.
 * 
 * @author Smart Office Team
 * @version 1.0
 */
public class PostgreSQLAccess extends JDBCAccess {

  /**
   * Konstruktor für PostgreSQL-Verbindung
   * 
   * Ruft den Parent-Konstruktor auf, um die Basis-Verbindungsklasse zu initialisieren.
   * 
   * @throws NoConnectionException wenn die Verbindung nicht hergestellt werden kann
   */
  public PostgreSQLAccess() throws NoConnectionException {
    super();
  }

  /**
   * Konfiguriert die Datenbankverbindungsparameter
   * 
   * Lädt die Verbindungseinstellungen aus der db.properties Datei im Classpath.
   * Falls die Datei nicht gefunden wird oder ein Fehler auftritt, werden
   * Standardwerte verwendet.
   * 
   * Die Methode setzt folgende Parameter:
   * - dbDrivername: PostgreSQL JDBC Driver
   * - dbURL: Datenbank-URL (Host, Port, Datenbankname)
   * - dbUser: Benutzername für die Datenbank
   * - dbPassword: Passwort für die Datenbank
   * - dbSchema: Schema-Name in der Datenbank
   * 
   * @throws NoConnectionException wenn die Properties-Datei nicht geladen werden kann
   */
  @Override
  public void setDBParms() throws NoConnectionException {
    // Properties-Objekt für Konfigurationswerte
    Properties p = new Properties();

    // db.properties aus dem Classpath laden (build/classes)
    // Die Datei liegt in src/main/resources/ und wird beim Build nach build/classes kopiert
    try (InputStream in = Thread.currentThread()
                                .getContextClassLoader()
                                .getResourceAsStream("db.properties")) {
      
      // Prüfen ob die Properties-Datei gefunden wurde
      if (in == null) {
        throw new NoConnectionException("db.properties nicht im Classpath gefunden (build/classes).");
      }
      
      // Properties aus der Datei laden
      p.load(in);
      
    } catch (Exception e) {
      // Fehlerbehandlung: Detaillierte Fehlermeldung mit ursprünglicher Exception
      throw new NoConnectionException("db.properties konnte nicht geladen werden: " + e.getMessage());
    }

    // Datenbankverbindungsparameter aus Properties laden
    // Jeder Parameter hat einen Fallback-Wert (zweiter Parameter)
    
    // PostgreSQL JDBC Driver - Standardwert falls nicht in Properties definiert
    String driver = p.getProperty("db.driver", "org.postgresql.Driver");
    this.dbDrivername = driver;
    
    // Datenbank-URL: Host, Port und Datenbankname
    // Fallback: localhost:5432 mit Standard-PostgreSQL-Datenbank
    this.dbURL = p.getProperty("db.url", "jdbc:postgresql://localhost:5432/postgres");
    
    // Datenbank-Benutzername
    // Fallback: Standard-Benutzer "bwi520"
    this.dbUser = p.getProperty("db.user", "bwi520");
    
    // Datenbank-Passwort
    // Fallback: Leeres Passwort (für Entwicklung)
    this.dbPassword = p.getProperty("db.password", "");
    
    // Datenbank-Schema
    // Fallback: Standard-Schema "public"
    this.dbSchema = p.getProperty("db.schema", "public");
  }
}
