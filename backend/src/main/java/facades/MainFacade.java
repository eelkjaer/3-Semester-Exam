package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Boat;
import entities.Harbour;
import entities.Owner;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import utils.JavaXEmailService;

public class MainFacade {
  private static MainFacade instance;
  private static EntityManagerFactory emf;
  private static JavaXEmailService mailer;

  private MainFacade() {}

  public static MainFacade getFacade(EntityManagerFactory _emf) {
    if (instance == null) {
      emf = _emf;
      instance = new MainFacade();
      mailer = new JavaXEmailService();
    }
    return instance;
  }

  //User story 1
  public List<OwnerDTO> getAllOwners() {
    List<OwnerDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Owner> query = em.createQuery("SELECT o FROM Owner o", Owner.class);
      List<Owner> res = query.getResultList();

      for(Owner o: res){
        dtos.add(new OwnerDTO(o));
      }

    }catch (NoResultException ex) {
      return new ArrayList<>();
    }finally {
      em.close();
    }
    return dtos;
  }

  //User story 2
  //By ID
  public List<BoatDTO> getBoatsByHarbour(long id){
    EntityManager em = emf.createEntityManager();
    List<BoatDTO> dtos = new ArrayList<>();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.id = :id", Boat.class);
      query.setParameter("id", id);

      List<Boat> boats = query.getResultList();

      if(boats.size() == 0){
        throw new NoResultException();
      }

      for(Boat b: boats){
        dtos.add(new BoatDTO(b));
      }
      return dtos;
    }catch (NoResultException ex) {
      throw new NotFoundException("No results for harbor with ID: " + id);
    } finally {
      em.close();
    }
  }

  //By name
  public List<BoatDTO> getBoatsByHarbour(String name){
    EntityManager em = emf.createEntityManager();
    List<BoatDTO> dtos = new ArrayList<>();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.name LIKE :name", Boat.class);
      name = "%" + name + "%"; //Adding wildcards
      query.setParameter("name", name);

      List<Boat> boats = query.getResultList();

      if(boats.size() == 0){
        throw new NoResultException();
      }

      for(Boat b: boats){
        dtos.add(new BoatDTO(b));
      }
      return dtos;
    }catch (NoResultException ex) {
      throw new NotFoundException("No results for harbor with name: " + name.replace("%", ""));
    } finally {
      em.close();
    }
  }

  public List<OwnerDTO> getOwnersOfBoat(int id) {
    EntityManager em = emf.createEntityManager();
    List<OwnerDTO> dtos = new ArrayList<>();
    try{
      TypedQuery<Owner> query = em.createQuery("SELECT b.owners FROM Boat b WHERE b.id = :id", Owner.class);
      query.setParameter("id", id);

      List<Owner> owners = query.getResultList();

      if(owners.size() == 0){
        throw new NoResultException();
      }

      for(Owner o: owners){
        dtos.add(new OwnerDTO(o));
      }
      return dtos;
    }catch (NoResultException ex) {
      throw new NotFoundException("No results for boat with ID: " + id);
    } finally {
      em.close();
    }
  }

  //explicit search.
  public List<OwnerDTO> getOwnersOfBoat(String name) {
    EntityManager em = emf.createEntityManager();
    List<OwnerDTO> dtos = new ArrayList<>();
    try{
      TypedQuery<Owner> query = em.createQuery("SELECT b.owners FROM Boat b WHERE b.name = :name", Owner.class);
      query.setParameter("name", name);

      List<Owner> owners = query.getResultList();

      if(owners.size() == 0){
        throw new NoResultException();
      }

      for(Owner o: owners){
        dtos.add(new OwnerDTO(o));
      }
      return dtos;
    }catch (NoResultException ex) {
      throw new NotFoundException("No results for boat with name: " + name);
    } finally {
      em.close();
    }
  }


  public BoatDTO createBoat(BoatDTO q){
    Boat boat = new Boat(q);
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();

      List<Owner> owners = focOwners(q.getOwners());

      Harbour harbour = q.getHarbour().getId() != 0 ? findHarbour(q.getHarbour().getId()) : findHarbour(q.getHarbour().getName());


      boat.setOwners(owners);
      boat.setHarbour(harbour);
      em.persist(boat);
      em.getTransaction().commit();

      q = new BoatDTO(boat);

      System.out.println("Boat created with ID \"" + q.getId() + "\"");
    } catch (NotAuthorizedException ex) {
      throw new WebApplicationException(ex.getMessage(), 401);
    } finally {
      em.close();
    }
    return q;
  }

  private Harbour findHarbour(long id){
    EntityManager em = emf.createEntityManager();
    Harbour harbour = null;

    try {
      Query query = em.createQuery("SELECT h FROM Harbour h WHERE h.id = :id", Harbour.class);
      query.setParameter("id", id);
      harbour = (Harbour) query.getSingleResult();


    } catch (RuntimeException ex) {
      throw new NotFoundException("Harbour with id " + id + " was not found!");
    } finally {
      em.close();
    }
    return harbour;
  }

  private Harbour findHarbour(String name){
    EntityManager em = emf.createEntityManager();
    Harbour harbour = null;

    try {
      Query query = em.createQuery("SELECT h FROM Harbour h WHERE h.name LIKE :name", Harbour.class);
      query.setParameter("name", "%" + name + "%");
      harbour = (Harbour) query.getSingleResult();


    } catch (RuntimeException ex) {
      throw new NotFoundException("Harbour with id " + name + " was not found!");
    } finally {
      em.close();
    }
    return harbour;
  }

  private Owner focOwner(OwnerDTO dto){
    EntityManager em = emf.createEntityManager();
    Owner owner = null;

    try {
      Query query;
      if(dto.getId() != 0){
        query = em.createQuery("SELECT o FROM Owner o WHERE o.id = :id", Owner.class);
        query.setParameter("id", dto.getId());
      } else {
        query = em.createQuery("SELECT o FROM Owner o WHERE o.name LIKE :name", Owner.class);
        query.setParameter("name", dto.getName());
      }


      owner = (Owner) query.getSingleResult();

      if(owner == null){
        owner = new Owner(dto);
        em.getTransaction().begin();
        em.persist(owner);
        em.getTransaction().commit();
      }

      return owner;

    } catch (RuntimeException ex) {
      throw new WebApplicationException(ex);
    } finally {
      em.close();
    }
  }

  private List<Owner> focOwners(Collection<OwnerDTO> dtos) {
    List<Owner> owners = new ArrayList<>();
    for(OwnerDTO o: dtos){
      owners.add(focOwner(o));
    }
    return owners;
  }

  public BoatDTO changeHarbour(BoatDTO dto) {
    EntityManager em = emf.createEntityManager();
    Boat boat;

    try {
      boat = em.find(Boat.class, dto.getId());
      boat.setHarbour(findHarbour(dto.getHarbour().getId()));
      em.getTransaction().begin();
      em.persist(boat);
      em.getTransaction().commit();
    } catch (RuntimeException ex) {
      throw new NotFoundException(ex);
    } finally {
      em.close();
    }
    return new BoatDTO(boat);
  }

  public BoatDTO changeBoat(BoatDTO dto) {
    EntityManager em = emf.createEntityManager();
    Boat boat;

    try {
      boat = em.find(Boat.class, dto.getId());
      boat.setHarbour(findHarbour(dto.getHarbour().getId()));
      boat.setOwners(focOwners(dto.getOwners()));
      boat.setBrand(dto.getBrand());
      boat.setMake(dto.getMake());
      boat.setImage(dto.getImage());
      boat.setName(dto.getName());
      em.getTransaction().begin();
      em.persist(boat);
      em.getTransaction().commit();
    } catch (RuntimeException ex) {
      throw new NotFoundException(ex);
    } finally {
      em.close();
    }
    return new BoatDTO(boat);
  }

  public boolean deleteBoat(long id) {
    EntityManager em = emf.createEntityManager();
    Boat boat;
    try {
      boat = em.find(Boat.class, id);
      em.getTransaction().begin();
      em.remove(boat);
      em.getTransaction().commit();
    } catch (RuntimeException ex) {
      throw new NotFoundException(ex);
    } finally {
      em.close();
    }
    return true;
  }

  public boolean deleteBoat(BoatDTO dto) {
    return deleteBoat(dto.getId());
  }
}