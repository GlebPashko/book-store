package org.example.bookstore.service.impl;

import static org.example.bookstore.util.TestUtil.getBook;
import static org.example.bookstore.util.TestUtil.getBookDto;
import static org.example.bookstore.util.TestUtil.getBookRequestDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.BookMapper;
import org.example.bookstore.model.Book;
import org.example.bookstore.repository.book.BookRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class BookServiceImplTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookMapper bookMapper;
    @InjectMocks
    private BookServiceImpl bookService;

    @Test
    @DisplayName("Verify the correct saved bookDto returned when requestDto correct")
    public void save_WithValidRequestDto_ShouldReturnValidBookDto() {
        Book book = getBook();
        CreateBookRequestDto requestDto = getBookRequestDto();
        BookDto expected = getBookDto();

        when(bookMapper.toModel(requestDto)).thenReturn(book);
        when(bookRepository.save(book)).thenReturn(book);
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.save(requestDto);

        assertEquals(expected, actual);
        verify(bookRepository, times(1)).save(book);
    }

    @Test
    @DisplayName("Verify the correct find all bookDto returned when we have one book")
    public void findAll_WithValidData_ShouldReturnListOfBookDto() {
        BookDto bookDto = getBookDto();
        Book book = getBook();
        Pageable pageable = Pageable.ofSize(10);
        Page<Book> bookPage = new PageImpl<>(List.of(book));

        when(bookRepository.findAll(pageable)).thenReturn(bookPage);
        when(bookMapper.toDtoList(bookPage)).thenReturn(List.of(bookDto));

        List<BookDto> expected = List.of(bookDto);
        List<BookDto> actual = bookService.findAll(pageable);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify the correct book returned when book exists")
    public void getBookById_WithValidId_ShouldReturnValidBookDto() {
        Long bookId = 1L;
        Book book = getBook();
        BookDto expected = getBookDto();

        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));
        when(bookMapper.toDto(book)).thenReturn(expected);

        BookDto actual = bookService.getBookById(bookId);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Verify the correct exception"
            + " returned when book dont exists")
    public void getBookById_WithNonExistingId_ShouldThrowException() {
        Long bookId = 999L;

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(
                EntityNotFoundException.class,
                () -> bookService.getBookById(bookId)
        );

        String expected = "Book with id: " + bookId + " not found";
        String actual = exception.getMessage();

        assertEquals(expected, actual);
    }
}
