package org.example.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.BookDtoWithoutCategory;
import org.example.bookstore.dto.book.BookSearchParameters;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.BookMapper;
import org.example.bookstore.model.Book;
import org.example.bookstore.repository.book.BookRepository;
import org.example.bookstore.repository.book.BookSpecificationBuilder;
import org.example.bookstore.service.BookService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;
    private final BookSpecificationBuilder specificationBuilder;

    @Override
    public BookDto save(CreateBookRequestDto requestDto) {
        Book book = bookMapper.toModel(requestDto);
        bookRepository.save(book);
        BookDto booksDto = bookMapper.toDto(book);
        booksDto.setCategoryIds(requestDto.getCategoryIds());
        return booksDto;
    }

    @Override
    public List<BookDtoWithoutCategory> findAll(Pageable pageable) {
        return bookMapper.toDtoListWithoutCategory(bookRepository.findAll(pageable));
    }

    @Override
    public BookDtoWithoutCategory getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id: " + id + " not found"));
        return bookMapper.toDtoWithoutCategory(book);
    }

    @Override
    public List<BookDtoWithoutCategory> findAllByCategoryId(Long id, Pageable pageable) {
        Page<Book> books = bookRepository.findAllByCategoryId(id, pageable);
        return bookMapper.toDtoListWithoutCategory(books);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }

    @Override
    public List<BookDtoWithoutCategory> searchBooks(BookSearchParameters searchParameters,
                                                    Pageable pageable) {
        Specification<Book> specification = specificationBuilder.build(searchParameters);
        return bookMapper.toDtoListWithoutCategory(bookRepository.findAll(specification, pageable));
    }

    @Override
    public void updateById(Long id, CreateBookRequestDto requestDto) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Book with id: " + id + " not found"));
        book.setTitle(requestDto.getTitle());
        book.setAuthor(requestDto.getAuthor());
        book.setIsbn(requestDto.getIsbn());
        book.setPrice(requestDto.getPrice());
        book.setDescription(requestDto.getDescription());
        book.setCoverImage(requestDto.getCoverImage());
        book.setCategories(book.getCategories());
        bookRepository.save(book);
    }
}
