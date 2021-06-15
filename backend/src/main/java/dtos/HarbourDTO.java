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

  public HarbourDTO() {
  }

  public HarbourDTO(long id) {
    this.id = id;
  }

  public HarbourDTO(Harbour entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.address = entity.getAddress();
    this.capacity = entity.getCapacity();
  }

  public HarbourDTO(long id, String name, String address, int capacity) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
  }

  public HarbourDTO(String name, String address, int capacity) {
    this.id = -1;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
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

  @Override
  public String toString() {
    return "HarbourDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", capacity=" + capacity +
        '}';
  }
}
