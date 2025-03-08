package com.eCommers.eCommersApp.repo;

import com.eCommers.eCommersApp.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
