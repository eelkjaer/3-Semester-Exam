package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.QuestionDTO;
import facades.MainFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.core.UriInfo;
import utils.EMF_Creator;

@Path("")
public class QueueResource {
  private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
  private static final MainFacade FACADE = MainFacade.getFacade(EMF);
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

  @Context
  private UriInfo context;

  @Context
  SecurityContext securityContext;

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String isAlive() {
    return "{\"msg\":\"I'm alive\"}";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("schools/count")
  public String countSchools(){
    System.out.println("GET REQUEST: countSchools");
    return "{\"count\":"+FACADE.getNumberOfSchools()+"}";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("courses/count")
  public String countCourses(){
    System.out.println("GET REQUEST: countCourses");
    return "{\"count\":"+FACADE.getNumberOfCourses()+"}";
  }


  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("schools/all")
  public String allSchools(){
    System.out.println("GET REQUEST: allSchools");
    return GSON.toJson(FACADE.getAllSchools());
  }



  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("questions/semester/{id}")
  public String allQuestionsBySemesterId(@PathParam("id") int semesterId){
    System.out.println("GET REQUEST: allQuestionsBySemester | Params(Semester ID: " + semesterId + ")");
    return GSON.toJson(FACADE.getAllQuestions(semesterId));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("questions")
  public String allQuestions(){
    System.out.println("GET REQUEST: allQuestions");
    return GSON.toJson(FACADE.getAllQuestions());
  }


  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("question")
  public String createQuestion(QuestionDTO dto){
    System.out.println("POST REQUEST: createQuestion");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.createQuestion(dto));
  }

  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("question")
  @RolesAllowed("admin")
  public String addAnswer(QuestionDTO dto){
    System.out.println("PUT REQUEST: addAnswer");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.addAnswer(dto));

  }
}
