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
import entities.Student;
import entities.Teacher;
import facades.UserFacade;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.Consumes;
import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
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
  public Response login(String jsonString) throws BadRequestException, NotAuthorizedException {
    String username;
    String password;
    try {
      JsonObject json = JsonParser.parseString(jsonString).getAsJsonObject();
      username = json.get("email").getAsString();
      password = json.get("password").getAsString();
    } catch (Exception e) {
      throw new BadRequestException("Malformed JSON Suplied",e);
    }

    try {
      Teacher teacher = USER_FACADE.getVerfiedUser(username, password);
      String token = createToken(username);
      JsonObject responseJson = new JsonObject();
      responseJson.addProperty("username", username);
      responseJson.addProperty("token", token);
      return Response.ok(new Gson().toJson(responseJson)).build();

    } catch (JOSEException ex) {

      Logger.getLogger(ex.getClass().getName()).log(Level.SEVERE, null, ex);
    }
    throw new NotAuthorizedException("Invalid username or password! Please try again");
  }

  @POST
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
    Student student = USER_FACADE.registerStudent(email, name);
      if (student.getId() != -1) {
        JsonObject responseJson = new JsonObject();
        responseJson.addProperty("email", student.getEmail());
        responseJson.addProperty("name", student.getName());
        return Response.ok(Status.CREATED).build();
    } else {
        return Response.status(Status.fromStatusCode(400)).build();
      }
    } catch (WebApplicationException e) {
      throw new BadRequestException("User already exists");
    }
  }


  private String createToken(String userName) throws JOSEException {
    String issuer = "codergram.me";

    JWSSigner signer = new MACSigner(SharedSecret.getSharedKey());
    Date date = new Date();
    JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
        .subject(userName)
        .claim("username", userName)
        .claim("roles", "admin")
        .claim("issuer", issuer)
        .issueTime(date)
        .expirationTime(new Date(date.getTime() + TOKEN_EXPIRE_TIME))
        .build();
    SignedJWT signedJWT = new SignedJWT(new JWSHeader(JWSAlgorithm.HS256), claimsSet);
    signedJWT.sign(signer);
    return signedJWT.serialize();

  }
}