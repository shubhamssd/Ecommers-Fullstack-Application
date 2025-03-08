package com.eCommers.eCommersApp.service;

import com.eCommers.eCommersApp.Enum.OrderStatus;
import com.eCommers.eCommersApp.Enum.PaymentMethod;
import com.eCommers.eCommersApp.Enum.PaymentStatus;
import com.eCommers.eCommersApp.exception.PaymentException;
import com.eCommers.eCommersApp.exception.UserException;
import com.eCommers.eCommersApp.model.Orders;
import com.eCommers.eCommersApp.model.Payment;
import com.eCommers.eCommersApp.model.User;
import com.eCommers.eCommersApp.repo.OrderRepository;
import com.eCommers.eCommersApp.repo.PaymentRepository;
import com.eCommers.eCommersApp.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;


    public Payment makePayment(Integer orderId, Integer userId) throws PaymentException {

        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User not found in the database."));

        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new UserException("order not found in the database."));;
        if (order == null) {
            throw new PaymentException("Order not found for the given customer.");
        }

        Payment payment = new Payment();
        payment.setPaymentAmount(order.getTotalAmount());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(PaymentMethod.UPI);
        payment.setPaymentStatus(PaymentStatus.SUCCESSFUL);
        payment.setUser(existingUser);

        payment.setOrder(order);
        paymentRepository.save(payment);

        order.setStatus(OrderStatus.SHIPPED);

        // Set the payment for the order
        order.setPayment(payment);
        // Save the changes to the Order entity, including the associated Payment
        orderRepository.save(order);

        existingUser.getPayments().add(payment);
        // Save the changes to the User entity, including the new payment association
        userRepository.save(existingUser);
        // Save the payment to the database
        return  payment;
    }
}
