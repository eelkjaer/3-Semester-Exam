package dtos;

import entities.Boat;
import entities.Harbour;
import java.util.ArrayList;
import java.util.Collection;

public class HarbourDTO {

  private long id;
  private String name;
  private String address;
  private int capacity;
  private Collection<BoatDTO> boats;

  public HarbourDTO() {
  }

  public HarbourDTO(Harbour entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.capacity = entity.getCapacity();
    this.boats = new ArrayList<>();
    for(Boat o: entity.getBoats()){
      this.boats.add(new BoatDTO(o));
    }
  }

  public HarbourDTO(long id, String name, String address, int capacity) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.boats = new ArrayList<>();
  }

  public HarbourDTO(String name, String address, int capacity) {
    this.id = -1;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.boats = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public int getCapacity() {
    return capacity;
  }

  public void setCapacity(int capacity) {
    this.capacity = capacity;
  }

  public Collection<BoatDTO> getBoats() {
    return boats;
  }

  public void setBoats(Collection<BoatDTO> boats) {
    this.boats = boats;
  }

  @Override
  public String toString() {
    return "HarbourDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", capacity=" + capacity +
        ", boats=" + boats +
        '}';
  }
}
