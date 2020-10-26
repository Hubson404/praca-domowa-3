package org.hubson404.pracadomowa3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle extends RepresentationModel<Vehicle> {

    private long id;

    @NotNull(message = "brand cannot be null")
    private String brand;
    @NotNull(message = "model cannot be null")
    private String model;
    @NotNull(message = "color cannot be null")
    private String color;

}
