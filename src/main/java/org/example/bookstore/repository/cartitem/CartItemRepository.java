package org.example.bookstore.repository.cartitem;

import java.util.Set;
import org.example.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findByShoppingCartId(Long shoppingCartId);
}