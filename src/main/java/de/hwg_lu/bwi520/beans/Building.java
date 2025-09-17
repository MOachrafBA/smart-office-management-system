package de.hwg_lu.bwi520.beans;
public class Building { 
  private int id; 
  private String name;
  
  public Building(int id,String name)
  {this.id=id;this.name=name;}
  
  public int getId()
  {return id;} 
  
  public String getName()
  {return name;}
}
