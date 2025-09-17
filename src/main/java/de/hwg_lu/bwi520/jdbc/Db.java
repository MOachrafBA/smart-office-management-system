package de.hwg_lu.bwi520.jdbc;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public final class Db {
  private static Connection conn;

  // JDBC-Treiber einmalig beim Klassenladen registrieren
  static {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("PostgreSQL JDBC Driver fehlt im Classpath", e);
    }
  }

  private Db() { }

  private static Properties loadProps() {
    try (InputStream in = Db.class.getClassLoader().getResourceAsStream("db.properties")) {
      if (in == null) {
        throw new IllegalStateException("db.properties nicht gefunden (liegt sie unter src/main/resources?)");
      }
      Properties p = new Properties();
      p.load(in);
      return p;
    } catch (Exception e) {
      throw new RuntimeException("Konnte db.properties nicht laden", e);
    }
  }

  public static synchronized Connection get() throws SQLException {
    if (conn == null || conn.isClosed()) {
      Properties p = loadProps();
      conn = DriverManager.getConnection(
          p.getProperty("db.url"),
          p.getProperty("db.user"),
          p.getProperty("db.password")
      );

      String schema = p.getProperty("db.schema");
      if (schema != null && !schema.isBlank()) {
        try (Statement st = conn.createStatement()) {
          st.execute("SET search_path TO " + schema + ", public");
        }
      }
    }
    return conn;
  }

  public static synchronized void closeQuietly() {
    try {
      if (conn != null && !conn.isClosed()) conn.close();
    } catch (SQLException ignore) {}
    conn = null;
  }
}
