package dtos;

import entities.Boat;
import entities.Owner;
import java.util.ArrayList;
import java.util.Collection;

public class BoatDTO {

  private long id;
  private HarbourDTO harbour;
  private Collection<OwnerDTO> owners;
  private String brand;
  private String make;
  private String name;
  private String image;


  public BoatDTO() {
  }

  public BoatDTO(long id, String brand, String make, String name, String image) {
    this.id = id;
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
  }

  public BoatDTO(long id, HarbourDTO harbour, String brand, String make, String name,
      String image) {
    this.id = id;
    this.harbour = harbour;
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
  }

  public BoatDTO(long id, HarbourDTO harbour, Collection<OwnerDTO> owners, String brand,
      String make, String name, String image) {
    this.id = id;
    this.harbour = harbour;
    this.owners = owners;
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
  }

  public BoatDTO(Boat boat) {
    this.id = boat.getId();
    this.harbour = new HarbourDTO(boat.getHarbour());
    this.owners = addOwners(boat.getOwners());
    this.brand = boat.getBrand();
    this.make = boat.getMake();
    this.name = boat.getName();
    this.image = boat.getImage();
  }

  private Collection<OwnerDTO> addOwners(Collection<Owner> owners) {
    ArrayList<OwnerDTO> dtos = new ArrayList<>();
    for(Owner o: owners){
      dtos.add(convertOwner(o));
    }
    return dtos;
  }

  private OwnerDTO convertOwner(Owner o) {
    if(o != null){
      return new OwnerDTO(o);
    } else {
      return null;
    }
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public HarbourDTO getHarbour() {
    return harbour;
  }

  public void setHarbour(HarbourDTO harbour) {
    this.harbour = harbour;
  }

  public Collection<OwnerDTO> getOwners() {
    return owners;
  }

  public void setOwners(Collection<OwnerDTO> owners) {
    this.owners = owners;
  }

  public String getBrand() {
    return brand;
  }

  public void setBrand(String brand) {
    this.brand = brand;
  }

  public String getMake() {
    return make;
  }

  public void setMake(String make) {
    this.make = make;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getImage() {
    return image;
  }

  public void setImage(String image) {
    this.image = image;
  }

  @Override
  public String toString() {
    return "BoatDTO{" +
        "id=" + id +
        ", harbour=" + harbour +
        ", owners=" + owners +
        ", brand='" + brand + '\'' +
        ", make='" + make + '\'' +
        ", name='" + name + '\'' +
        ", image='" + image + '\'' +
        '}';
  }
}
