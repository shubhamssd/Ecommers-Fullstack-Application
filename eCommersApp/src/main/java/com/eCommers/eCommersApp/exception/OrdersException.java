package com.eCommers.eCommersApp.exception;

public class OrdersException extends RuntimeException {

    public OrdersException(){

    }
    public OrdersException(String msg) {
        super(msg);
    }
}
