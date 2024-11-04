package org.example.bookstore.controller;

import static org.example.bookstore.util.TestUtil.getCategoryDto;
import static org.example.bookstore.util.TestUtil.getCategoryRequestDto;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.example.bookstore.dto.category.CategoryDto;
import org.example.bookstore.dto.category.CreateCategoryRequestDto;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/remove-all-from-categories-table.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CategoryControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @DisplayName("Verify create method return CategoryDto")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void createCategory_ValidCreateCategoryRequestDto_Success() throws Exception {
        CreateCategoryRequestDto requestDto = getCategoryRequestDto();
        CategoryDto expected = getCategoryDto();
        String jsonRequest = objectMapper.writeValueAsString(requestDto);

        mockMvc.perform(post("/categories")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-category-to-categories-table.sql")
    @DisplayName("Verify getAll returns list of CategoryDto")
    @WithMockUser(username = "user", roles = "USER")
    public void getAll_WithUserRole_ShouldReturnCategoryDtos() throws Exception {
        CategoryDto expected = getCategoryDto();
        mockMvc.perform(get("/categories"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(expected.getId()))
                .andExpect(jsonPath("$[0].name", is(expected.getName())))
                .andExpect(jsonPath("$[0].description", is(expected.getDescription())));
    }

    @Test
    @Transactional
    @DisplayName("Verify getAll without login, should returns exception")
    public void getAll_WithoutLogin_ShouldReturnException() throws Exception {
        mockMvc.perform(get("/categories"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-category-to-categories-table.sql")
    @DisplayName("Verify getCategoryById returns CategoryDto for valid ID")
    @WithMockUser(username = "user", roles = "USER")
    public void getCategoryById_WithValidId_ShouldReturnCategoryDto() throws Exception {
        CategoryDto expected = getCategoryDto();
        Long categoryId = 1L;

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expected.getId()))
                .andExpect(jsonPath("$.name", is(expected.getName())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())));
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-category-to-categories-table.sql")
    @DisplayName("Verify deleteCategory removes category for valid ID")
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void deleteCategory_WithValidId_ShouldDeleteCategory() throws Exception {
        Long categoryId = 1L;

        mockMvc.perform(delete("/categories/{id}", categoryId))
                .andExpect(status().isOk());

        mockMvc.perform(get("/categories/{id}", categoryId))
                .andExpect(status().isNotFound());
    }
}
