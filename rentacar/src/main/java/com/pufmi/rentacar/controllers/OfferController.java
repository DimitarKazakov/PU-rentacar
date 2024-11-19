package com.pufmi.rentacar.controllers;

import com.pufmi.rentacar.base.AppResponse;
import com.pufmi.rentacar.models.Car;
import com.pufmi.rentacar.models.Offer;
import com.pufmi.rentacar.services.CarService;
import com.pufmi.rentacar.services.ClientService;
import com.pufmi.rentacar.services.OfferService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Controller
public class OfferController {
    private CarService carService;
    private OfferService offerService;
    private ClientService clientService;

    public OfferController(CarService carService, OfferService offerService, ClientService clientService) {
        this.carService = carService;
        this.clientService = clientService;
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> create(@RequestBody Offer data) {
        var carData = this.carService.getSpecific(data.getCarId());
        if (carData == null) {
            return AppResponse.error().withMessage("car not found").build();
        }

        data.setCarId(carData.getId());
        data.setCar(carData);

        if (data.getClient() == null) {
            return AppResponse.error().withMessage("client info is empty").build();
        }

        var clientData = this.clientService.getByNamePhone(data.getClient().getName(), data.getClient().getPhone());
        if (clientData == null) {
            if (!this.clientService.create(data.getClient()))
            {
                return AppResponse.error().withMessage("error why creating the client").build();
            }

            clientData = this.clientService.getByNamePhone(data.getClient().getName(), data.getClient().getPhone());
        }

        data.setClientId(clientData.getId());
        data.setClient(clientData);

        data.setFinalPrice(this.offerService.calculateFinalPrice(data));

        if (this.offerService.create(data)) {
            return AppResponse.success().withMessage("offer was created successfully").build();
        }

        return AppResponse.error().withMessage("offer during creation").build();
    }

    @GetMapping("/offers")
    public ResponseEntity<?> getAll(@RequestParam(required = true, defaultValue = "") String clientName, @RequestParam(required = true, defaultValue = "") String clientPhone) {
        if (clientName.isBlank() || clientPhone.isBlank()) {
            return AppResponse.error().withMessage("missing client data").build();
        }

        var clientData = this.clientService.getByNamePhone(clientName,clientPhone);
        if (clientData == null) {
            return AppResponse.error().withMessage("client not found").build();
        }

        var offers = this.offerService.getAll(clientData.getId());

        return AppResponse.success().withData(offers).build();
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> getSpecific(@PathVariable int id) {
        var offer = this.offerService.getSpecific(id);

        if (offer == null) {
            return AppResponse.error().withMessage("data not found").build();
        }

        return AppResponse.success().withDataAsArray(offer).build();
    }

    @PutMapping("/offers/{id}")
    public ResponseEntity<?> accept(@PathVariable int id) {
        if (this.offerService.accept(id)) {
            return AppResponse.success().withMessage("entity was accepted successfully").build();
        }

        return AppResponse.error().withMessage("error during accepting").build();
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> delete(@PathVariable int id) {
        if (this.offerService.delete(id)) {
            return AppResponse.success().withMessage("entity was deleted successfully").build();
        }

        return AppResponse.error().withMessage("error during deletion").build();
    }
}
