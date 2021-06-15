package facade;


import entities.Boat;
import entities.Owner;
import facades.MainFacade;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

class BoatTest {

  private static EntityManagerFactory emf;
  private static MainFacade facade;
  private static Boat boat1, boat2;

  public BoatTest() {
  }

  @BeforeAll
  static void setUpClass() {
    emf = EMF_Creator.createEntityManagerFactoryForTest();
    facade = MainFacade.getFacade(emf);

    boat1 = new Boat("Some brand", "Some make", "HMS 1", "noimg.png");
    boat2 = new Boat("Some brand2", "Some make2", "HMS 2", "noimg.png");
  }

  @BeforeEach
  void setUp() {
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
      em.persist(boat1);
      em.persist(boat2);

      em.getTransaction().commit();
    } finally {
      em.close();
    }
  }


  @Test
  void testNumbersOfBoats() {
    assertEquals(2, facade.getAllOwners().size(), "Expects two rows in the database");
  }

  @Test
  void testBoatName(){
    assertEquals("HMS 1", boat1.getName());
  }

}
