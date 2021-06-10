package dtos;

import entities.Course;
import entities.School;
import java.util.ArrayList;
import java.util.List;

public class SchoolDTO {

  private long id;
  private String name;
  private String img;
  private List<CourseDTO> educations;

  public SchoolDTO() {
  }

  public SchoolDTO(School entity){
    this.id = entity.getId();
    this.name = entity.getName();
    this.img = entity.getLogoUrl();
    this.educations = new ArrayList<>();
    for(Course c: entity.getCourses()){
      this.educations.add(new CourseDTO(c));
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

  public String getImg() {
    return img;
  }

  public void setImg(String img) {
    this.img = img;
  }

  public List<CourseDTO> getEducations() {
    return educations;
  }

  public void setEducations(List<CourseDTO> educations) {
    this.educations = educations;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("SchoolDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", img='").append(img).append('\'');
    sb.append(", educations=").append(educations);
    sb.append('}');
    return sb.toString();
  }
}
