package com.eCommers.eCommersApp.controller;

import com.eCommers.eCommersApp.model.Shipper;
import com.eCommers.eCommersApp.service.ShipperService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/ecom/order-shippers")
@RequiredArgsConstructor
public class ShipperController {

    private final ShipperService shipperService;

    @GetMapping("/{id}")
    public ResponseEntity<Shipper> getShipperById(@PathVariable Integer id) {
        Shipper shipper = shipperService.getShipperById(id);
        return ResponseEntity.ok(shipper);
    }

    @GetMapping
    public ResponseEntity<List<Shipper>> getAllShippers() {
        List<Shipper> shippers = shipperService.getAllShippers();
        return ResponseEntity.ok(shippers);
    }

    @PostMapping("/add")
    public ResponseEntity<Shipper> saveShipper(@Valid @RequestBody Shipper shipper) {
        Shipper savedShipper = shipperService.saveShipper(shipper);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedShipper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteShipperById(@PathVariable Integer id) {
        shipperService.deleteShipperById(id);
        return ResponseEntity.ok("Shipper with ID " + id + " successfully deleted.");
    }
}
