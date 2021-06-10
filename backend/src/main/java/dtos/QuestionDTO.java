package dtos;

import entities.Question;
import java.sql.Timestamp;
import java.time.Instant;

public class QuestionDTO {

  private long id;
  private long semesterId;
  private StudentDTO student;
  private String topic;
  private String description;
  private String studentLink;
  private String password;
  private String timestamp;
  private AnswerDTO answer;


  public QuestionDTO() {
  }



  public QuestionDTO(Question question) {
    this.id = question.getId();
    this.semesterId = question.getSemester().getId();
    this.student = new StudentDTO(question.getStudent());
    this.topic = question.getTopic();
    this.description = question.getDescription();
    this.studentLink = question.getLink();
    this.password = question.getPassword();
    this.timestamp = question.getTimestamp().toString();
    this.answer = question.getAnswer() != null ? new AnswerDTO(question.getAnswer()) : null;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public long getSemesterId() {
    return semesterId;
  }

  public void setSemesterId(long semesterId) {
    this.semesterId = semesterId;
  }

  public StudentDTO getStudent() {
    return student;
  }

  public void setStudent(StudentDTO student) {
    this.student = student;
  }

  public String getTopic() {
    return topic;
  }

  public void setTopic(String topic) {
    this.topic = topic;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getStudentLink() {
    return studentLink;
  }

  public void setStudentLink(String studentLink) {
    this.studentLink = studentLink;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(String timestamp) {
    this.timestamp = timestamp;
  }

  public AnswerDTO getAnswer() {
    return answer;
  }

  public void setAnswer(AnswerDTO answer) {
    this.answer = answer;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("QuestionDTO{");
    sb.append("id=").append(id);
    sb.append(", semesterId=").append(semesterId);
    sb.append(", student=").append(student);
    sb.append(", topic='").append(topic).append('\'');
    sb.append(", description='").append(description).append('\'');
    sb.append(", link='").append(studentLink).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", timestamp='").append(timestamp).append('\'');
    sb.append(", answer='").append(answer);
    sb.append('}');
    return sb.toString();
  }
}
