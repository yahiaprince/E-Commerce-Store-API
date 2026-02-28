package com.yahiaprince.store.carts;

public class CartEmptyException extends RuntimeException{
    public CartEmptyException() {
        super("Cart is empty.");
    }
}