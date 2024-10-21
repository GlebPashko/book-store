package org.example.bookstore.repository.orderitem;

import java.util.Set;
import org.example.bookstore.model.OrderItem;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @EntityGraph(attributePaths = {"order", "book"})
      Set<OrderItem> findByOrderId(Long id);

    @Query("SELECT o FROM OrderItem o JOIN o.order or JOIN o.book "
              + "where o.id = :orderItemId AND or.id = :orderId")
      OrderItem findByOrderIdAndOrderItemId(Long orderId, Long orderItemId);
}
