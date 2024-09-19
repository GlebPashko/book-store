package org.example.bookstore.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.BookSearchParameters;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.example.bookstore.service.BookService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book management", description = "Endpoints for managing books")
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {
    private final BookService bookService;

    @Operation(summary = "Create a new book")
    @ApiResponse(responseCode = "200", description = "Book created successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid input data.")
    @PostMapping
    public BookDto createBook(@RequestBody @Valid CreateBookRequestDto requestDto) {
        return bookService.save(requestDto);
    }

    @Operation(summary = "Find all books")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully.")
    @GetMapping
    public List<BookDto> getAll(Pageable pageable) {
        return bookService.findAll(pageable);
    }

    @Operation(summary = "Find a book by id")
    @ApiResponse(responseCode = "200", description = "Book retrieved successfully.")
    @ApiResponse(responseCode = "404", description = "Book not found.")
    @GetMapping("/{id}")
    public BookDto getBookById(@PathVariable Long id) {
        return bookService.getBookById(id);
    }

    @Operation(summary = "Update a book by id")
    @ApiResponse(responseCode = "204", description = "Book updated successfully.")
    @ApiResponse(responseCode = "400", description = "Invalid input data.")
    @ApiResponse(responseCode = "404", description = "Book not found.")
    @PutMapping("/{id}")
    public void updateBookById(@PathVariable Long id,
                               @RequestBody @Valid CreateBookRequestDto requestDto) {
        bookService.updateById(id, requestDto);
    }

    @Operation(summary = "Search for books by parameters")
    @ApiResponse(responseCode = "200", description = "Books retrieved successfully.")
    @GetMapping("/search")
    public List<BookDto> searchBooks(BookSearchParameters searchParameters, Pageable pageable) {
        return bookService.searchBooks(searchParameters, pageable);
    }

    @Operation(summary = "Delete a book by id", description = "Mark the book "
            + "field 'is_deleted' = true")
    @ApiResponse(responseCode = "204", description = "Book deleted successfully.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable Long id) {
        bookService.deleteById(id);
    }
}
