package com.yahiaprince.store.carts;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class CartProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}
