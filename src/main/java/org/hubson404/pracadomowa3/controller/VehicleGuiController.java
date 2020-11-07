package org.hubson404.pracadomowa3.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hubson404.pracadomowa3.model.Vehicle;
import org.hubson404.pracadomowa3.service.VehicleService;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VehicleGuiController {

    private final VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String showAllVehicles(Model model) {
        CollectionModel<Vehicle> allVehicles = vehicleService.getAllVehicles();
        model.addAttribute("allVehicles", allVehicles);
        return "vehicles";
    }

    @GetMapping("/modify/{id}")
    public String openModifyForm(Model model, @PathVariable long id) {
        Optional<Vehicle> vehicleById = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicleById.get());
        return "modifyForm";
    }

    @GetMapping("mod-vehicle")
    public String modifyVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.patchVehicle(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor());
        return "redirect:/vehicles";
    }

    @GetMapping("/vehicles/{id}")
    public String findVehicleById(Model model, @PathVariable Long id) {
        Optional<Vehicle> vehicleById = vehicleService.getVehicleById(id);
        if (vehicleById.isEmpty()) {
            return "redirect:/vehicles";
        }
        model.addAttribute("vehicle", vehicleById.get());
        return "vehicleById";
    }

    @PostMapping("/getVehicle")
    public String getVehicleById(Long id) {
        if (id == null){
            return "redirect:/vehicles";
        }
        return "redirect:/vehicles/" + id;
    }

    @PostMapping("/postVehicle")
    public String postVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.add(vehicle);
        return "redirect:/vehicles";
    }

    @GetMapping("/addVehicle")
    public String addVehicle(Model model) {
        model.addAttribute("newVehicle", new Vehicle());
        Long aLong = vehicleService.getAllVehicles()
                .getContent()
                .stream()
                .map(vehicle -> vehicle.getId())
                .max(Long::compareTo)
                .get();
        model.addAttribute("nextId", aLong + 1);
        return "addVehicle";
    }

    @GetMapping("/delete/{id}")
    public String removeVehicleById(@PathVariable long id) {
        vehicleService.deleteById(id);
        return "redirect:/vehicles";
    }
}
