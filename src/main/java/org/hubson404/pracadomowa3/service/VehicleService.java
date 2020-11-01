package org.hubson404.pracadomowa3.service;

import lombok.RequiredArgsConstructor;
import org.hubson404.pracadomowa3.model.Vehicle;
import org.hubson404.pracadomowa3.controller.VehicleController;
import org.hubson404.pracadomowa3.model.VehicleColor;
import org.hubson404.pracadomowa3.repository.VehicleRepository;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;

    public CollectionModel<Vehicle> getAllVehicles() {
        List<Vehicle> vehicles = vehicleRepository.findAll();
        vehicles.forEach(vehicle -> vehicle
                .addIf(!vehicle.hasLinks(), () -> linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel()));
        Link link = linkTo(VehicleController.class).withSelfRel();
        return CollectionModel.of(vehicles, link);
    }

    public Optional<Vehicle> getVehicleById(long id) {
        Optional<Vehicle> byId = vehicleRepository.findById(id);
        if (byId.isPresent()) {
            if (!byId.get().hasLinks()) {
                addLinkToVehicle(byId.get());
            }
        }
        return byId;
    }

    public CollectionModel<Vehicle> getByColor(VehicleColor color) {
        List<Vehicle> list = vehicleRepository.findByColor(color);
        list.forEach(vehicle -> vehicle.addIf(!vehicle.hasLinks(), () -> linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel()));
        Link link = linkTo(methodOn(VehicleController.class).findVehiclesByColor(color)).withSelfRel();
        return new CollectionModel<>(list, link);
    }

    public boolean add(Vehicle vehicle) {
        return vehicleRepository.saveVehicle(vehicle);
    }

    public boolean modVehicle(Vehicle newVehicle) {
        return vehicleRepository.modVehicle(newVehicle);
    }

    public boolean deleteById(long id) {
        return vehicleRepository.deleteById(id);
    }

    public boolean patchVehicle(long id, String brand, String model, VehicleColor color) {
        Optional<Vehicle> byId = vehicleRepository.findById(id);
        boolean patch = byId.isPresent();
        if (patch) {
            Vehicle vehicle = byId.get();
            if (brand != null) {
                vehicle.setBrand(brand);
            }
            if (model != null) {
                vehicle.setModel(model);
            }
            if (color != null) {
                vehicle.setColor(color);
            }
        }
        return patch;
    }

    private Vehicle addLinkToVehicle(Vehicle vehicle) {
        return vehicle.add(linkTo(VehicleController.class).slash(vehicle.getId()).withSelfRel());
    }
}
