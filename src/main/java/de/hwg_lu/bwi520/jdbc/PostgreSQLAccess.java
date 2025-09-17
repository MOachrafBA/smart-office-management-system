package de.hwg_lu.bwi520.jdbc;

import java.io.InputStream;
import java.util.Properties;

public class PostgreSQLAccess extends JDBCAccess {

  public PostgreSQLAccess() throws NoConnectionException {
    super();
  }

  @Override
  public void setDBParms() throws NoConnectionException {
    Properties p = new Properties();

    // db.properties aus dem Classpath laden (WEB-INF/classes)
    try (InputStream in = Thread.currentThread()
                                .getContextClassLoader()
                                .getResourceAsStream("db.properties")) {
      if (in == null) {
        throw new NoConnectionException("db.properties nicht im Classpath gefunden (WEB-INF/classes).");
      }
      p.load(in);
    } catch (Exception e) {
      throw new NoConnectionException("db.properties konnte nicht geladen werden: " + e.getMessage());
    }

    // Optional: erlaubtes Override per Property; sonst Defaults
    String driver = p.getProperty("db.driver", "org.postgresql.Driver");

    this.dbDrivername = driver;
    this.dbURL        = p.getProperty("db.url",      "jdbc:postgresql://localhost:5432/postgres");
    this.dbUser       = p.getProperty("db.user",     "bwi520");
    this.dbPassword   = p.getProperty("db.password", "");
    this.dbSchema     = p.getProperty("db.schema",   "public");
  }
}
