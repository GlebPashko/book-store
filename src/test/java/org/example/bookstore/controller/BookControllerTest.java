package org.example.bookstore.controller;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import java.util.List;
import org.example.bookstore.dto.book.BookDto;
import org.example.bookstore.exception.EntityNotFoundException;
import org.example.bookstore.security.JwtUtil;
import org.example.bookstore.service.BookService;
import org.example.bookstore.util.TestUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@Sql(scripts = "classpath:database/remove-all-books-from-table.sql",
        executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookControllerTest {
    private static MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private BookService bookService;
    @MockBean
    private JwtUtil jwtUtil;

    @BeforeAll
    static void beforeAll(@Autowired WebApplicationContext applicationContext) {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(applicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    @Transactional
    @DisplayName("Verify save() method works")
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void saveBook_ValidCreateBookRequestDto_ShouldReturnBookDto() throws Exception {
        BookDto expected = TestUtil.getBookDto();
        String jsonRequest = objectMapper.writeValueAsString(TestUtil.getBookRequestDto());

        mockMvc.perform(post("/books")
                        .content(jsonRequest)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is(expected.getAuthor())))
                .andExpect(jsonPath("$.price").value(expected.getPrice()))
                .andExpect(jsonPath("$.title", is(expected.getTitle())))
                .andExpect(jsonPath("$.coverImage", is(expected.getCoverImage())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())))
                .andExpect(jsonPath("$.isbn", is(expected.getIsbn())));
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-book-to-books-table.sql")
    @DisplayName("Verify getAll() method works")
    @WithMockUser(username = "user", roles = "USER")
    public void getAll_ValidData_ShouldReturnBookDtos() throws Exception {
        when(bookService.findAll(any(Pageable.class))).thenReturn(List.of(TestUtil.getBookDto()));
        mockMvc.perform(get("/books")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1));
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-book-to-books-table.sql")
    @DisplayName("Verify getBookById() method works")
    @WithMockUser(username = "user", roles = "USER")
    public void getBookById_ValidId_ShouldReturnBookDto() throws Exception {
        long bookId = 1L;
        BookDto expected = TestUtil.getBookDto();

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$.author", is(expected.getAuthor())))
                .andExpect(jsonPath("$.price").value(expected.getPrice().doubleValue()))
                .andExpect(jsonPath("$.title", is(expected.getTitle())))
                .andExpect(jsonPath("$.coverImage", is(expected.getCoverImage())))
                .andExpect(jsonPath("$.description", is(expected.getDescription())))
                .andExpect(jsonPath("$.isbn", is(expected.getIsbn())));
    }

    @Test
    @Transactional
    @DisplayName("Verify getBookById() method works when book by id not exists")
    @WithMockUser(username = "user", roles = "USER")
    public void getBookById_DontValidId_ShouldReturnBookDto() throws Exception {
        long bookId = 100L;
        Exception exp = new EntityNotFoundException("Book with id: " + bookId + " not found");

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(exp.getMessage())));
    }

    @Test
    @Transactional
    @Sql(scripts = "classpath:database/add-book-to-books-table.sql")
    @DisplayName("Verify deleteById() method works")
    @WithMockUser(username = "admin", roles = {"ADMIN", "USER"})
    public void deleteById_ValidId_Success() throws Exception {
        long bookId = 1L;

        mockMvc.perform(delete("/books/" + bookId))
                .andExpect(status().isNoContent());

        mockMvc.perform(get("/books/" + bookId))
                .andExpect(status().isNotFound());
    }
}

