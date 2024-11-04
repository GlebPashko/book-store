package org.example.bookstore.util;

import java.math.BigDecimal;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.example.bookstore.dto.category.CategoryDto;
import org.example.bookstore.dto.category.CreateCategoryRequestDto;
import org.example.bookstore.model.Book;
import org.example.bookstore.model.Category;

public class TestUtil {
    public static Book getBook() {
        Long bookId = 1L;
        Book book = new Book();
        book.setId(bookId);
        book.setTitle("Title");
        book.setAuthor("Author");
        book.setIsbn("1234567890");
        book.setDescription("Description");
        book.setPrice(BigDecimal.TEN);
        return book;
    }

    public static BookDto getBookDto() {
        Long bookId = 1L;
        BookDto bookDto = new BookDto();
        bookDto.setId(bookId);
        bookDto.setTitle("Frankenstein");
        bookDto.setAuthor("Mary Shelley");
        bookDto.setIsbn("9781122334456");
        bookDto.setDescription("Description");
        bookDto.setCoverImage("image.jpg");
        bookDto.setPrice(BigDecimal.TEN);
        return bookDto;
    }

    public static CreateBookRequestDto getBookRequestDto() {
        CreateBookRequestDto requestDto = new CreateBookRequestDto();
        requestDto.setTitle("Frankenstein");
        requestDto.setAuthor("Mary Shelley");
        requestDto.setIsbn("9781122334456");
        requestDto.setDescription("Description");
        requestDto.setCoverImage("image.jpg");
        requestDto.setPrice(BigDecimal.TEN);
        return requestDto;
    }

    public static Category getCategory() {
        Category category = new Category();
        category.setId(1L);
        category.setName("Horror");
        category.setDescription("Horror type");
        return category;
    }

    public static CategoryDto getCategoryDto() {
        CategoryDto dto = new CategoryDto();
        dto.setId(1L);
        dto.setName("Horror");
        dto.setDescription("Horror type");
        return dto;
    }

    public static CreateCategoryRequestDto getCategoryRequestDto() {
        CreateCategoryRequestDto requestDto = new CreateCategoryRequestDto();
        requestDto.setName("Horror");
        requestDto.setDescription("Horror type");
        return requestDto;
    }
}
