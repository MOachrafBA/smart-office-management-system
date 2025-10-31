package de.hwg_lu.bwi520.beans;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * Bean für Control Request Daten
 */
public class ControlRequestRow implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private long id;
    private double requestedValue;
    private Timestamp createdAt;
    private String username;
    private int sensorId;
    private String sensorLabel;
    private String sensorTypeKey;
    
    // Konstruktor
    public ControlRequestRow() {}
    
    // Getter und Setter
    public long getId() { return id; }
    public void setId(long id) { this.id = id; }
    
    public double getRequestedValue() { return requestedValue; }
    public void setRequestedValue(double requestedValue) { this.requestedValue = requestedValue; }
    
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }
    
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    
    public int getSensorId() { return sensorId; }
    public void setSensorId(int sensorId) { this.sensorId = sensorId; }
    
    public String getSensorLabel() { return sensorLabel; }
    public void setSensorLabel(String sensorLabel) { this.sensorLabel = sensorLabel; }
    
    public String getSensorTypeKey() { return sensorTypeKey; }
    public void setSensorTypeKey(String sensorTypeKey) { this.sensorTypeKey = sensorTypeKey; }
}
