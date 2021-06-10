package entities;

import dtos.QuestionDTO;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Question.deleteAllRows", query = "DELETE from Question")
@Table(name = "questions")
public class Question implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @ManyToOne
  @JoinColumn(name = "semester_id", referencedColumnName = "id", nullable = false)
  private Semester semester;

  @OneToOne
  @JoinColumn(name = "answer_id", referencedColumnName = "id", nullable = true)
  private Answer answer;

  @ManyToOne
  private Student student;

  @Column(name = "topic", nullable = true, length = 255)
  private String topic;

  @Column(name = "description", nullable = true, length = 255)
  private String description;

  @Column(name = "link", nullable = true, length = 255)
  private String link;

  @Column(name = "timestamp")
  private Timestamp timestamp;

  @Column(name = "password", nullable = true, length = 255)
  private String password;

  public Question() {
    this.timestamp = Timestamp.from(Instant.now());
  }

  public Question(Semester semester, Answer answer, Student student, String topic,
      String description, String link, String password) {
    this.semester = semester;
    this.answer = answer;
    this.student = student;
    this.topic = topic;
    this.description = description;
    this.link = link;
    this.timestamp = Timestamp.from(Instant.now());
    this.password = password;
  }

  public Question(long id, Semester semester, Answer answer, Student student, String topic,
      String description, String link, String password) {
    this.id = id;
    this.semester = semester;
    this.answer = answer;
    this.student = student;
    this.topic = topic;
    this.description = description;
    this.link = link;
    this.timestamp = Timestamp.from(Instant.now());
    this.password = password;
  }

  public Question(QuestionDTO q) {
    this.id = q.getId();
    this.semester = null;
    this.answer = q.getAnswer() != null ? new Answer(q.getAnswer()) : null;
    this.student = new Student(q.getStudent());
    this.topic = q.getTopic();
    this.description = q.getDescription();
    this.link = q.getStudentLink();
    this.timestamp = Timestamp.from(Instant.now());
    this.password = q.getPassword();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Semester getSemester() {
    return semester;
  }

  public void setSemester(Semester semester) {
    this.semester = semester;
  }

  public Answer getAnswer() {
    return answer;
  }

  public void setAnswer(Answer answer) {
    this.answer = answer;
  }

  public Student getStudent() {
    return student;
  }

  public void setStudent(Student student) {
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

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public Timestamp getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Timestamp timestamp) {
    this.timestamp = timestamp;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }
}
