package entities;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
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
@NamedQuery(name = "Course.deleteAllRows", query = "DELETE from Course")
@Table(name = "courses")
public class Course implements Serializable{

  //Variables

  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = false, length = 45)
  private String name;
  @ManyToOne
  private School school;

  @OneToMany(mappedBy = "course", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Semester> semesters;

  //Constructors

  public Course() {
  }

  public Course(String name, School school) {
    this.id = -1;
    this.name = name;
    this.school = school;
    this.semesters = null;
  }

  public Course(long id, String name, School school) {
    this.id = id;
    this.name = name;
    this.school = school;
    this.semesters = null;
  }

  public Course(long id, String name, School school,
      Collection<Semester> semesters) {
    this.id = id;
    this.name = name;
    this.school = school;
    this.semesters = semesters;
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

  public School getSchool() {
    return school;
  }

  public void setSchool(School school) {
    this.school = school;
  }

  public Collection<Semester> getSemesters() {
    return semesters;
  }

  public void setSemesters(Collection<Semester> semesters) {
    this.semesters = semesters;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Course{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", school=").append(school);
    sb.append(", semesters=").append(semesters);
    sb.append('}');
    return sb.toString();
  }
}
