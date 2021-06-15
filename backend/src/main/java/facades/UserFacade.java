package facades;

import entities.Owner;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.WebApplicationException;

public class UserFacade {
  private static UserFacade instance;
  private static EntityManagerFactory emf;
  private EntityManager getEntityManager() {
    return emf.createEntityManager();
  }
  private UserFacade() {}
  public static UserFacade getFacade(EntityManagerFactory _emf) {
    if (instance == null) {
      emf = _emf;
      instance = new UserFacade();
    }
    return instance;
  }


  public User getVerfiedUser(String username, String password) throws NotAuthorizedException {
    EntityManager em = emf.createEntityManager();
    User user;
    try {
      TypedQuery<User> query = em
          .createQuery("SELECT t FROM User t WHERE t.userName = :username", User.class);
      query.setParameter("username", username);
      user = query.getSingleResult();
      if (user == null || !user.verifyPassword(password)) {
        throw new NotAuthorizedException("Invalid username or password");
      }
    } catch (NoResultException e){
      throw new NotAuthorizedException("Invalid username or password");
    } finally {
      em.close();
    }
    return user;
  }

  /*public Owner registerStudent(String username, String password) throws WebApplicationException{
    EntityManager em = emf.createEntityManager();
    Owner user = new Owner(name, email);

    List<String> domains = getSchoolDomains();

    try {

      for(String s: domains){
        if (s.equals(user.getEmail().split("@")[1])) {
          em.getTransaction().begin();
          em.persist(user);
          em.getTransaction().commit();
          break;
        }
      }

      return user;

    } catch (Exception e){
      throw new WebApplicationException(409); //409 Conflict exception
    } finally {
      em.close();
    }
  }

  private List<String> getSchoolDomains(){
      EntityManager em = emf.createEntityManager();
      try{
        TypedQuery<String> query = em.createQuery("SELECT s.domain FROM School s", String.class);
        return query.getResultList();
      }catch (NoResultException ex) {
        return new ArrayList<>();
      }finally {
        em.close();
      }
  }*/

  public List<User> getAllUsers() {
    return new ArrayList<>();
  }
}