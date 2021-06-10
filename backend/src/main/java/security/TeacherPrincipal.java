package security;

import entities.Teacher;
import java.security.Principal;

public class TeacherPrincipal implements Principal {

  private String email;

  /* Create a UserPrincipal, given the Entity class User*/
  public TeacherPrincipal(Teacher teacher) {
    this.email = teacher.getEmail();
  }

  public TeacherPrincipal(String email) {
    super();
    this.email = email;
  }

  @Override
  public String getName() {
    return this.email;
  }

  public boolean isUserInRole(String role) {
    return true;
  }
}