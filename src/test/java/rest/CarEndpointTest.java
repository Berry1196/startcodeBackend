package rest;

import entities.Car;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CarEndpointTest {
    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    Car c1, c2, c3, c4;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
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
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
         c1 = new Car("Volvo", "V70", "ABC1234");
         c2 = new Car("BMW", "M3", "DEF5678");
         c3 = new Car("Audi", "A4", "GHI9101");
         c4 = new Car("Ford", "Mustang", "JKL1112");
        try {
            em.getTransaction().begin();
            //Delete existing users and roles to get a "fresh" database
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.persist(c3);
            em.persist(c4);

            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given()
                .when()
                .get("/cars")
                .then()
                .statusCode(200);
    }

    // Rest assured test that verifies that the endpoint returns the number of rows in the Car table.
    @Test
    public void testCount() throws Exception {
        given()
                .contentType("application/json")
                .get("/cars").then()
                .assertThat()
                .statusCode(200).body("size()", org.hamcrest.Matchers.is(4));
    }

    // Rest assured test that verifies that the endpoint returns the correct car.
    @Test
    public void testGetCarById() throws Exception {
        given()
                .contentType("application/json")
                .get("/cars/{id}", c1.getId()).then()
                .assertThat()
                .body("brand", equalTo("Volvo"))
                .body("model", equalTo("V70"))
                .body("numberPlate", equalTo("ABC1234"));

    }
}
