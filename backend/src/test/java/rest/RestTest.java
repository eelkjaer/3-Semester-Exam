
package rest;

import entities.Boat;
import entities.Harbour;
import entities.Owner;
import org.junit.jupiter.api.DisplayName;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class RestTest {

  private static final int SERVER_PORT = 7777;
  private static final String SERVER_URL = "http://localhost/api";

  static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
  private static HttpServer httpServer;
  private static EntityManagerFactory emf;

  //Entities for the tests
  private static Boat boat1, boat2, boat3, boat4;
  private static Harbour harbour1, harbour2;
  private static Owner owner1, owner2, owner3, owner4, owner5, owner6, owner7;

  static HttpServer startServer() {
    ResourceConfig rc = ResourceConfig.forApplication(new ApiConfig());
    return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
  }

  @BeforeAll
  public static void setUpClass() {
    //This method must be called before you request the EntityManagerFactory
    EMF_Creator.startREST_TestWithDB();
    emf = EMF_Creator.createEntityManagerFactoryForTest();

    httpServer = startServer();
    //Setup RestAssured
    RestAssured.baseURI = SERVER_URL;
    RestAssured.port = SERVER_PORT;
    RestAssured.defaultParser = Parser.JSON;
  }

  @AfterAll
  public static void closeTestServer() {
    //System.in.read();

    //Don't forget this, if you called its counterpart in @BeforeAll
    EMF_Creator.endREST_TestWithDB();
    httpServer.shutdownNow();
  }

  @BeforeEach
  public void setUp() {
    EntityManager em = emf.createEntityManager();

    harbour1 = new Harbour("Køge Havn", "Havnevej 1, 4600 Køge", 250);
    harbour2 = new Harbour("Vallensbæk Havn", "Søvej 4, 2665 Vallensbæk Strand", 150);

    boat1 = new Boat("Some brand", "Some make", "HMS 1", "noimg.png");
    boat2 = new Boat("Some brand2", "Some make2", "HMS 2", "noimg.png");
    boat3 = new Boat("Some brand3", "Some make3", "HMS 3", "noimg.png");
    boat4 = new Boat("Some brand4", "Some make4", "HMS 4", "noimg.png");

    owner1 = new Owner("Tester 1", "Vejnavn 1", 12345678);
    owner2 = new Owner("Tester 2", "Vejnavn 2", 12345678);
    owner3 = new Owner("Tester 3", "Vejnavn 3", 12345678);
    owner4 = new Owner("Tester 4", "Vejnavn 4", 12345678);
    owner5 = new Owner("Tester 5", "Vejnavn 5", 12345678);
    owner6 = new Owner("Tester 6", "Vejnavn 6", 12345678);
    owner7 = new Owner("Tester 7", "Vejnavn 7", 12345678);


    try {
      em.getTransaction().begin();
      //Ensure all rows are empty
      em.createNamedQuery("Harbour.deleteAllRows").executeUpdate();
      em.createNamedQuery("Boat.deleteAllRows").executeUpdate();
      em.createNamedQuery("Owner.deleteAllRows").executeUpdate();

      //Create schools etc.
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

    } finally {
      em.close();
    }
  }

  @Test
  @DisplayName("As a user I would like to see all owners")
  //Checks that owners are present and the number of owners are correct in array.
  void US1() throws Exception {
    given()
        .contentType("application/json")
        .get("/owners").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("name", hasItems("Tester 1", "Tester 2", "Tester 3", "Tester 4", "Tester 5", "Tester 6", "Tester 7"))
        .body("size()", equalTo(7));
  }

  @Test
  @DisplayName("As a user I would like to see all boats belonging in a specific harbour")
  //Checks that 3 boats in harbour 1
  void US2() throws Exception {
    given()
        .pathParam("id", 1)
        .contentType("application/json")
        .get("/boats/harbour/{id}")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("size()", equalTo(3));
  }

  @Test
  @Disabled
  void testGetByName() throws Exception {
    given()
        .pathParam("id", "1")
        .contentType("application/json")
        .get("questions/semester/{id}")
        .then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("size()", equalTo(2));
  }
}

