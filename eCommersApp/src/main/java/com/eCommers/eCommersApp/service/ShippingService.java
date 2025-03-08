package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.dto.ShippingDTO;
import com.eCommers.eCommersApp.exception.ShippingException;
import com.eCommers.eCommersApp.model.Orders;
import com.eCommers.eCommersApp.model.Shipper;
import com.eCommers.eCommersApp.model.ShippingDetails;
import com.eCommers.eCommersApp.repo.OrderRepository;
import com.eCommers.eCommersApp.repo.ShipperRepository;
import com.eCommers.eCommersApp.repo.ShippingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingRepository shippingRepository;

    private final OrderRepository orderRepository;

    private final ShipperRepository shipperRepository;


    public ShippingDetails setShippingDetails(Integer orderId, Integer shipperId, ShippingDetails shippingDetails)
            throws ShippingException {
        if (shippingDetails == null)
            throw new ShippingException("ShippingDetails cannot be null");

        Orders existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new ShippingException("Order not found"));

        Shipper existingShipper = shipperRepository.findById(shipperId)
                .orElseThrow(() -> new ShippingException("Shipper not found"));

        // Save the ShippingDetails entity first
        shippingDetails.setOrders(existingOrder);
        shippingDetails.setShipper(existingShipper);
        shippingDetails = shippingRepository.save(shippingDetails);

        // Now update the Orders entity with the saved ShippingDetails
        existingOrder.setShippingDetails(shippingDetails);
        orderRepository.save(existingOrder);

        return shippingDetails;
    }



    public ShippingDetails updateShippingAddress(Integer shippingId, ShippingDTO shippingDTO) throws ShippingException {
        ShippingDetails existing = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new ShippingException("Shipping detail not found"));

        existing.setState(shippingDTO.getState());
        existing.setAddress(shippingDTO.getAddress());
        existing.setCity(shippingDTO.getCity());
        existing.setPostalCode(shippingDTO.getPostalCode());
        return shippingRepository.save(existing);
    }


    public void deleteShippingDetails(Integer shippingId) throws ShippingException {
        ShippingDetails existing = shippingRepository.findById(shippingId)
                .orElseThrow(() -> new ShippingException("Shipping detail not found"));

        shippingRepository.delete(existing);

    }

}
