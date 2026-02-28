package com.yahiaprince.store.payments;

import java.util.Optional;

import com.yahiaprince.store.orders.Order;

public interface PaymentGateway {
    CheckoutSession createCheckoutSession(Order order);

    Optional<PaymentResult> parseWebhookRequest(WebhookRequest request);
}