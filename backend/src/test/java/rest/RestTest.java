package rest;

import entities.Answer;
import entities.Course;
import entities.Question;
import entities.School;
import entities.Semester;
import entities.Student;
import entities.Teacher;
import java.util.ArrayList;
import java.util.List;
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

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.equalToIgnoringCase;

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
  private static Answer answer1, answer2;
  private static Course course1, course2, course3;
  private static Question question1, question2, question3, question4, question5;
  private static School school1, school2;
  private static Semester semester1, semester2, semester3, semester4;
  private static Student student1, student2, student3, student4;
  private static Teacher teacher1, teacher2, teacher3;

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

    school1 = new School("Cphbusiness", "cphbusiness.dk", "cphbusiness.png");
    school2 = new School("CBS", "cbs.dk", "cbs.png");

    course1 = new Course("Datamatiker", null);
    course2 = new Course("Finansøkonom", null);
    course3 = new Course("HA-Dat", null);

    semester1 = new Semester("1. Semester");
    semester2 = new Semester("2. Semester");
    semester3 = new Semester("3. Semester");
    semester4 = new Semester("6. Semester");

    student1 = new Student("Emil","emil@cphbusiness.dk");
    student2 = new Student("Jacob", "jacob@cphbusiness.dk");
    student3 = new Student("Tobias", "tobias@cphbusiness.dk");
    student4 = new Student("Arik", "arik@cbs.dk");

    teacher1 = new Teacher("Lars", "lars@cphbusiness.dk", "kodeord123", "https://zoom.us/lars");
    teacher2 = new Teacher("Palle", "palle@cphbusiness.dk", "password456", "https://zoom.us/palle");
    teacher3 = new Teacher("Hans", "hans@cbs.dk", "1234", "https://zoom.us/hans");

    answer1 = new Answer(question2, teacher1, "Selvhjælp er den bedste hjælp", "https://google.dk/");


    question1 = new Question(semester1, null, student1, "Fejl i JPA", "Jeg får en fejl i en foreign key", "https://pastebin.com/asdf", "minkode123");
    question2 = new Question(semester1, null, student2, "Hjælp til proxy", "Jeg har brug for vejledning til at komme i gang med min proxy", null, "sikkerkode123");
    question3 = new Question(semester3, null, student3, "Rentes rente", "Jeg får en negativ værdi når jeg udregner rentes rente", null, "password123");
    question4 = new Question(semester2, null, student3, "IntelliJ crasher", "Overskriften siger alt", "https://zoom.us/", "password123");
    question5 = new Question(semester4, null, student4, "Validering af SWOT analyse", "Jeg har brug for at få godkendt min SWOT analyse", "https://zoom.us/", "password123");


    try {
      em.getTransaction().begin();
      //Ensure all rows are empty
      em.createNamedQuery("Answer.deleteAllRows").executeUpdate();
      em.createNamedQuery("Question.deleteAllRows").executeUpdate();
      em.createNamedQuery("Teacher.deleteAllRows").executeUpdate();
      em.createNamedQuery("Student.deleteAllRows").executeUpdate();
      em.createNamedQuery("Semester.deleteAllRows").executeUpdate();
      em.createNamedQuery("Course.deleteAllRows").executeUpdate();
      em.createNamedQuery("School.deleteAllRows").executeUpdate();

      //Create schools etc.
      em.persist(school1);
      em.persist(school2);

      em.getTransaction().commit();
      em.getTransaction().begin();

      course1.setSchool(school1);
      course2.setSchool(school1);
      course3.setSchool(school2);
      em.persist(course1);
      em.persist(course2);
      em.persist(course3);

      em.getTransaction().commit();
      em.getTransaction().begin();
      semester1.setCourse(course1);
      semester2.setCourse(course1);
      semester3.setCourse(course2);
      semester4.setCourse(course3);
      em.persist(semester1);
      em.persist(semester2);
      em.persist(semester3);
      em.persist(semester4);

      em.getTransaction().commit();
      em.getTransaction().begin();

      //Create students
      em.persist(student1);
      em.persist(student2);
      em.persist(student3);
      em.persist(student4);

      //Create teachers
      em.persist(teacher1);
      em.persist(teacher2);
      em.persist(teacher3);

      em.getTransaction().commit();
      em.getTransaction().begin();

      //Questions
      question1.setStudent(student1);
      question1.setSemester(semester1);
      question2.setStudent(student2);
      question2.setSemester(semester1);
      question3.setStudent(student3);
      question3.setSemester(semester3);
      question4.setStudent(student3);
      question4.setSemester(semester2);
      question5.setStudent(student4);
      question5.setSemester(semester4);
      em.persist(question1);
      em.persist(question2);
      em.persist(question3);
      em.persist(question4);
      em.persist(question5);
      em.getTransaction().commit();

      //Answers
      em.getTransaction().begin();
      answer1.setQuestion(question2);
      answer1.setTeacher(teacher1);
      em.persist(answer1);
      question1.setAnswer(answer1);
      em.merge(answer1);
      em.getTransaction().commit();

    } finally {
      em.close();
    }
  }

  @Test
  void testCount() throws Exception {
    given()
        .contentType("application/json")
        .get("/schools/count").then()
        .assertThat()
        .statusCode(HttpStatus.OK_200.getStatusCode())
        .body("count", equalTo(2));
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
