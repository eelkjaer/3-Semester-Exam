package entities;

import dtos.StudentDTO;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Student.deleteAllRows", query = "DELETE from Student")
@Table(name = "students")
public class Student implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = true, length = 255)
  private String name;

  @Column(name = "email", nullable = true, length = 255, unique = true)
  private String email;

  @OneToMany(mappedBy = "student", cascade = {
      CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Question> questions;


  public Student() {
  }

  public Student(String name, String email) {
    this.id = -1;
    this.name = name;
    this.email = email;
    this.questions = null;
  }

  public Student(long id, String name, String email) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.questions = null;
  }

  public Student(String name, String email, Collection<Question> questions) {
    this.id = -1;
    this.name = name;
    this.email = email;
    this.questions = questions;
  }

  public Student(long id, String name, String email,
      Collection<Question> questions) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.questions = questions;
  }

  public Student(StudentDTO student) {
    this.id = student.getId();
    this.name = student.getName();
    this.email = student.getEmail();
    this.questions = null;
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

  public Collection<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Collection<Question> questions) {
    this.questions = questions;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Student{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", questions=").append(questions);
    sb.append('}');
    return sb.toString();
  }
}
