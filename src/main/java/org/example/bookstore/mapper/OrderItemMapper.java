package org.example.bookstore.mapper;

import java.util.Set;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.orderitem.OrderItemResponseDto;
import org.example.bookstore.model.CartItem;
import org.example.bookstore.model.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface OrderItemMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "price", source = "book.price")
    OrderItem toOrderItem(CartItem cartItem);

    @Mapping(target = "bookId", source = "book.id")
    OrderItemResponseDto toOrderItemResponseDto(OrderItem orderItem);

    Set<OrderItem> toOrderItemSet(Set<CartItem> cartItems);

    Set<OrderItemResponseDto> toOrderItemResponseDtoSet(Set<OrderItem> orderItems);
}
