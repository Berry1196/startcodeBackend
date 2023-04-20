package dtos;

import entities.Car;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
    private Long id;
    private String brand;
    private String model;
    private String numberPlate;


    public CarDTO(String brand, String model, String numberPlate) {
        this.brand = brand;
        this.model = model;
        this.numberPlate = numberPlate;
    }

    public CarDTO(Car c) {
        this.id = c.getId();
        this.brand = c.getBrand();
        this.model = c.getModel();
        this.numberPlate = c.getNumberPlate();
    }


    public  List<CarDTO> getDTOs(List<Car> car){
        List<CarDTO> carDTOs = new ArrayList();
        car.forEach(c->carDTOs.add(new CarDTO(c)));
        return carDTOs;
    }


}
