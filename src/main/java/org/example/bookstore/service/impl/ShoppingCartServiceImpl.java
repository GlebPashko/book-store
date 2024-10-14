package org.example.bookstore.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.cartitem.CartItemRequestDto;
import org.example.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import org.example.bookstore.dto.shoppingcart.UpdateShoppingCartRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.CartItemMapper;
import org.example.bookstore.mapper.ShoppingCartMapper;
import org.example.bookstore.model.Book;
import org.example.bookstore.model.CartItem;
import org.example.bookstore.model.ShoppingCart;
import org.example.bookstore.model.User;
import org.example.bookstore.repository.book.BookRepository;
import org.example.bookstore.repository.cartitem.CartItemRepository;
import org.example.bookstore.repository.shoppingcart.ShoppingCartRepository;
import org.example.bookstore.service.ShoppingCartService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemMapper cartItemMapper;

    @Override
    public ShoppingCartResponseDto getShoppingCart() {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartResponseDto addBookToShoppingCart(CartItemRequestDto requestDto) {
        Book book = bookRepository.findById(requestDto.getBookId())
                .orElseThrow(() -> new EntityNotFoundException("Book with id "
                        + requestDto.getBookId() + " not found"));
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        CartItem cartItem = cartItemRepository.findByShoppingCartId(shoppingCart.getId())
                .stream()
                .filter(item -> item.getBook().getId().equals(requestDto.getBookId()))
                .findFirst()
                .orElseGet(() -> {
                    CartItem newCartItem = cartItemMapper.toModel(requestDto);
                    newCartItem.setShoppingCart(shoppingCart);
                    newCartItem.setBook(book);
                    return newCartItem;
                });
        if (cartItem.getId() != null) {
            cartItem.setQuantity(cartItem.getQuantity() + requestDto.getQuantity());
        }

        cartItemRepository.save(cartItem);
        shoppingCart.getCartItems().add(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    public ShoppingCartResponseDto updateQuantity(
            Long id, UpdateShoppingCartRequestDto requestDto) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());

        CartItem cartItem = cartItemRepository.findByIdAndShoppingCartId(id, shoppingCart.getId())
                .orElseThrow(() -> new EntityNotFoundException("Item not found"));
        cartItem.setQuantity(requestDto.getQuantity());
        cartItemRepository.save(cartItem);

        return shoppingCartMapper.toShoppingCartResponseDto(shoppingCart);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        ShoppingCart shoppingCart = shoppingCartRepository.findByUserId(getAuthenticatedUser());
        cartItemRepository.deleteByIdAndShoppingCartId(id, shoppingCart.getId());
    }

    @Override
    public void addShoppingCartToUser(User user) {
        ShoppingCart shoppingCart = new ShoppingCart();
        shoppingCart.setUser(user);
        shoppingCartRepository.save(shoppingCart);
    }

    private Long getAuthenticatedUser() {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return user.getId();
    }
}
