package org.example.bookstore.dto.shoppingcart;

import java.util.Set;
import lombok.Data;
import org.example.bookstore.dto.cartitem.CartItemResponseDto;

@Data
public class ShoppingCartResponseDto {
    private Long id;
    private Long userId;
    private Set<CartItemResponseDto> cartItems;
}
