package dtos;

import entities.Answer;

public class AnswerDTO {

  private long id;
  private TeacherDTO teacher;
  private String answer;
  private String teacherLink;

  public AnswerDTO() {
  }

  public AnswerDTO(TeacherDTO teacher){
    this.id = -1;
    this.teacher = teacher;
    this.answer = "";
    this.teacherLink = "";
  }

  public AnswerDTO(Answer answer){
    this.id = answer.getId();
    this.teacher = new TeacherDTO(answer.getTeacher());
    this.answer = answer.getAnswer();
    this.teacherLink = answer.getUrl();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public TeacherDTO getTeacher() {
    return teacher;
  }

  public void setTeacher(TeacherDTO teacher) {
    this.teacher = teacher;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getTeacherLink() {
    return teacherLink;
  }

  public void setTeacherLink(String teacherLink) {
    this.teacherLink = teacherLink;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("AnswerDTO{");
    sb.append("id=").append(id);
    sb.append(", teacher=").append(teacher);
    sb.append(", answer='").append(answer).append('\'');
    sb.append(", url='").append(teacherLink).append('\'');
    sb.append('}');
    return sb.toString();
  }
}
