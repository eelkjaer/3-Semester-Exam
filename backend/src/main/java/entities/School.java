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
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@NamedQuery(name = "School.deleteAllRows", query = "DELETE from School")
@Table(name = "schools")
public class School implements Serializable{

  //Variables
  private static final long serialVersionUID = 1L;

  @Id
  @Column(name = "id", nullable = false)
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(name = "name", nullable = true, length = 45)
  private String name;

  @Column(name = "domain", nullable = true, length = 45)
  private String domain;

  @Column(name = "logo_url", nullable = true, length = 45)
  private String logoUrl;

  @OneToMany(mappedBy = "school", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
  private Collection<Course> courses;


  //Constructors

  public School() {
  }

  public School(String name, String domain, String logoUrl){
    this.id = -1;
    this.name = name;
    this.domain = domain;
    this.logoUrl = logoUrl;
    this.courses = null;
  }

  public School(long id, String name, String domain, String logoUrl) {
    this.id = id;
    this.name = name;
    this.domain = domain;
    this.logoUrl = logoUrl;
  }

  public School(long id, String name, String domain, String logoUrl,
      Collection<Course> courses) {
    this.id = id;
    this.name = name;
    this.domain = domain;
    this.logoUrl = logoUrl;
    this.courses = courses;
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


  public String getDomain() {
    return domain;
  }

  public void setDomain(String domain) {
    this.domain = domain;
  }


  public String getLogoUrl() {
    return logoUrl;
  }

  public void setLogoUrl(String logoUrl) {
    this.logoUrl = logoUrl;
  }

  public Collection<Course> getCourses() {
    return courses;
  }

  public void setCourses(Collection<Course> courses) {
    this.courses = courses;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("School{");
    sb.append("id=").append(id);
    sb.append(", name='").append(name).append('\'');
    sb.append(", domain='").append(domain).append('\'');
    sb.append(", logoUrl='").append(logoUrl).append('\'');
    sb.append(", courses=").append(courses);
    sb.append('}');
    return sb.toString();
  }
}
