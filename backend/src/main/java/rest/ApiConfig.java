package rest;

import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.ExceptionMapper;

@ApplicationPath("api")
public class ApiConfig extends Application {
  @Override
  public Set<Class<?>> getClasses() {
    Set<Class<?>> resources = new java.util.HashSet<>();
    addRestResourceClasses(resources);

    return resources;
  }

  /**
   * Do not modify addRestResourceClasses() method.
   * It is automatically populated with
   * all resources defined in the project.
   * If required, comment out calling this method in getClasses().
   */
  private void addRestResourceClasses(Set<Class<?>> resources) {
    resources.add(cors.CorsFilter.class);
    resources.add(ExceptionMapper.class);
    resources.add(org.glassfish.jersey.server.wadl.internal.WadlResource.class);
    resources.add(QueueResource.class);
    resources.add(security.JWTAuthFilter.class);
    resources.add(AuthResource.class);
    resources.add(security.RolesAllowedFilter.class);
    resources.add(ExternalResource.class);
  }
}
