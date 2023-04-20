package facades;

import dtos.CarDTO;
import dtos.RenameMeDTO;
import entities.Car;
import entities.RenameMe;
import utils.EMF_Creator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 *
 * Rename Class to a relevant name Add add relevant facade methods
 */
public class CarFacade {

    private static CarFacade instance;
    private static EntityManagerFactory emf;

    //Private Constructor to ensure Singleton
    private CarFacade() {}
    
    
    /**
     * 
     * @param _emf
     * @return an instance of this facade class.
     */
    public static CarFacade getCarFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CarFacade();
        }
        return instance;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }
    
    public CarDTO create(CarDTO carDTO){
        Car car = new Car(carDTO.getBrand(), carDTO.getModel(), carDTO.getNumberPlate());
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
        return new CarDTO(car);
    }
    public List<CarDTO> getAllCars() {
        EntityManager em = emf.createEntityManager();
        TypedQuery<Car> query = em.createQuery("SELECT c FROM Car c", Car.class);
        List<Car> cars = query.getResultList();
        return new CarDTO().getDTOs(cars);
    }


    public void deleteCar(Long id) {
        EntityManager em = emf.createEntityManager();
        Car car = em.find(Car.class, id);
        try {
            em.getTransaction().begin();
            em.remove(car);
            em.getTransaction().commit();
        } finally {
            em.close();
        }

    }
}
