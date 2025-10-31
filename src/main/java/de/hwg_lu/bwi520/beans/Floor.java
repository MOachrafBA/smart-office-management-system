package de.hwg_lu.bwi520.beans;
public class Floor {
  private int id, buildingId, indexNo;
  private String name;
  
  public Floor(int id,int buildingId,String name,int indexNo){
    this.id=id; 
    this.buildingId=buildingId;
    this.name=name;
    this.indexNo=indexNo;}
  
  public int getId(){
	  return id;} 
  
  public int getBuildingId(){
	  return buildingId;}
  
  public String getName(){
	  return name;} 
  
  public int getIndexNo(){
	  return indexNo;}
}
