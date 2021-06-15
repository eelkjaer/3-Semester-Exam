package entities;

import dtos.BoatDTO;
import dtos.HarbourDTO;
import dtos.OwnerDTO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Harbour.deleteAllRows", query = "DELETE from Harbour")
@Table(name = "harbours")
public class Harbour implements Serializable{

  //Variables
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false, length = 45, unique = true)
  private String name;

  @Column(name = "address", nullable = true, length = 45)
  private String address;

  @Column(name = "capacity", nullable = true, length = 45)
  private int capacity;

  @OneToMany(mappedBy = "harbour", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Boat> boats;

  //Constructors

  public Harbour() {
  }

  public Harbour(String name, String address, int capacity) {
    this.id = -1;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.boats = new ArrayList<>();
  }

  public Harbour(long id, String name, String address, int capacity) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.boats = new ArrayList<>();
  }

  public Harbour(long id, String name, String address, int capacity,
      Collection<Boat> boats) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.capacity = capacity;
    this.boats = boats;
  }

  public Harbour(HarbourDTO dto){
    this.id = dto.getId();
    this.name = dto.getName();
    this.address = dto.getAddress();
    this.capacity = dto.getCapacity();
    this.boats = new ArrayList<>();
    for(BoatDTO o: dto.getBoats()){
      this.boats.add(new Boat(o));
    }
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

  public Collection<Boat> getBoats() {
    return boats;
  }

  public void setBoats(Collection<Boat> boats) {
    this.boats = boats;
  }

  public void addBoat(Boat boat){
    this.boats.add(boat);
  }

  @Override
  public String toString() {
    return "Harbour{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", capacity=" + capacity +
        ", boats=" + boats +
        '}';
  }
}
