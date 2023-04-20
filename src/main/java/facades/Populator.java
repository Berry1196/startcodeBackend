/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import dtos.CarDTO;
import dtos.RenameMeDTO;
import entities.RenameMe;
import javax.persistence.EntityManagerFactory;
import utils.EMF_Creator;

/**
 *
 * @author tha
 */
public class Populator {
    public static void populate(){
        EntityManagerFactory emf = EMF_Creator.createEntityManagerFactory();
        CarFacade facade = CarFacade.getCarFacade(emf);
        facade.create(new CarDTO("Volvo", "V70", "ABC123"));
        facade.create(new CarDTO("BMW", "X5", "ABC123"));
        facade.create(new CarDTO("Audi", "R8", "ABC123"));

    }
    
    public static void main(String[] args) {
        populate();
    }
}
