package com.ecommerce.exception.order;

public class InvalidCartItemException extends RuntimeException {
    public InvalidCartItemException() {
        super("Invalid item in cart");
    }
}

