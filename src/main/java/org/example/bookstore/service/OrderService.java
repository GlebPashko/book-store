package org.example.bookstore.service;

import java.util.Set;
import org.example.bookstore.dto.order.CreateOrderRequestDto;
import org.example.bookstore.dto.order.OrderResponseDto;
import org.example.bookstore.dto.order.UpdateOrderRequestDto;
import org.example.bookstore.dto.orderitem.OrderItemResponseDto;
import org.springframework.data.domain.Pageable;

public interface OrderService {
    OrderResponseDto createOrder(CreateOrderRequestDto requestDto);

    Set<OrderResponseDto> getOrders(Pageable pageable);

    OrderResponseDto updateOrderStatus(Long id, UpdateOrderRequestDto requestDto);

    Set<OrderItemResponseDto> getOrdersItems(Long orderId);

    OrderItemResponseDto getOrdersItem(Long orderId, Long itemId);
}
