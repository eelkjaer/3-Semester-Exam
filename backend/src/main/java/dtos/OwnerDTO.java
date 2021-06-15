package dtos;

import entities.Owner;

public class OwnerDTO {

  private long id;
  private String name;
  private String address;
  private int phone;

  public OwnerDTO() {
  }

  public OwnerDTO(long id, String name, String address, int phone) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public OwnerDTO(String name, String address, int phone) {
    this.id = -1;
    this.name = name;
    this.address = address;
    this.phone = phone;
  }

  public OwnerDTO(Owner owner) {
    this.id = owner.getId();
    this.name = owner.getName();
    this.address = owner.getAddress();
    this.phone = owner.getPhone();
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
    return "OwnerDTO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", address='" + address + '\'' +
        ", phone=" + phone +
        '}';
  }
}
