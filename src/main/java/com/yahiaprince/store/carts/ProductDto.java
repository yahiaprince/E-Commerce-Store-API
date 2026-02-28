package com.yahiaprince.store.carts;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductDto {
    private Long id;
    private String name;
    private String description;
    private double price;
    private Long categoryId;

}