package org.example.bookstore.dto.shoppingcart;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UpdateShoppingCartRequestDto {
    private Long bookId;
    @Min(1)
    private int quantity;
}
