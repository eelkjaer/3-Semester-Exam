package rest;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import entities.Owner;
import entities.User;
import facades.UserFacade;
import java.util.Date;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import security.SharedSecret;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

@Path("auth")
public class AuthResource {

  public static final int TOKEN_EXPIRE_TIME = 1000 * 60 * 30; //30 min
  private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
  public static final UserFacade USER_FACADE = UserFacade.getFacade(EMF);

  @POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response login(String jsonString) throws NotAuthorizedException {
    JsonObject json = new JsonParser().parse(jsonString).getAsJsonObject();
    String username = json.get("username").getAsString();
    String password = json.get("password").getAsString();

    try {
      User user = USER_FACADE.getVerfiedUser(username, password);
      String token = createToken(username, user.getRolesAsStrings());
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("username", username);
      responseJson.addProperty("token", token);
      return Response.ok(new Gson().toJson(responseJson)).build();

    } catch (JOSEException | NotAuthorizedException ex) {
      if (ex instanceof NotAuthorizedException) {
        throw (NotAuthorizedException) ex;
      }
    }
    throw new NotAuthorizedException("Invalid username or password! Please try again");
  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("admininfo")
  @RolesAllowed("admin")
  public Response checkIfAdmin(){
    return Response.ok().build();
  }

  @GET
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("userinfo")
  @RolesAllowed({"user", "admin"})
  public Response checkIfUser(){
    return Response.ok().build();
  }

  /*@POST
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @Path("register")
  public Response register(String jsonString) throws BadRequestException {
    String email, name;
    try {
      JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
      email = json.get("email").getAsString();
      name = json.get("name").getAsString();
    } catch (Exception e) {
      throw new BadRequestException("Malformed JSON Suplied",e);
    }

    try{
    Owner owner = USER_FACADE.registerStudent(email, name);
      if (owner.getId() != -1) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("username", owner());
        responseJson.addProperty("password", owner.getName());
        return Response.ok(Status.CREATED).build();
    } else {
        return Response.status(Status.fromStatusCode(400)).build();
      }
    } catch (WebApplicationException e) {
      throw new BadRequestException("User already exists");
    }
  }*/


  private String createToken(String userName, List<String> roles) throws JOSEException {

    StringBuilder res = new StringBuilder();
    for (String string : roles) {
      res.append(string);
      res.append(",");
    }
    String rolesAsString = res.length() > 0 ? res.substring(0, res.length() - 1) : "";
    String issuer = "eenielsen.dk";

    JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
    Date date = new Date();
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(userName)
        .claim("username", userName)
        .claim("roles", rolesAsString)
        .claim("issuer", issuer)
        .issueTime(date)
        .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
        .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    return signedJWT.serialize();

  }
}