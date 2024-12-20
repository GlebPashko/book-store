package org.example.bookstore.repository.book;

import static org.junit.Assert.assertEquals;

import org.example.bookstore.model.Book;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlMergeMode;

@Sql(scripts = {
        "classpath:database/remove-all-from-books-categories-table.sql",
        "classpath:database/remove-all-from-categories-table.sql",
        "classpath:database/remove-all-books-from-table.sql"
}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SqlMergeMode(SqlMergeMode.MergeMode.MERGE)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookRepositoryTest {
    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("Verify the correct book returned when book exists")
    @Sql(scripts = "classpath:database/add-book-to-books-table.sql")
    void findAll_WithCorrectData_ReturnBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> result = bookRepository.findAll(pageable);

        assertEquals(result.getTotalElements(), 1);
    }

    @Test
    @DisplayName("Verify the correct book returned when book and category exists")
    @Sql(scripts = {
            "classpath:database/add-book-to-books-table.sql",
            "classpath:database/add-category-to-categories-table.sql",
            "classpath:database/add-category-to-book.sql"
    })
    void findAllByCategoryId_WithCorrectData_ReturnBooks() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> result = bookRepository.findAllByCategoryId(1L, pageable);

        assertEquals(result.getTotalElements(), 1);
    }

    @Test
    @DisplayName("Verify the empty list returned when book and category dont exists")
    void findAllByCategoryId_WithoutData_ReturnEmptyList() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Book> result = bookRepository.findAllByCategoryId(1L, pageable);

        assertEquals(result.getTotalElements(), 0);
    }
}
