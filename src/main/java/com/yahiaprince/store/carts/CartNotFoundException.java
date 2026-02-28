package com.yahiaprince.store.carts;

public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException() {
        super("Cart not found.");
    }
}