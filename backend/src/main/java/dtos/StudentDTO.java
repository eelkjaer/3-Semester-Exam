package dtos;

import entities.Student;

public class StudentDTO {

  private long id;
  private String name;
  private String email;

  public StudentDTO() {
  }

  public StudentDTO(long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
  }

  public StudentDTO(String name, String email) {
    this.id = -1;
    this.name = name;
    this.email = email;
  }

  public StudentDTO(Student student) {
    this.id = student.getId();
    this.name = student.getName();
    this.email = student.getEmail();
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

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("StudentDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
