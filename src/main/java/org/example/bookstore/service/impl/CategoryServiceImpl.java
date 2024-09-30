package org.example.bookstore.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.bookstore.dto.category.CategoryDto;
import org.example.bookstore.dto.category.CreateCategoryRequestDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.mapper.CategoryMapper;
import org.example.bookstore.model.Category;
import org.example.bookstore.repository.category.CategoryRepository;
import org.example.bookstore.service.CategoryService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public CategoryDto save(CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.save(categoryMapper.toEntity(requestDto));
        return categoryMapper.toDto(category);
    }

    @Override
    public List<CategoryDto> findAll() {
        return categoryMapper.toDtoList(categoryRepository.findAll());
    }

    @Override
    public CategoryDto getById(Long id) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(
                "Category with id " + id + " not found"));
        return categoryMapper.toDto(category);
    }

    @Override
    public CategoryDto update(Long id, CreateCategoryRequestDto requestDto) {
        Category category = categoryRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException(
                "Category with id " + id + " not found"));
        category.setDescription(requestDto.getDescription());
        category.setName(requestDto.getName());
        categoryRepository.save(category);

        return categoryMapper.toDto(category);
    }

    @Override
    public void deleteById(Long id) {
        categoryRepository.deleteById(id);
    }
}
