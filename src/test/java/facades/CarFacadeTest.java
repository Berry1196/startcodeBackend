package facades;

import dtos.CarDTO;
import entities.Car;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;

    Car c1, c2;

    public CarFacadeTest() {


    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CarFacade.getCarFacade(emf);
    }

    @AfterAll
    public static void tearDownClass() {

    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
         c1 = new Car("Ford", "Mustang", "AB12345");
         c2 = new Car("Tesla", "Model 3", "CD67890");
        try {
            em.getTransaction().begin();
            em.createNamedQuery("Car.deleteAllRows").executeUpdate();
            em.persist(c1);
            em.persist(c2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void getCarByID() {
        CarDTO car = facade.getCarById(c1.getId());
        assertEquals("Ford", car.getBrand());
        System.out.println(car);
    }


}
