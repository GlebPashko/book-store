package org.example.bookstore.mapper;

import java.util.List;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.dto.book.BookDtoWithoutCategory;
import org.example.bookstore.dto.book.CreateBookRequestDto;
import org.example.bookstore.model.Book;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

@Mapper(config = MapperConfig.class, uses = CategoryMapper.class)
public interface BookMapper {
    BookDtoWithoutCategory toDtoWithoutCategory(Book book);

    BookDto toDto(Book book);

    @Mapping(target = "categories", source = "categoryIds", qualifiedByName = "categoryById")
    Book toModel(CreateBookRequestDto requestDto);

    List<BookDtoWithoutCategory> toDtoListWithoutCategory(Page<Book> books);
}
