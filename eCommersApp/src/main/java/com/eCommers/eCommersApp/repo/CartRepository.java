package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
