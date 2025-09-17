package de.hwg_lu.bwi520.beans;
public class SensorRow {
  private int id; private String label,typeKey,unit,value,ts; private boolean writable;
  public SensorRow(int id,String label,String typeKey,String unit,boolean writable,String value,String ts){
    this.id=id; this.label=label; this.typeKey=typeKey; this.unit=unit; this.writable=writable; this.value=value; this.ts=ts;}
  public int getId(){return id;} public String getLabel(){return label;}
  public String getTypeKey(){return typeKey;} public String getUnit(){return unit;}
  public boolean isWritable(){return writable;} public String getValue(){return value;}
  public String getTs(){return ts;}
}
