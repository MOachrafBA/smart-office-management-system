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
   * Einfache, direkte Konfiguration wie beim Dozenten
   */
  @Override
  public void setDBParms() {
    dbDrivername = "org.postgresql.Driver";
    dbURL        = "jdbc:postgresql://localhost:5432/postgres";
    dbUser       = "postgres";
    dbPassword   = "pgusers";
    dbSchema     = "bwi520";
  }
}
