package entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.Table;

@Entity
@NamedQuery(name = "Semester.deleteAllRows", query = "DELETE from Semester")
@Table(name = "semesters")
public class Semester implements Serializable{

  //Variables
  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = true, length = 45)
  private String name;

  @OneToMany(mappedBy = "semester", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Question> questions;

  @ManyToOne
  private Course course;

  //Constructors

  public Semester() {
  }

  public Semester(String name, Course course) {
    this.id = -1;
    this.name = name;
    this.course = course;
  }

  public Semester(String name) {
    this.id = -1;
    this.name = name;
    this.course = null;
  }

  public Semester(long id, String name, Course course) {
    this.id = -1;
    this.name = name;
    this.course = course;
  }

  public Semester(long id, String name) {
    this.id = id;
    this.name = name;
    this.course = null;
  }

//Methods

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


  public Collection<Question> getQuestions() {
    return questions;
  }

  public void setQuestions(Collection<Question> questions) {
    this.questions = questions;
  }

  public Course getCourse() {
    return course;
  }

  public void setCourse(Course course) {
    this.course = course;
  }

}
