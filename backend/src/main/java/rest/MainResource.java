package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dtos.BoatDTO;
import facades.MainFacade;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
public class MainResource {
  private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
  private static final MainFacade FACADE = MainFacade.getFacade(EMF);
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

  @Context
  private UriInfo context;

  @Context
  SecurityContext securityContext;

  //Storyless
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String isAlive() {
    return "{\"msg\":\"I'm alive\"}";
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("harbours")
  public String getAllHarbours(){
    System.out.println("GET REQUEST: all harbours");
    return GSON.toJson(FACADE.getAllHarbours());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("boats")
  public String getAllBoats(){
    System.out.println("GET REQUEST: all boats");
    return GSON.toJson(FACADE.getAllBoats());
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("boat/{id}")
  public String getBoatById(@PathParam("id") int boatId){
    System.out.println("GET REQUEST: getBoatById | Params(Boat ID: " + boatId + ")");
    return GSON.toJson(FACADE.getBoatById(boatId));
  }


  //User story 1
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("owners")
  public String allOwners(){
    System.out.println("GET REQUEST: all owners");
    return GSON.toJson(FACADE.getAllOwners());
  }


  //User story 2
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("boats/harbour/{id}")
  public String allBoatsInHarbour(@PathParam("id") int harbourId){
    System.out.println("GET REQUEST: allBoatsInHarbour | Params(Harbour ID: " + harbourId + ")");
    return GSON.toJson(FACADE.getBoatsByHarbour(harbourId));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("boats/harbour/name/{name}")
  public String allBoatsInHarbour(@PathParam("name") String harbourName){
    System.out.println("GET REQUEST: allBoatsInHarbour | Params(Harbour Name: " + harbourName + ")");
    return GSON.toJson(FACADE.getBoatsByHarbour(harbourName));
  }


  //User story 3
  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("owners/boat/{id}")
  public String allOwnersOfBoat(@PathParam("id") int boatId){
    System.out.println("GET REQUEST: allOwnersOfBoat | Params(Boat ID: " + boatId + ")");
    return GSON.toJson(FACADE.getOwnersOfBoat(boatId));
  }

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  @Path("owners/boat/name/{name}")
  public String allOwnersOfBoat(@PathParam("name") String boatName){
    System.out.println("GET REQUEST: allOwnersOfBoat | Params(Boat Name: " + boatName + ")");
    return GSON.toJson(FACADE.getOwnersOfBoat(boatName));
  }

  //User story 4
  @POST
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("boat")
  @RolesAllowed("admin")
  public String createBoat(BoatDTO dto){
    System.out.println("POST REQUEST: createBoat");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.createBoat(dto));
  }

  //User story 5
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("boat/changeharbour")
  @RolesAllowed("admin")
  public String changeHarbour(BoatDTO dto){
    System.out.println("PUT REQUEST: changeHarbour");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.changeHarbour(dto));

  }

  //User story 6
  @PUT
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("boat")
  @RolesAllowed("admin")
  public String changeBoat(BoatDTO dto){
    System.out.println("PUT REQUEST: changeBoat");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.changeBoat(dto));
  }

  //User story 7
  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("boat")
  @RolesAllowed("admin")
  public String deleteBoat(BoatDTO dto){
    System.out.println("DELETE REQUEST: deleteBoat");
    System.out.println("JSON recieved: " + dto);
    return GSON.toJson(FACADE.deleteBoat(dto));
  }

  @DELETE
  @Produces(MediaType.APPLICATION_JSON)
  @Consumes(MediaType.APPLICATION_JSON)
  @Path("boat/{id}")
  @RolesAllowed("admin")
  public String deleteBoat(@PathParam("id") int boatId){
    System.out.println("DELETE REQUEST: deleteBoat | Params(Boat ID: " + boatId + ")");
    return GSON.toJson(FACADE.deleteBoat(boatId));
  }

}
