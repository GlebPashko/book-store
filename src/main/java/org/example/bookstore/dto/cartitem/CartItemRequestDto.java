package org.example.bookstore.dto.cartitem;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class CartItemRequestDto {
    private Long bookId;
    @Min(1)
    private int quantity;
}
