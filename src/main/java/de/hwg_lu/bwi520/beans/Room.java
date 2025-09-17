package de.hwg_lu.bwi520.beans;
public class Room {
  private int id, floorId; private String code, name;
  public Room(int id,int floorId,String code,String name){
    this.id=id; this.floorId=floorId; this.code=code; this.name=name;}
  public int getId(){return id;} public int getFloorId(){return floorId;}
  public String getCode(){return code;} public String getName(){return name;}
}
