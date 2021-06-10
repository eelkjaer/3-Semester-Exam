package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.ws.rs.GET;
import javax.ws.rs.InternalServerErrorException;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import org.apache.commons.io.IOUtils;

@Path("covid")
public class ExternalResource {
  private static final Gson GSON = new GsonBuilder().setPrettyPrinting().serializeNulls().create();

  @GET
  @Produces(MediaType.APPLICATION_JSON)
  public String covidNumbers() throws WebApplicationException {
    try {
      URL url = new URL("https://api.apify.com/v2/key-value-stores/EAlpwScH29Qa5m60g/records/LATEST?disableRedirect=true");
      HttpURLConnection con = (HttpURLConnection) url.openConnection();
      con.setRequestMethod("GET");
      con.setRequestProperty("Accept", "application/json;charset=UTF-8");
      con.addRequestProperty("User-Agent", "server");
      int responseCode = con.getResponseCode();

      if(responseCode == 200){
        CovidDTO dto = GSON.fromJson(IOUtils.toString(con.getInputStream()), CovidDTO.class);
        return GSON.toJson(dto);
      } else {
        switch (responseCode){
          case 404:
            throw new NotFoundException();
          case 500:
            throw new InternalServerErrorException();
          default:
            throw new WebApplicationException();
        }
      }

    } catch (IOException ex) {
      throw new WebApplicationException(ex.getCause());
    }
  }

  private class CovidDTO {

    private int dailyInfected;
    private int dailyRecovered;
    private int intensive;
    private int dailyDead;

    public CovidDTO() {
    }

    public CovidDTO(int dailyInfected, int dailyRecovered, int intensive, int dailyDead) {
      this.dailyInfected = dailyInfected;
      this.dailyRecovered = dailyRecovered;
      this.intensive = intensive;
      this.dailyDead = dailyDead;
    }

    public int getDailyInfected() {
      return dailyInfected;
    }

    public void setDailyInfected(int dailyInfected) {
      this.dailyInfected = dailyInfected;
    }

    public int getDailyRecovered() {
      return dailyRecovered;
    }

    public void setDailyRecovered(int dailyRecovered) {
      this.dailyRecovered = dailyRecovered;
    }

    public int getIntensive() {
      return intensive;
    }

    public void setIntensive(int intensive) {
      this.intensive = intensive;
    }

    public int getDailyDead() {
      return dailyDead;
    }

    public void setDailyDead(int dailyDead) {
      this.dailyDead = dailyDead;
    }

    @Override
    public String toString() {
      return "CovidDTO{" +
          "dailyInfected=" + dailyInfected +
          ", dailyRecovered=" + dailyRecovered +
          ", intensive=" + intensive +
          ", dailyDead=" + dailyDead +
          '}';
    }

  }
}
