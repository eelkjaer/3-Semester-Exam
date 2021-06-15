package entities;

import dtos.OwnerDTO;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
@Table(name = "owners")
public class Owner implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = true, length = 255)
  private String name;

  @Column(name = "address", nullable = true, length = 255, unique = false)
  private String address;

  @Column(name = "phone", nullable = true, length = 255, unique = false)
  private int phone;

  public Owner() {
  }

  public Owner(String name, String address, int phone) {
    this.id = -1;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public Owner(long id, String name, String address, int phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public Owner(String name, int phone) {
    this.id = -1;
    this.name = name;
    this.address = "";
    this.phone = phone;
  }

  public Owner(OwnerDTO ownerDTO) {
    this.id = ownerDTO.getId();
    this.name = ownerDTO.getName();
    this.address = ownerDTO.getAddress();
    this.phone = ownerDTO.getPhone();
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

  public int getPhone() {
    return phone;
  }

  public void setPhone(int phone) {
    this.phone = phone;
  }

  @Override
  public String toString() {
    return "Owner{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", phone=" + phone +
        '}';
  }
}
