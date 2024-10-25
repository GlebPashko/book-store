package org.example.bookstore.mapper;

import java.util.Set;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.order.OrderResponseDto;
import org.example.bookstore.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class, uses = OrderItemMapper.class)
public interface OrderMapper {
    @Mapping(target = "userId", source = "user.id")
    OrderResponseDto orderResponseDto(Order order);

    Set<OrderResponseDto> orderResponseDtoSet(Page<Order> order);
}
