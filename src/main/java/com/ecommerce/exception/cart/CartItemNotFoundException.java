package com.ecommerce.exception.cart;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException() {
        super("Product not found in cart");
    }
}

