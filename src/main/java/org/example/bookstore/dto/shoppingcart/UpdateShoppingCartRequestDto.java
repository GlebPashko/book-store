package org.example.bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class UpdateShoppingCartRequestDto {
    @Positive
    private int quantity;
}
