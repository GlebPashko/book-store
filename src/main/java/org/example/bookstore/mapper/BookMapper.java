package org.example.bookstore.mapper;

import java.util.List;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.BookDto;
import org.example.bookstore.dto.CreateBookRequestDto;
import org.example.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class)
public interface BookMapper {
    BookDto toDto(Book employee);

    Book toModel(CreateBookRequestDto requestDto);

    List<BookDto> toDtoList(Page<Book> books);
}
