package com.yahiaprince.store.carts;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class UpdateCartItemRequest {
    @NotNull(message = "Quantity must be provided.")
    @Min(value = 1, message = "Quantity must be at least 1.")
    @Max(value = 100, message = "Quantity must not exceed 100.")
    public Integer quantity;
    
}