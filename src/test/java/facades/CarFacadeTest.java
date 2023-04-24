package facades;

import dtos.CarDTO;
import entities.Car;
import org.junit.jupiter.api.*;
import utils.EMF_Creator;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

public class CarFacadeTest {

    private static EntityManagerFactory emf;
    private static CarFacade facade;

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
        try {
            em.getTransaction().begin();
            em.persist(new Car("Ford", "Mustang", "AB12345"));
            em.persist(new Car("Tesla", "Model 3", "AB12345"));
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
        CarDTO car = facade.getCarById(1L);
        Assertions.assertEquals("Ford", car.getBrand());
        System.out.println(car);
    }


}
