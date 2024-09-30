package org.example.bookstore.service;

import java.util.List;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.BookDtoWithoutCategory;
import org.example.bookstore.dto.book.BookSearchParameters;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.springframework.data.domain.Pageable;

public interface BookService {
    BookDto save(CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategory> findAll(Pageable pageable);

    BookDtoWithoutCategory getBookById(Long id);

    List<BookDtoWithoutCategory> findAllByCategoryId(Long id, Pageable pageable);

    void deleteById(Long id);

    List<BookDtoWithoutCategory> searchBooks(BookSearchParameters searchParameters,
                                             Pageable pageable);

    void updateById(Long id, CreateBookRequestDto requestDto);
}
