package entities;

import dtos.TeacherDTO;
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
@NamedQuery(name = "Teacher.deleteAllRows", query = "DELETE from Teacher")
@Table(name = "teachers")
public class Teacher implements Serializable {

  private static final long serialVersionUID = 1L;
  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = true, length = 45)
  private String name;

  @Column(name = "email", nullable = true, length = 45, unique = true)
  private String email;

  @Column(name = "password", nullable = true, length = 45)
  private String password;

  @Column(name = "zoom_url", nullable = true, length = 45)
  private String zoom_url;

  @OneToMany(mappedBy = "teacher", cascade = {
      CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Answer> answers;


  public Teacher() {
  }

  public Teacher(long id, String name, String email, String password, String zoom_url,
      Collection<Answer> answers) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.zoom_url = zoom_url;
    this.answers = answers;
  }

  public Teacher(String name, String email, String password, String zoom_url,
      Collection<Answer> answers) {
    this.id = -1;
    this.name = name;
    this.email = email;
    this.password = password;
    this.zoom_url = zoom_url;
    this.answers = answers;
  }

  public Teacher(String name, String email, String password, String zoom_url) {
    this.id = -1;
    this.name = name;
    this.email = email;
    this.password = password;
    this.zoom_url = zoom_url;
    this.answers = null;
  }

  public Teacher(long id, String name, String email, String password, String zoom_url) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.password = password;
    this.zoom_url = zoom_url;
    this.answers = null;
  }

  public Teacher(TeacherDTO teacher) {
    this.id = teacher.getId();
    this.name = teacher.getTeacherName();
    this.email = teacher.getEmail();
    this.password = "";
    this.zoom_url = teacher.getZoom_url();
    this.answers = null;
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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getZoom_url() {
    return zoom_url;
  }

  public void setZoom_url(String zoom_url) {
    this.zoom_url = zoom_url;
  }

  public Collection<Answer> getAnswers() {
    return answers;
  }

  public void setAnswers(Collection<Answer> answers) {
    this.answers = answers;
  }


  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("Teacher{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", email='").append(email).append('\'');
    sb.append(", password='").append(password).append('\'');
    sb.append(", zoom_url='").append(zoom_url).append('\'');
    sb.append(", answers=").append(answers);
    sb.append('}');
    return sb.toString();
  }

  public boolean verifyPassword(String password) {
    return this.password.equals(password);
  }
}
