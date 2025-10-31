package de.hwg_lu.bwi520.beans;
public class SensorRow {
  private int id;
  private String label,typeKey,unit,value,ts; 
  private boolean writable;
  
  // DatenContainer für Sensor Informationen
  public SensorRow(int id,String label,String typeKey,String unit,boolean writable,String value,String ts){
    this.id=id; 
    this.label=label;
    this.typeKey=typeKey;// Typ (z.B. "temperature", "humidity")
    this.unit=unit;// Einheit (z.B. "°C", "%")
    this.writable=writable; 
    this.value=value; // Aktueller Wert (z.B. "22.5")
    this.ts=ts; // Zeitstempel (z.B. "2025-01-01 12:00:00")}
  
  public int getId(){
	  return id;} 
  
  public String getLabel(){
	  return label;}
  
  public String getTypeKey(){
	  return typeKey;} 
  
  public String getUnit(){
	  return unit;}
  
  public boolean isWritable(){
	  return writable;} 
  
  public String getValue(){
	  return value;}
  
  public String getTs(){
	  return ts;}
}
