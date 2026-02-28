package com.yahiaprince.store.carts;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartDto {
    private UUID id;
    private List<CartItemDto> items = new ArrayList<>();
    private BigDecimal totalPrice = BigDecimal.ZERO;
}