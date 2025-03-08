package com.eCommers.eCommersApp.exception;

public class PaymentException extends RuntimeException {
    public PaymentException() {

    }
    public PaymentException(String msg) {
        super(msg);
    }

}
