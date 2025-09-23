package de.hwg_lu.bwi520.beans;

import java.io.Serializable;

/**
 * Room-Klasse für Raum-Daten
 */
public class Room implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private int id;
    private int floorId;
    private String code;
    private String name;

    // ========== KONSTRUKTOREN ==========

    public Room() {
        // Standard-Konstruktor
    }

    public Room(int id, int floorId, String code, String name) {
        this.id = id;
        this.floorId = floorId;
        this.code = code;
        this.name = name;
    }

    // ========== GETTER UND SETTER ==========

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloorId() {
        return floorId;
    }

    public void setFloorId(int floorId) {
        this.floorId = floorId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // ========== HILFSMETHODEN ==========

    @Override
    public String toString() {
        return "Room{" +
               "id=" + id +
               ", floorId=" + floorId +
               ", code='" + code + '\'' +
               ", name='" + name + '\'' +
               '}';
    }
}
