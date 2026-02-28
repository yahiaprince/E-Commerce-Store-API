package com.yahiaprince.store.payments;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yahiaprince.store.auth.AuthService;
import com.yahiaprince.store.carts.CartEmptyException;
import com.yahiaprince.store.carts.CartNotFoundException;
import com.yahiaprince.store.carts.CartRepository;
import com.yahiaprince.store.carts.CartService;
import com.yahiaprince.store.orders.Order;
import com.yahiaprince.store.orders.OrderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(CheckoutRequest request) {
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if (cart == null) {
            throw new CartNotFoundException();
        }

        if (cart.isEmpty()) {
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);

        try {
            var session = paymentGateway.createCheckoutSession(order);
            cartService.clearCart(cart.getId());

            return new CheckoutResponse(order.getId(), session.getCheckoutUrl());
        } catch (PaymentException e) {
            orderRepository.delete(order);
            throw e;
        }
    }

    public void handleWebhookEvent(WebhookRequest request) {
        paymentGateway
                .parseWebhookRequest(request)
                .ifPresent(paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getPaymentStatus());
                    orderRepository.save(order);
                });
    }
}