package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.cartitem.CartItemRequestDto;
import org.example.bookstore.dto.shoppingcart.ShoppingCartResponseDto;
import org.example.bookstore.dto.shoppingcart.UpdateShoppingCartRequestDto;
import org.example.bookstore.service.ShoppingCartService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Shopping cart management", description = "Endpoints for managing shopping cart")
@RequiredArgsConstructor
@RestController
@RequestMapping("/cart")
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @Operation(summary = "Get user's cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    public ShoppingCartResponseDto getShoppingCart() {
        return shoppingCartService.getShoppingCart();
    }

    @Operation(summary = "Add a book to the cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    public void addBookToShoppingCart(@RequestBody @Valid CartItemRequestDto requestDto) {
        shoppingCartService.addBookToShoppingCart(requestDto);
    }

    @Operation(summary = "Update the book's quantity")
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{id}")
    public void updateQuantity(
            @PathVariable Long id,
            @RequestBody @Valid UpdateShoppingCartRequestDto requestDto) {
        shoppingCartService.updateQuantity(id, requestDto);
    }

    @Operation(summary = "Delete a book from the cart")
    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Long id) {
        shoppingCartService.deleteBook(id);
    }
}
