package org.example.bookstore.service.impl;

import static org.example.bookstore.util.TestUtil.getCategory;
import static org.example.bookstore.util.TestUtil.getCategoryDto;
import static org.example.bookstore.util.TestUtil.getCategoryRequestDto;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import org.example.bookstore.dto.category.CategoryDto;
import org.example.bookstore.dto.category.CreateCategoryRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.CategoryMapper;
import org.example.bookstore.model.Category;
import org.example.bookstore.repository.category.CategoryRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
    @Mock
    private CategoryRepository categoryRepository;
    @Mock
    private CategoryMapper categoryMapper;
    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Test
    @DisplayName("Verify the correct saved CategoryDto is returned when requestDto is valid")
    void save_WithValidRequestDto_ShouldValidCategoryDto() {
        Category category = getCategory();
        CreateCategoryRequestDto requestDto = getCategoryRequestDto();
        CategoryDto expected = getCategoryDto();

        when(categoryMapper.toEntity(requestDto)).thenReturn(category);
        when(categoryRepository.save(category)).thenReturn(category);
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.save(requestDto);

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).save(category);
    }

    @Test
    @DisplayName("Verify findAll() returns correct list of CategoryDto")
    public void findAll_ShouldReturnListOfCategoryDto() {
        List<Category> categories = List.of(getCategory());
        List<CategoryDto> expected = List.of(getCategoryDto());

        when(categoryRepository.findAll()).thenReturn(categories);
        when(categoryMapper.toDtoList(categories)).thenReturn(expected);

        List<CategoryDto> actual = categoryService.findAll();

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Verify the correct CategoryDto is returned when ID is valid")
    public void getById_WithValidId_ShouldReturnCategoryDto() {
        Long id = 1L;
        Category category = getCategory();
        CategoryDto expected = getCategoryDto();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(category));
        when(categoryMapper.toDto(category)).thenReturn(expected);

        CategoryDto actual = categoryService.getById(id);

        assertEquals(expected, actual);
        verify(categoryRepository, times(1)).findById(id);
    }

    @Test
    @DisplayName("Verify EntityNotFoundException is thrown when ID is invalid")
    public void getById_WithInvalidId_ShouldThrowEntityNotFoundException() {
        Long invalidId = 99L;

        when(categoryRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> categoryService.getById(invalidId));
        verify(categoryRepository, times(1)).findById(invalidId);
    }
}
