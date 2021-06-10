package dtos;

import entities.Semester;

public class SemesterDTO {

  private long id;
  private String name;

  public SemesterDTO() {
  }

  public SemesterDTO(Semester entity){
    this.id = entity.getId();
    this.name = entity.getName();
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

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SemesterDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
