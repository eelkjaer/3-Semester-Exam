package facades;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import entities.Role;
import entities.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import utils.EMF_Creator;

public class Tester {

  public static void main(String[] args) {
    EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
    EntityManager em = emf.createEntityManager();

    em.getTransaction().begin();

    Harbour harbour1 = new Harbour("Køge Havn", "Havnevej 1, 4600 Køge", 250);
    Harbour harbour2 = new Harbour("Vallensbæk Havn", "Søvej 4, 2665 Vallensbæk Strand", 150);

    Boat boat1 = new Boat("Some brand", "Some make", "HMS 1", "noimg.png");
    Boat boat2 = new Boat("Some brand2", "Some make2", "HMS 2", "noimg.png");
    Boat boat3 = new Boat("Some brand3", "Some make3", "HMS 3", "noimg.png");
    Boat boat4 = new Boat("Some brand4", "Some make4", "HMS 4", "noimg.png");

    Owner owner1 = new Owner("Tester 1", "Vejnavn 1", 12345678);
    Owner owner2 = new Owner("Tester 2", "Vejnavn 2", 12345678);
    Owner owner3 = new Owner("Tester 3", "Vejnavn 3", 12345678);
    Owner owner4 = new Owner("Tester 4", "Vejnavn 4", 12345678);
    Owner owner5 = new Owner("Tester 5", "Vejnavn 5", 12345678);
    Owner owner6 = new Owner("Tester 6", "Vejnavn 6", 12345678);
    Owner owner7 = new Owner("Tester 7", "Vejnavn 7", 12345678);

    User user1 = new User("admin", "test");
    User user2 = new User("user", "test");
    Role role1 = new Role("admin");
    Role role2 = new Role("user");

    em.persist(role1);
    em.persist(role2);

    user1.addRole(role1);
    user2.addRole(role2);

    em.persist(user1);
    em.persist(user2);

    em.getTransaction().commit();
    em.getTransaction().begin();

    em.persist(harbour1);
    em.persist(harbour2);

    em.getTransaction().commit();
    em.getTransaction().begin();

    em.persist(owner1);
    em.persist(owner2);
    em.persist(owner3);
    em.persist(owner4);
    em.persist(owner5);
    em.persist(owner6);
    em.persist(owner7);

    em.getTransaction().commit();
    em.getTransaction().begin();

    boat1.setHarbour(harbour1);
    boat1.addOwner(owner1);
    em.persist(boat1);

    boat2.setHarbour(harbour1);
    boat2.addOwner(owner2);
    boat2.addOwner(owner3);
    em.persist(boat2);

    boat3.setHarbour(harbour2);
    boat3.addOwner(owner4);
    em.persist(boat3);

    boat4.setHarbour(harbour1);
    boat4.addOwner(owner5);
    boat4.addOwner(owner6);
    boat4.addOwner(owner7);
    em.persist(boat4);

    em.getTransaction().commit();
    em.getTransaction().begin();



    //Get Hobbie list
    TypedQuery<Owner> query1 = em.createQuery("SELECT o FROM Owner o", Owner.class);

    List<Owner> owners = query1.getResultList();
    System.out.println(owners);


    em.getTransaction().commit();
  }

}