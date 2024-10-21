package org.example.bookstore.service.impl;

import jakarta.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.order.CreateOrderRequestDto;
import org.example.bookstore.dto.order.OrderResponseDto;
import org.example.bookstore.dto.order.UpdateOrderRequestDto;
import org.example.bookstore.dto.orderitem.OrderItemResponseDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.OrderItemMapper;
import org.example.bookstore.mapper.OrderMapper;
import org.example.bookstore.model.Order;
import org.example.bookstore.model.OrderItem;
import org.example.bookstore.model.ShoppingCart;
import org.example.bookstore.model.User;
import org.example.bookstore.repository.order.OrderRepository;
import org.example.bookstore.repository.orderitem.OrderItemRepository;
import org.example.bookstore.repository.shoppingcart.ShoppingCartRepository;
import org.example.bookstore.service.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;

    @Override
    @Transactional
    public OrderResponseDto createOrder(CreateOrderRequestDto requestDto) {
        User user = getAuthenticatedUser();
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());

        Order order = new Order();
        order.setUser(user);
        order.setStatus(Order.Status.PROCESSING);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(requestDto.getShippingAddress());

        Set<OrderItem> orderItems = orderItemMapper.toOrderItemSet(shoppingCart.getCartItems());
        orderItems.forEach(orderItem -> {
                    if (orderItem.getPrice() == null || orderItem.getQuantity() < 1) {
                        throw new IllegalArgumentException("OrderItem with id "
                                + orderItem.getId() + " has a illegal price or quantity.");
                    }
                    orderItem.setOrder(order);
                    orderItem.setPrice(orderItem.getPrice()
                            .multiply(new BigDecimal(orderItem.getQuantity())));
                }
        );

        order.setOrderItems(orderItems);
        order.setTotal(
                orderItems.stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
        );
        shoppingCart.getCartItems().clear();
        orderRepository.save(order);

        return orderMapper.orderResponseDto(order);
    }

    @Override
    public Set<OrderResponseDto> getOrders(Pageable pageable) {
        User user = getAuthenticatedUser();
        Page<Order> order = orderRepository.findByUserId(user.getId(), pageable);
        orderMapper.orderResponseDtoSet(order);

        return orderMapper.orderResponseDtoSet(order);
    }

    @Override
    public OrderResponseDto updateOrderStatus(Long id, UpdateOrderRequestDto requestDto) {
        Order order = orderRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException("Order not found"));
        order.setStatus(Order.Status.valueOf(requestDto.getStatus()));

        return orderMapper.orderResponseDto(orderRepository.save(order));
    }

    @Override
    public Set<OrderItemResponseDto> getOrdersItems(Long orderId) {
        Set<OrderItem> orders = orderItemRepository.findByOrderId(orderId);
        return orderItemMapper.toOrderItemResponseDtoSet(orders);
    }

    @Override
    public OrderItemResponseDto getOrdersItem(Long orderId, Long itemId) {
        OrderItem orderItem = orderItemRepository.findByOrderIdAndOrderItemId(orderId, itemId);
        return orderItemMapper.toOrderItemResponseDto(orderItem);
    }

    private User getAuthenticatedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user;
    }
}
