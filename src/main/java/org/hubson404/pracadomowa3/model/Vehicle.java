package org.hubson404.pracadomowa3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends RepresentationModel<Vehicle> {

    private long id;
    @NotEmpty(message = "brand cannot be empty")
    private String brand;
    @NotEmpty(message = "model cannot be empty")
    private String model;
    @NotEmpty(message = "color cannot be empty")
    private VehicleColor color;

}
