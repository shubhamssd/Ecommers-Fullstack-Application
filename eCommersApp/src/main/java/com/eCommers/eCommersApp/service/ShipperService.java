package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.exception.ShipperException;
import com.eCommers.eCommersApp.model.Shipper;
import com.eCommers.eCommersApp.repo.ShipperRepository;
import com.eCommers.eCommersApp.repo.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShipperService {

    private final ShipperRepository shipperRepository;

    @Autowired
    private ShippingRepository shippingRepository;




    public Shipper saveShipper(Shipper shipper) throws ShipperException {
        if (shipper == null) {
            throw new ShipperException("Shipper object cannot be null.");
        }
        return shipperRepository.save(shipper);
    }


    public void deleteShipperById(Integer id) throws ShipperException {
        Optional<Shipper> optionalShipper = shipperRepository.findById(id);
        if (optionalShipper.isPresent()) {
            shipperRepository.deleteById(id);
        } else {
            throw new ShipperException("Shipper with ID " + id + " not found.");
        }
    }


    public Shipper getShipperById(Integer id) throws ShipperException {
        return shipperRepository.findById(id)
                .orElseThrow(() -> new ShipperException("Shipper with ID " + id + " not found."));
    }


    public List<Shipper> getAllShippers() throws ShipperException {
        List<Shipper> shippers = shipperRepository.findAll();
        if (shippers.isEmpty()) {
            throw new ShipperException("No shippers found.");
        }
        return shippers;
    }
}
