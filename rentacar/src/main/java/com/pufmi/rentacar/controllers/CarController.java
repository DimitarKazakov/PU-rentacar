package com.pufmi.rentacar.controllers;

import com.pufmi.rentacar.base.AppResponse;
import com.pufmi.rentacar.models.Car;
import com.pufmi.rentacar.services.CarService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class CarController {
    private CarService service;

    public CarController(CarService service) {
        this.service = service;
    }

    @GetMapping("/cars")
    public ResponseEntity<?> getAll(@RequestParam(required = false, defaultValue = "") String location) {
        if (!location.isBlank() && !location.equals("Plovdiv") && !location.equals("Sofia") && !location.equals("Varna") && !location.equals("Burgas")) {
            return AppResponse.error().withMessage("location not supported").build();
        }

        var customers = this.service.getAll(location);

        return AppResponse.success().withData(customers).build();
    }

    @GetMapping("/cars/{id}")
    public ResponseEntity<?> getSpecific(@PathVariable int id) {
        var customer = this.service.getSpecific(id);

        if (customer == null) {
            return AppResponse.error().withMessage("data not found").build();
        }

        return AppResponse.success().withDataAsArray(customer).build();
    }

    @PostMapping("/cars")
    public ResponseEntity<?> create(@RequestBody Car data) {
        var location = data.getLocation();
        if (!location.isBlank() && !location.equals("Plovdiv") && !location.equals("Sofia") && !location.equals("Varna") && !location.equals("Burgas")) {
            return AppResponse.error().withMessage("location not supported").build();
        }

        if (this.service.create(data)) {
            return AppResponse.success().withMessage("entity was created successfully").build();
        }

        return AppResponse.error().withMessage("error during creation").build();
    }

    @PutMapping("/cars/{id}")
    public ResponseEntity<?> update(@PathVariable int id, @RequestBody Car data) {
        var location = data.getLocation();
        if (!location.isBlank() && !location.equals("Plovdiv") && !location.equals("Sofia") && !location.equals("Varna") && !location.equals("Burgas")) {
            return AppResponse.error().withMessage("location not supported").build();
        }

        if (this.service.update(id, data)) {
            return AppResponse.success().withMessage("entity was updated successfully").build();
        }

        return AppResponse.error().withMessage("error during updation").build();
    }

    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (this.service.delete(id)) {
            return AppResponse.success().withMessage("entity was deleted successfully").build();
        }

        return AppResponse.error().withMessage("error during deletion").build();
    }
}
