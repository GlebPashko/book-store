package org.example.bookstore.repository.cartitem;

import java.util.Optional;
import java.util.Set;
import org.example.bookstore.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Set<CartItem> findByShoppingCartId(Long shoppingCartId);

    Optional<CartItem> findByIdAndShoppingCartId(Long itemId, Long cartId);

    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.id = :id AND c.shoppingCart.id = :shoppingCartId")
    void deleteByIdAndShoppingCartId(Long id, Long shoppingCartId);
}
