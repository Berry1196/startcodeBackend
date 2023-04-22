package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import dtos.CarDTO;
import facades.CarFacade;
import facades.FacadeExample;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Path("cars")
public class CarResource {
    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();

    private static final CarFacade FACADE =  CarFacade.getCarFacade(EMF);
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @GET
    @Produces("application/json")
    public String getAllCars() {
        return GSON.toJson(FACADE.getAllCars());
    }
    @POST
    @Consumes("application/json")
    @Produces("application/json")
    public String addCar(String car) {

        CarDTO carDTO = GSON.fromJson(car, CarDTO.class);
        return GSON.toJson(FACADE.create(carDTO));
    }
    @DELETE
    @Path("{id}")
    public void deleteCar(@PathParam("id") int id) {
        Long idLong = Long.valueOf(id);
        FACADE.deleteCar(idLong);
    }

    @GET
    @Path("jokes")
    @Produces("application/json")
    public String getJokes() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.chucknorris.io/jokes/random"))
                .method("GET", HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = null;
        try {
            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return response.body();
    }
//    @POST
//    @Path("jokes")
//    @Produces("application/json")
//    @Consumes("application/json")
//    public String createJoke(String jokes) {
//        String joke;
//        String result;
//        HttpRequest request = HttpRequest.newBuilder()
//                .uri(URI.create("https://api.chucknorris.io/jokes/random"))
//                .method("GET", HttpRequest.BodyPublishers.noBody())
//                .build();
//        HttpResponse<String> response = null;
//        try{
//            JsonObject json = JsonParser.parseString(jokes).getAsJsonObject();
//            joke = json.get("joke").getAsString();
//            response = HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
//            result = response.body();
//        } catch (Exception e) {
//            return "{\"error\":\"Invalid JSON\"}";
//        }
//
//        return GSON.toJson(result);
//
//    }

}
