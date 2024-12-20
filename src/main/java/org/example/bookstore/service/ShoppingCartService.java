package org.example.bookstore.service;

import org.example.bookstore.dto.cartitem.CartItemRequestDto;
import org.example.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import org.example.bookstore.dto.shoppingcart.UpdateShoppingCartRequestDto;
import org.example.bookstore.model.User;

public interface ShoppingCartService {
    ShoppingCartResponseDto getShoppingCart();

    ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto);

    ShoppingCartResponseDto updateQuantity(Long id, UpdateShoppingCartRequestDto requestDto);

    void deleteBook(Long id);

    void addShoppingCartToUser(User user);
}
