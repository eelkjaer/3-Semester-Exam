package dtos;

import entities.Course;
import entities.Semester;
import java.util.ArrayList;
import java.util.List;

public class CourseDTO {

  private long id;
  private String name;
  private List<SemesterDTO> semesters;

  public CourseDTO() {
  }

  public CourseDTO(Course entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.semesters = new ArrayList<>();
    for(Semester s: entity.getSemesters()){
      this.semesters.add(new SemesterDTO(s));
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

  public List<SemesterDTO> getSemesters() {
    return semesters;
  }

  public void setSemesters(List<SemesterDTO> semesters) {
    this.semesters = semesters;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("CourseDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", semesters=").append(semesters);
    sb.append('}');
    return sb.toString();
  }
}
