package org.example.bookstore.mapper;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.cartitem.CartItemResponseDto;
import org.example.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import org.example.bookstore.model.CartItem;
import org.example.bookstore.model.ShoppingCart;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(config = MapperConfig.class)
public interface ShoppingCartMapper {
    ShoppingCartResponseDto toShoppingCartResponseDto(ShoppingCart shoppingCart);

    @AfterMapping
    default void setTitleAndBookId(@MappingTarget ShoppingCartResponseDto responseDto,
                                   ShoppingCart shoppingCart) {
        Map<Long, CartItemResponseDto> dtoMap = responseDto.getCartItems().stream()
                .collect(Collectors.toMap(CartItemResponseDto::getId, Function.identity()));

        for (CartItem cartItem : shoppingCart.getCartItems()) {
            CartItemResponseDto dto = dtoMap.get(cartItem.getId());
            if (dto != null && cartItem.getBook() != null) {
                dto.setBookId(cartItem.getBook().getId());
                dto.setBookTitle(cartItem.getBook().getTitle());
            }
        }
    }

    @AfterMapping
    default void setUserId(@MappingTarget ShoppingCartResponseDto responseDto,
                           ShoppingCart shoppingCart) {
        responseDto.setUserId(shoppingCart.getUser().getId());
    }
}
