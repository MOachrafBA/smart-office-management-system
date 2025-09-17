package de.hwg_lu.bwi520.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class JDBCAccess {
  protected Connection dbConn;
  protected String dbDrivername, dbURL, dbUser, dbPassword, dbSchema;

  public JDBCAccess() throws NoConnectionException {
    setDBParms();       
    createConnection();
    setSchema();
  }

  // <<< hier: throws erlauben
  public abstract void setDBParms() throws NoConnectionException;

  public void createConnection() throws NoConnectionException {
    try {
      Class.forName(dbDrivername);
      dbConn = DriverManager.getConnection(dbURL, dbUser, dbPassword);
    } catch (Exception e) {
      throw new NoConnectionException(e.getMessage());
    }
  }

  public void setSchema() throws NoConnectionException {
    try {
      if (dbSchema != null && !dbSchema.isBlank()) {
        dbConn.createStatement().executeUpdate("SET search_path TO " + dbSchema + ", public");
      }
    } catch (SQLException e) {
      throw new NoConnectionException(e.getMessage());
    }
  }

  public Connection getConnection() throws NoConnectionException {
    setSchema(); // sicherstellen
    return dbConn;
  }
}
