package entities;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name = "Cars")
public class Car  {
    @Id
    @GeneratedValue
   private Long id;
   private String brand;
   private String model;
   private String numberPlate;

   public Car (String brand, String model, String numberPlate){
       this.brand = brand;
       this.model = model;
       this.numberPlate = numberPlate;
   }
}

