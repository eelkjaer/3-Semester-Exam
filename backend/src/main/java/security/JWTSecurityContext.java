package security;

import java.security.Principal;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.SecurityContext;

public class JWTSecurityContext implements SecurityContext {
  TeacherPrincipal teacher;
  ContainerRequestContext request;

  public JWTSecurityContext(TeacherPrincipal teacher,ContainerRequestContext request) {
    this.teacher = teacher;
    this.request = request;
  }
  @Override
  public boolean isUserInRole(String role) {
    return true;
  }
  @Override
  public boolean isSecure() {
    return request.getUriInfo().getBaseUri().getScheme().equals("https");
  }
  @Override
  public Principal getUserPrincipal() {
    return teacher;
  }
  @Override
  public String getAuthenticationScheme() {
    return "JWT"; //Only for INFO
  }
}