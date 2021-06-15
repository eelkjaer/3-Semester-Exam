package facades;

import dtos.BoatDTO;
import dtos.OwnerDTO;
import entities.Harbour;
import entities.Boat;
import entities.Owner;
import entities.User;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
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
  public Boat getBoatsByHarbour(long id){
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.id = :id", Boat.class);
      query.setParameter("id", id);
      return query.getSingleResult();
    }catch (NoResultException ex) {
      return null;
    } finally {
      em.close();
    }
  }
  //By name
  public Boat getBoatsByHarbour(String name){
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT b FROM Boat b WHERE b.harbour.name = :name", Boat.class);
      query.setParameter("name", name);
      return query.getSingleResult();
    }catch (NoResultException ex) {
      return null;
    } finally {
      em.close();
    }
  }


  /*public long getNumberOfCourses(){
    EntityManager em = emf.createEntityManager();
    try{
      return (long)em.createQuery("SELECT COUNT(c) FROM Course c").getSingleResult();
    }finally{
      em.close();
    }
  }*/

  /*public OwnerDTO findStudent(OwnerDTO dto) throws NotAuthorizedException {
    EntityManager em = emf.createEntityManager();
    Owner owner = new Owner(dto);
    try {
      Query query = em.createQuery("SELECT s FROM Owner s WHERE s.email = :email", Owner.class);
      query.setParameter("email", owner.getEmail());
      owner = (Owner) query.getSingleResult();

    } catch (RuntimeException ex) {
      throw new NotAuthorizedException("Student: " + dto.getEmail() + " is not authorized!");
    } finally {
      em.close();
    }
    return new OwnerDTO(owner);
  }


  public BoatDTO createQuestion(BoatDTO q){
    Boat boat = new Boat(q);
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();

      Owner owner = new Owner(findStudent(q.getStudent()));
      Harbour harbour = em.find(Harbour.class, q.getSemesterId());

      //Verify that student belongs to given school.
      if(!harbour.getCourse().getSchool().getDomain().equals(owner.getEmail().split("@")[1])){
        throw new NotAuthorizedException("Wrong school domain");
      }

      boat.setSemester(harbour);
      boat.setStudent(owner);
      boat.setTimestamp(Timestamp.from(Instant.now()));
      em.persist(boat);
      em.getTransaction().commit();

      q = new BoatDTO(boat);

      System.out.println("Question created with ID \"" + q.getId() + "\" by student with id \""+q.getStudent().getId()+"\"");
      } catch (NotAuthorizedException ex) {
      System.out.println("\"" + q.getStudent().getEmail() + " \" is not allowed here.");
      throw new WebApplicationException(ex.getMessage(), 401);
    } finally {
      em.close();
    }
    return q;
  }

  public Harbour getSemesterById(long id){
    Harbour harbour;
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      harbour = em.find(Harbour.class, id);
    } catch (Exception e){
      throw new NotFoundException();
    } finally {
      em.close();
    }
    return harbour;
  }


  public List<BoatDTO> getAllQuestions(){
    List<BoatDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT q FROM Boat q", Boat.class);
      List<Boat> res = query.getResultList();

      for(Boat q: res){
        dtos.add(new BoatDTO(q));
      }

    }catch (NoResultException ex) {
      return new ArrayList<>();
    } finally {
      em.close();
    }
    return dtos;
  }

  public List<BoatDTO> getAllQuestions(int semesterId){
    List<BoatDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Boat> query = em.createQuery("SELECT q FROM Boat q WHERE q.semester.id = :id", Boat.class);
      query.setParameter("id", semesterId);
      List<Boat> res = query.getResultList();

      for(Boat q: res){
        if(q.getAnswer() != null){
          System.out.println("Got answer: " + q.getAnswer().getId());
        }
        dtos.add(new BoatDTO(q));
      }

      for(BoatDTO qd: dtos){
        if(qd.getAnswer() == null){
          qd.setAnswer(new AnswerDTO(new TeacherDTO()));
        }
      }

    }catch (NoResultException ex) {
      return new ArrayList<>();
    }finally {
      em.close();
    }
    return dtos;
  }



  public AnswerDTO createAnswer(AnswerDTO a, long questionId){
    Answer answer = new Answer(a);
    Boat q;

    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();

      User user = new User(findTeacher(a.getTeacher()));

      q = em.find(Boat.class, questionId);

      OwnerDTO ownerDTO = new OwnerDTO(q.getStudent());
      Owner owner = new Owner(findStudent(ownerDTO));
      q.setStudent(owner);

      Harbour harbour = em.find(Harbour.class, q.getSemester().getId());


      //Verify that student belongs to given school.
      if(!harbour.getCourse().getSchool().getDomain().equals(user.getEmail().split("@")[1])){
        throw new NotAuthorizedException("Wrong school domain");
      }

      answer.setQuestion(q);
      answer.setTeacher(user);
      em.persist(answer);
      q.setAnswer(answer);
      em.merge(q);
      em.getTransaction().commit();

      a = new AnswerDTO(answer);

    } catch (NotAuthorizedException ex) {
      throw new WebApplicationException(ex.getMessage(), 401);
    } finally {
      em.close();
    }
    return a;
  }

  public BoatDTO addAnswer(BoatDTO dto) {
    AnswerDTO answerDTO = createAnswer(dto.getAnswer(), dto.getId());
    dto = new BoatDTO(getQuestionById(dto.getId()));
    dto.setAnswer(answerDTO);

    Harbour sem = getSemesterById(dto.getSemesterId());
    String schoolName = sem.getCourse().getSchool().getName();
    String courseName = sem.getCourse().getName();
    String semName = sem.getName();

    String linkName = String.format("Gå til %s, %s, %s", semName, courseName, schoolName);
    String link = String.format("https://theq.dk/%s/%s/%s", schoolName, courseName, semName).replaceAll(" ", "%20");

    String message = mailer.createMessage(
        String.format("Hej %s!</br>"
            + "Du har nu modtaget svar på dit spørgsmål <i>\"%s\"</i>.</br></br>"
            + "<a href=\"%s\" target=\"_blank\">%s</a>",
            dto.getStudent().getName(), dto.getTopic(), link, linkName)
    );

    try {
      mailer.sendEmail(dto.getStudent().getEmail(), message);
    } catch (Exception e){
      System.out.println("Error sending mail: " + e.getMessage());
    }
    return dto;
  }*/
}