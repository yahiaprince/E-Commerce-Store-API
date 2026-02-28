package com.yahiaprince.store.carts;

import java.util.UUID;
import org.springframework.stereotype.Service;

import com.yahiaprince.store.products.ProductNotFoundException;
import com.yahiaprince.store.products.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CartService {
    private CartRepository cartRepository;
    private CartMapper cartMapper;
    private ProductRepository productRepository;

    public CartDto createCart() {
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto addItemToCart(UUID cartId, Long productId) {
        var cart = cartRepository.findById(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();
        cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cart.getItem(productId));
    }

    public CartDto getCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        return cartMapper.toCartDto(cart);
    }

    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        var product = cart.getItem(productId);
        if (product == null)
            throw new ProductNotFoundException();
        cart.setQuantity(product, quantity);
        cartRepository.save(cart);
        return cartMapper.toCartItemDto(cart.getItem(productId));
    }

    public void removeCartItem(UUID cartId, Long productId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        var product = productRepository.findById(productId).orElse(null);
        if (product == null)
            throw new ProductNotFoundException();
        cart.removeItem(product);
        cartRepository.save(cart);
    }

    public void clearCart(UUID cartId) {
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if (cart == null)
            throw new CartNotFoundException();
        cart.clear();
        cartRepository.save(cart);
    }
}