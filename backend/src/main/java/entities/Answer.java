package entities;

import dtos.AnswerDTO;
import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Answer.deleteAllRows", query = "DELETE from Answer")
@Table(name = "answers")
public class Answer implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @OneToOne(mappedBy = "answer", cascade = {
      CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Question question;

  @ManyToOne
  private Teacher teacher;

  @Column(name = "answer", nullable = true, length = 255)
  private String answer;

  @Column(name = "url", nullable = true, length = 255)
  private String url;

  public Answer() {
  }

  public Answer(long id, Question question, Teacher teacher, String answer, String url) {
    this.id = id;
    this.question = question;
    this.teacher = teacher;
    this.answer = answer;
    this.url = url;
  }

  public Answer(Question question, Teacher teacher, String answer, String url) {
    this.id = -1;
    this.question = question;
    this.teacher = teacher;
    this.answer = answer;
    this.url = url;
  }

  public Answer(AnswerDTO a) {
    this.id = a.getId();
    this.question = null;
    this.teacher = new Teacher(a.getTeacher());
    this.answer = a.getAnswer();
    this.url = a.getTeacherLink();
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public Question getQuestion() {
    return question;
  }

  public void setQuestion(Question question) {
    this.question = question;
  }

  public Teacher getTeacher() {
    return teacher;
  }

  public void setTeacher(Teacher teacher) {
    this.teacher = teacher;
  }

  public String getAnswer() {
    return answer;
  }

  public void setAnswer(String answer) {
    this.answer = answer;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

}
