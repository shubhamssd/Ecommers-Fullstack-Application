package com.eCommers.eCommersApp.controller;

import com.eCommers.eCommersApp.dto.OrdersDTO;
import com.eCommers.eCommersApp.model.Orders;
import com.eCommers.eCommersApp.service.OrdersService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/ecom/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrdersService ordersService;

    @PostMapping("/placed/{userid}")
    public ResponseEntity<?> addOrderToCart(@PathVariable("userid") Integer userid) {
        OrdersDTO placeOrder = ordersService.placeOrder(userid);
        return ResponseEntity.ok(placeOrder);
    }

    @GetMapping("/{orderId}")
    public ResponseEntity<Orders> getOrdersDetails(@PathVariable("orderId") Integer orderId) {
        Orders order = ordersService.getOrdersDetails(orderId);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping("/orders/{userId}")
    public ResponseEntity<List<Orders>> getAllUserOrder(@PathVariable Integer userId) {
        List<Orders> orders = ordersService.getAllUserOrder(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Orders>> viewAllOrders() {
        List<Orders> orders = ordersService.viewAllOrders();
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/date/{date}")
    public ResponseEntity<List<Orders>> viewAllOrderByDate(
            @PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd") Date date) {
        List<Orders> orders = ordersService.viewAllOrderByDate(date);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @DeleteMapping("/users/{userId}/{orderId}")
    public ResponseEntity<String> deleteOrders(@PathVariable Integer userId, @PathVariable Integer orderId) {
        ordersService.deleteOrders(userId, orderId);
        return new ResponseEntity<>("Order successfully deleted.", HttpStatus.OK);
    }


}
