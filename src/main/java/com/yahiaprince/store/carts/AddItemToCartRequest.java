package com.yahiaprince.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddItemToCartRequest {
    @NotNull
    private Long productId;
}