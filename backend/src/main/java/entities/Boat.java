package entities;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
@Table(name = "boats")
public class Boat implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "harbour_id", referencedColumnName = "id", nullable = false)
  private Harbour harbour;


  @ManyToMany(cascade = {CascadeType.ALL})
  @JoinTable(name="boat_owners",
      joinColumns={@JoinColumn(name="boat_id")},
      inverseJoinColumns={@JoinColumn(name="owner_id")})
  private Collection<Owner> owners;


  @Column(name = "brand", nullable = true, length = 255)
  private String brand;

  @Column(name = "make", nullable = true, length = 255)
  private String make;

  @Column(name = "name", nullable = true, length = 255)
  private String name;

  @Column(name = "image")
  private String image;

  public Boat() {
  }

  public Boat(BoatDTO dto){
    this.id = dto.getId();
    this.owners = new ArrayList<>();
    for(OwnerDTO o: dto.getOwners()){
      this.owners.add(new Owner(o));
    }
    this.harbour = new Harbour(dto.getHarbour());
    this.brand = dto.getBrand();
    this.make = dto.getMake();
    this.name = dto.getName();
    this.image = dto.getImage();
  }

  public Boat(Harbour harbour, String brand, String make,
      String name, String image) {
    this.id = -1;
    this.harbour = harbour;
    this.owners = new ArrayList<>();
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
  }

  public Boat(long id, Harbour harbour, Collection<Owner> owners, String brand, String make,
      String name, String image) {
    this.id = id;
    this.harbour = harbour;
    this.owners = owners;
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
  }

  public Boat(Harbour harbour, String brand, String make, String name) {
    this.id = -1;
    this.harbour = harbour;
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.owners = new ArrayList<>();
  }

  public Boat(String brand, String make, String name, String image) {
    this.brand = brand;
    this.make = make;
    this.name = name;
    this.image = image;
    this.owners = new ArrayList<>();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Harbour getHarbour() {
    return harbour;
  }

  public void setHarbour(Harbour harbour) {
    this.harbour = harbour;
  }

  public Collection<Owner> getOwners() {
    return owners;
  }

  public void setOwners(Collection<Owner> owners) {
    this.owners = owners;
  }

  public void addOwner(Owner owner){
    this.owners.add(owner);
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
    return "Boat{" +
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
