package org.example.bookstore.mapper;

import java.util.List;
import org.example.bookstore.config.MapperConfig;
import org.example.bookstore.dto.category.CategoryDto;
import org.example.bookstore.dto.category.CreateCategoryRequestDto;
import org.example.bookstore.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CategoryMapper {
    CategoryDto toDto(Category category);

    List<CategoryDto> toDtoList(List<Category> category);

    Category toEntity(CreateCategoryRequestDto requestDto);

    Category updateCategoryFromDto(CreateCategoryRequestDto requestDto,
                                   @MappingTarget Category category);

    @Named("categoryById")
    default Category categoryById(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }

    @Named("idsByCategory")
    default Long categoryIds(Category category) {
        if (category == null) {
            return null;
        }
        Long id = category.getId();
        return id;
    }
}
