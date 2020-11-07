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

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VehicleGuiController {

    private final VehicleService vehicleService;

    @GetMapping("/vehicles")
    public String findAllVehicles(Model model) {
        CollectionModel<Vehicle> allVehicles = vehicleService.getAllVehicles();
        model.addAttribute("allVehicles", allVehicles);
        return "vehicles";
    }

    @GetMapping("/delete/{id}")
    public String removeVehicleById(@PathVariable long id) {
        boolean delete = vehicleService.deleteById(id);
        return "redirect:/vehicles";
    }

    @GetMapping("/modify/{id}")
    public String goToModify(Model model, @PathVariable long id) {
        Optional<Vehicle> vehicleById = vehicleService.getVehicleById(id);
        model.addAttribute("vehicle", vehicleById.get());
        return "modify";
    }

    @GetMapping("mod-vehicle")
    public String modifyVehicle(@ModelAttribute Vehicle vehicle) {
        vehicleService.patchVehicle(vehicle.getId(), vehicle.getBrand(), vehicle.getModel(), vehicle.getColor());
        return "redirect:/vehicles";
    }

    @GetMapping("/vehicles/{id}")
    public String showVehicle(Model model, @PathVariable Long id){
        Vehicle vehicle = vehicleService.getVehicleById(id).get();
        model.addAttribute("vehicle", vehicle);
        return "distinct-vehicle";
    }

    @PostMapping("/getVehicle")
    public String getVehicleById(Long id) {
        return "redirect:/vehicles/" + id;
    }

    @PostMapping("/postVehicle")
    public String postVehicle(@ModelAttribute Vehicle vehicle){
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
        model.addAttribute("nextId", aLong+1);
        return "addVehicle";
    }

}
