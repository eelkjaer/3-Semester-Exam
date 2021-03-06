package facade;


import entities.Owner;
import facades.MainFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

@Disabled
class OwnerTest {

  private static EntityManagerFactory emf;
  private static MainFacade facade;
  private static Owner owner1, owner2;

  public OwnerTest() {
  }

  @BeforeAll
  static void setUpClass() {
    emf = EMF_Creator.createEntityManagerFactoryForTest();
    facade = MainFacade.getFacade(emf);

    owner1 = new Owner("Tester 1", "Vejnavn 1", 12345678);
    owner2 = new Owner("Tester 2", "Vejnavn 2", 12345678);
  }

  @BeforeEach
  void setUp() {
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      em.createNamedQuery("Owner.deleteAllRows").executeUpdate();
      em.persist(owner1);
      em.persist(owner2);

      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }


  @Test
  void testNumbersOfOwners() {
    assertEquals(2, facade.getAllOwners().size(), "Expects two rows in the database");
  }

}
