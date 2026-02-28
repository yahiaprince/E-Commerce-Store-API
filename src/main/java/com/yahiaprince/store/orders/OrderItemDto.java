package com.yahiaprince.store.orders;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class OrderItemDto {
    private OrderProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
