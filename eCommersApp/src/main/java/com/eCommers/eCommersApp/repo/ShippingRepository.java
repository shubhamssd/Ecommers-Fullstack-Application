package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.ShippingDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShippingRepository extends JpaRepository<ShippingDetails, Integer> {
}
