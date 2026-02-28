package com.yahiaprince.store.payments;

import com.yahiaprince.store.carts.CartEmptyException;
import com.yahiaprince.store.carts.CartNotFoundException;
import com.yahiaprince.store.common.ErrorDto;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/checkout")
@Tag(name = "Checkout")
public class CheckoutController {
    private final CheckoutService checkoutService;
    @PostMapping("/webhook")
    public void handleWebhook(
            @RequestHeader Map<String, String> headers, @RequestBody String payload) {
        checkoutService.handleWebhookEvent(new WebhookRequest(headers, payload));
    }
    @PostMapping
    public CheckoutResponse checkout(@Valid @RequestBody CheckoutRequest request) {
        return checkoutService.checkout(request);
    }
    @ExceptionHandler(PaymentException.class)
    public ResponseEntity<?> handlePaymentException(PaymentException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorDto("Error Creating a Checkout Session."));
    }

    @ExceptionHandler({ CartNotFoundException.class, CartEmptyException.class })
    public ResponseEntity<ErrorDto> handleException(Exception ex) {
        return ResponseEntity.badRequest().body(new ErrorDto(ex.getMessage()));
    }
}