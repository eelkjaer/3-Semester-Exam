package facades;

import dtos.AnswerDTO;
import dtos.QuestionDTO;
import dtos.SchoolDTO;
import dtos.SemesterDTO;
import dtos.StudentDTO;
import dtos.TeacherDTO;
import entities.Answer;
import entities.Question;
import entities.School;
import entities.Semester;
import entities.Student;
import entities.Teacher;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;
import utils.EMF_Creator;
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

  public List<SchoolDTO> getAllSchools() {
    List<SchoolDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<School> query = em.createQuery("SELECT s FROM School s", School.class);
      List<School> res = query.getResultList();

      for(School s: res){
        dtos.add(new SchoolDTO(s));
      }

    }catch (NoResultException ex) {
      return new ArrayList<>();
    }finally {
      em.close();
    }
    return dtos;
  }

  public long getNumberOfSchools(){
    EntityManager em = emf.createEntityManager();
    try{
      return (long)em.createQuery("SELECT COUNT(s) FROM School s").getSingleResult();
    }finally{
      em.close();
    }
  }

  public long getNumberOfCourses(){
    EntityManager em = emf.createEntityManager();
    try{
      return (long)em.createQuery("SELECT COUNT(c) FROM Course c").getSingleResult();
    }finally{
      em.close();
    }
  }

  public StudentDTO findStudent(StudentDTO dto) throws NotAuthorizedException {
    EntityManager em = emf.createEntityManager();
    Student student = new Student(dto);
    try {
      Query query = em.createQuery("SELECT s FROM Student s WHERE s.email = :email", Student.class);
      query.setParameter("email", student.getEmail());
      student = (Student) query.getSingleResult();

    } catch (RuntimeException ex) {
      throw new NotAuthorizedException("Student: " + dto.getEmail() + " is not authorized!");
    } finally {
      em.close();
    }
    return new StudentDTO(student);
  }

  public TeacherDTO findTeacher(TeacherDTO dto) throws NotAuthorizedException {
    EntityManager em = emf.createEntityManager();
    Teacher teacher = new Teacher(dto);
    try {
      Query query = em.createQuery("SELECT t FROM Teacher t WHERE t.email = :email", Teacher.class);
      query.setParameter("email", teacher.getEmail());
      teacher = (Teacher) query.getSingleResult();
    } catch (RuntimeException ex) {
      throw new NotAuthorizedException("Teacher: " + dto.getEmail() + " is not authorized!");
    } finally {
      em.close();
    }
    return new TeacherDTO(teacher);
  }

  public QuestionDTO createQuestion(QuestionDTO q){
    Question question = new Question(q);
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();

      Student student = new Student(findStudent(q.getStudent()));
      Semester semester = em.find(Semester.class, q.getSemesterId());

      //Verify that student belongs to given school.
      if(!semester.getCourse().getSchool().getDomain().equals(student.getEmail().split("@")[1])){
        throw new NotAuthorizedException("Wrong school domain");
      }

      question.setSemester(semester);
      question.setStudent(student);
      question.setTimestamp(Timestamp.from(Instant.now()));
      em.persist(question);
      em.getTransaction().commit();

      q = new QuestionDTO(question);

      System.out.println("Question created with ID \"" + q.getId() + "\" by student with id \""+q.getStudent().getId()+"\"");
      } catch (NotAuthorizedException ex) {
      System.out.println("\"" + q.getStudent().getEmail() + " \" is not allowed here.");
      throw new WebApplicationException(ex.getMessage(), 401);
    } finally {
      em.close();
    }
    return q;
  }

  public Semester getSemesterById(long id){
    Semester semester;
    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();
      semester = em.find(Semester.class, id);
    } catch (Exception e){
      throw new NotFoundException();
    } finally {
      em.close();
    }
    return semester;
  }


  public List<QuestionDTO> getAllQuestions(){
    List<QuestionDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q", Question.class);
      List<Question> res = query.getResultList();

      for(Question q: res){
        dtos.add(new QuestionDTO(q));
      }

    }catch (NoResultException ex) {
      return new ArrayList<>();
    } finally {
      em.close();
    }
    return dtos;
  }

  public List<QuestionDTO> getAllQuestions(int semesterId){
    List<QuestionDTO> dtos = new ArrayList<>();
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q WHERE q.semester.id = :id", Question.class);
      query.setParameter("id", semesterId);
      List<Question> res = query.getResultList();

      for(Question q: res){
        if(q.getAnswer() != null){
          System.out.println("Got answer: " + q.getAnswer().getId());
        }
        dtos.add(new QuestionDTO(q));
      }

      for(QuestionDTO qd: dtos){
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

  public Question getQuestionById(long id){
    EntityManager em = emf.createEntityManager();
    try{
      TypedQuery<Question> query = em.createQuery("SELECT q FROM Question q WHERE q.id = :id", Question.class);
      query.setParameter("id", id);
      return query.getSingleResult();
    }catch (NoResultException ex) {
      return null;
    } finally {
      em.close();
    }
  }

  public AnswerDTO createAnswer(AnswerDTO a, long questionId){
    Answer answer = new Answer(a);
    Question q;

    EntityManager em = emf.createEntityManager();
    try {
      em.getTransaction().begin();

      Teacher teacher = new Teacher(findTeacher(a.getTeacher()));

      q = em.find(Question.class, questionId);

      StudentDTO studentDTO = new StudentDTO(q.getStudent());
      Student student = new Student(findStudent(studentDTO));
      q.setStudent(student);

      Semester semester = em.find(Semester.class, q.getSemester().getId());


      //Verify that student belongs to given school.
      if(!semester.getCourse().getSchool().getDomain().equals(teacher.getEmail().split("@")[1])){
        throw new NotAuthorizedException("Wrong school domain");
      }

      answer.setQuestion(q);
      answer.setTeacher(teacher);
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

  public QuestionDTO addAnswer(QuestionDTO dto) {
    AnswerDTO answerDTO = createAnswer(dto.getAnswer(), dto.getId());
    dto = new QuestionDTO(getQuestionById(dto.getId()));
    dto.setAnswer(answerDTO);

    Semester sem = getSemesterById(dto.getSemesterId());
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
  }
}