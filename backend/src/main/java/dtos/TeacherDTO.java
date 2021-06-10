package dtos;

import entities.Teacher;
public class TeacherDTO {

  private long id;
  private String teacherName;
  private String email;
  private String zoom_url;

  public TeacherDTO() {
  }

  public TeacherDTO(Teacher teacher) {
    this.id = teacher.getId();
    this.teacherName = teacher.getName();
    this.email = teacher.getEmail();
    this.zoom_url = teacher.getZoom_url();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getTeacherName() {
    return teacherName;
  }

  public void setTeacherName(String teacherName) {
    this.teacherName = teacherName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getZoom_url() {
    return zoom_url;
  }

  public void setZoom_url(String zoom_url) {
    this.zoom_url = zoom_url;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("TeacherDTO{");
    sb.append("id=").append(id);
    sb.append(", name='").append(teacherName).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", zoom_url='").append(zoom_url).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
