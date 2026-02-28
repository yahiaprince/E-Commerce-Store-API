package com.yahiaprince.store.payments;

import com.yahiaprince.store.orders.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public class PaymentResult {
   private Long orderId;
   private PaymentStatus paymentStatus;
}