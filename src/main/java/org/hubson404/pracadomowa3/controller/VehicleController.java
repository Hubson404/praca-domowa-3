package org.hubson404.pracadomowa3.controller;

import lombok.RequiredArgsConstructor;
import org.hubson404.pracadomowa3.model.Vehicle;
import org.hubson404.pracadomowa3.model.VehicleColor;
import org.hubson404.pracadomowa3.service.VehicleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(path = "/api/vehicles", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public ResponseEntity<CollectionModel<Vehicle>> findAllVehicles() {
        CollectionModel<Vehicle> allVehicles = vehicleService.getAllVehicles();
        return new ResponseEntity(allVehicles, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Vehicle> findVehicleById(@PathVariable long id) {
        Optional<Vehicle> vehicleById = vehicleService.getVehicleById(id);
        if (vehicleById.isPresent()) {
            return new ResponseEntity<>(vehicleById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/byColor")
    public ResponseEntity<CollectionModel<Vehicle>> findVehiclesByColor(@RequestParam String color) {
        CollectionModel<Vehicle> byColor = vehicleService.getByColor(color);
        if (!byColor.hasLinks()) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity(byColor, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity addVehicle(@RequestBody @Valid Vehicle vehicle) {
        boolean add = vehicleService.add(vehicle);
        if (add) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @PutMapping
    public ResponseEntity modVehicle(@Valid @RequestBody Vehicle newVehicle) {
        boolean mod = vehicleService.modVehicle(newVehicle);
        if (mod) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @PatchMapping("/{id}")
    public ResponseEntity patchVehicle(@PathVariable long id,
                                       @RequestParam(required = false) String brand,
                                       @RequestParam(required = false) String model,
                                       @RequestParam(required = false) String color) {

        boolean patch = vehicleService.patchVehicle(id, brand, model, color);
        if (patch) {
            return new ResponseEntity(HttpStatus.ACCEPTED);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity removeVehicleById(@PathVariable long id) {
        boolean delete = vehicleService.deleteById(id);
        if (delete) {
            return new ResponseEntity(HttpStatus.OK);
        }
        return new ResponseEntity(HttpStatus.NOT_FOUND);
    }

}
