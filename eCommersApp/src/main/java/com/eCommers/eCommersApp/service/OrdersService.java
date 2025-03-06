package com.eCommers.eCommersApp.service;


import com.eCommers.eCommersApp.Enum.OrderStatus;
import com.eCommers.eCommersApp.dto.OrdersDTO;
import com.eCommers.eCommersApp.exception.OrdersException;
import com.eCommers.eCommersApp.exception.UserException;
import com.eCommers.eCommersApp.model.*;
import com.eCommers.eCommersApp.repo.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrdersService {

    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    private final CartItemRepository cartItemRepository;

    private final CartRepository cartRepository;


    public OrdersDTO placeOrder(Integer userId) throws OrdersException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));

        Cart usercart = existingUser.getCart();
        if(usercart.getTotalAmount()==0){
            throw new OrdersException("Add item To the cart first.......");
        }
        Integer cartId = usercart.getCartId();

        Orders newOrder = new Orders();

        newOrder.setOrderDate(LocalDateTime.now());
        newOrder.setStatus(OrderStatus.PENDING);

        existingUser.getOrders().add(newOrder);
        newOrder.setUser(existingUser);
        userRepository.save(existingUser);
        orderRepository.save(newOrder);

        List<OrderItem> orderItems = new ArrayList<>();

        for (CartItem itemDTO : usercart.getCartItems()) {
            System.out.println("inside the loop");
            if (Objects.equals(itemDTO.getCart().getCartId(), cartId)) {

                OrderItem orderItem = new OrderItem();// creating New orderItem;

                orderItem.setQuantity(itemDTO.getQuantity());
                orderItem.setProduct(itemDTO.getProduct());
                orderItem.setOrderId(newOrder.getOrderId());
                orderItems.add(orderItem);
                System.out.println("inside the loop and if");
            }
        }

        newOrder.setOrderItem(orderItems);
        newOrder.setTotalAmount(usercart.getTotalAmount());
        orderRepository.save(newOrder);


        usercart.setTotalAmount(usercart.getTotalAmount() - newOrder.getTotalAmount());
        cartItemRepository.removeAllProductFromCart(cartId);
        cartRepository.save(usercart);

        OrdersDTO orderdata=new OrdersDTO();
        orderdata.setOrderId(newOrder.getOrderId());
        orderdata.setOrderAmount(newOrder.getTotalAmount());
        orderdata.setStatus("Pending");
        orderdata.setPaymentStatus("Pending");
        orderdata.setOrderDate("Current Date");
        return orderdata;

    }

    @Transactional
    public Orders getOrdersDetails(Integer orderId) throws OrdersException {

        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrdersException("Order not found in the database."));
    }


    public List<Orders> getAllUserOrder(Integer userId) throws OrdersException {
        try {
            List<Orders> orders = orderRepository.getAllOrderByUserId(userId);
            if (orders.isEmpty()) {
                throw new OrdersException("No orders found for the user in the database.");
            }
            return orders;
        } catch (Exception e) {
            throw new OrdersException("Failed to fetch orders for the user: " + e.getMessage());
        }
    }


    public List<Orders> viewAllOrders() throws OrdersException {

        List<Orders> orders = orderRepository.findAll();

        if (orders.isEmpty()) {
            throw new OrdersException("No orders found in the database.");
        }
        return orders;
    }


    public List<Orders> viewAllOrderByDate(Date date) throws OrdersException {

        List<Orders> orders = orderRepository.findByOrderDateGreaterThanEqual(date);

        if (orders.isEmpty()) {
            throw new OrdersException("No orders found for the given date.");
        }

        return orders;

    }


    public void deleteOrders(Integer userId, Integer Orderid) throws OrdersException {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserException("User Not Found In Database"));
        Orders existingOrder = orderRepository.findById(Orderid)
                .orElseThrow(() -> new UserException("order Not Found In Database"));

        orderRepository.delete(existingOrder);
    }




}