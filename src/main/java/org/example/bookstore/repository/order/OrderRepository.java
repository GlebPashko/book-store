package org.example.bookstore.repository.order;

import org.example.bookstore.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface OrderRepository extends JpaRepository<Order, Long>,
        JpaSpecificationExecutor<Order> {
    @EntityGraph(attributePaths = {"orderItems", "orderItems.book"})
    Page<Order> findByUserId(Long id, Pageable pageable);
}
