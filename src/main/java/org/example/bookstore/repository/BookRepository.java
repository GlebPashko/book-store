package org.example.bookstore.repository;

import java.util.List;
import java.util.Optional;
import org.example.bookstore.model.Book;

public interface BookRepository {
    Book save(Book book);

    List findAll();

    Optional<Book> getBookById(Long id);
}
