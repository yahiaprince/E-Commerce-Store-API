package com.yahiaprince.store.carts;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CartItemDto {
    private CartProductDto product;
    private Integer quantity;
    private BigDecimal totalPrice;
}
