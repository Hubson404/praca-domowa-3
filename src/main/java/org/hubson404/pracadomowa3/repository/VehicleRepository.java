package org.hubson404.pracadomowa3.repository;

import org.hubson404.pracadomowa3.model.Vehicle;
import org.hubson404.pracadomowa3.model.VehicleColor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.hubson404.pracadomowa3.model.VehicleColor.*;

@Repository
public class VehicleRepository {

    private final List<Vehicle> vehicleList;

    @EventListener(ApplicationReadyEvent.class)
    public void initRepository() {
        vehicleList.add(new Vehicle(1L, "Tesla", "S", WHITE));
        vehicleList.add(new Vehicle(2L, "Audi", "RS6", GREY));
        vehicleList.add(new Vehicle(3L, "Fiat", "126P", YELLOW));
    }

    public VehicleRepository() {
        this.vehicleList = new ArrayList<>();
    }

    public List<Vehicle> findAll() {
        return vehicleList;
    }

    public Optional<Vehicle> findById(long id) {
        return vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
    }

    public List<Vehicle> findByColor(VehicleColor color) {
        return vehicleList.stream()
                .filter(vehicle -> Objects.equals(color, vehicle.getColor()))
                .collect(Collectors.toList());
    }

    public boolean saveVehicle(Vehicle vehicle) {
        return vehicleList.add(vehicle);
    }

    public boolean modVehicle(Vehicle newVehicle) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == newVehicle.getId()).findFirst();
        if (first.isPresent()) {
            vehicleList.remove(first.get());
            return vehicleList.add(newVehicle);
        }
        return false;
    }

    public boolean deleteById(long id) {
        Optional<Vehicle> first = vehicleList.stream().filter(vehicle -> vehicle.getId() == id).findFirst();
        if (first.isPresent()) {
            return vehicleList.remove(first.get());
        }
        return false;
    }
}
