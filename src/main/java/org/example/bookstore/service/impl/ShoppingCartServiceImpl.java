package org.example.bookstore.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.cartitem.CartItemRequestDto;
import org.example.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import org.example.bookstore.dto.shoppingcart.UpdateShoppingCartRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.CartItemMapper;
import org.example.bookstore.mapper.ShoppingCartMapper;
import org.example.bookstore.model.CartItem;
import org.example.bookstore.model.ShoppingCart;
import org.example.bookstore.model.User;
import org.example.bookstore.repository.cartitem.CartItemRepository;
import org.example.bookstore.repository.shoppingcart.ShoppingCartRepository;
import org.example.bookstore.repository.user.UserRepository;
import org.example.bookstore.service.ShoppingCartService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());
        shoppingCart.setCartItems(cartItemRepository.findByShoppingCartId(shoppingCart.getId()));

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public void addBookToShoppingCart(CartItemRequestDto requestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(user.getId());

        CartItem cartItem = cartItemMapper.toModel(requestDto);
        cartItem.setShoppingCart(shoppingCart);
        cartItemRepository.save(cartItem);
    }

    @Override
    public void updateQuantity(Long id, UpdateShoppingCartRequestDto requestDto) {
        CartItem cartItem = cartItemRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Item with id + " + id + " not found"));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);
    }

    @Override
    public void deleteBook(Long id) {
        cartItemRepository.deleteById(id);
    }

    @Override
    public void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }
}
