package ru.otus.marchenko.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.services.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BookController.class)
class BookControllerTest {

    private static final List<AuthorDto> AUTHOR_EXPECT = List.of(
            new AuthorDto(1L, "J. W. Goethe"),
            new AuthorDto(2L, "F. Dostoevsky"));
    private static final List<GenreDto> GENRE_EXPECT = List.of(
            new GenreDto(1L, "Tragedy"),
            new GenreDto(2L, "Novel"));

    private static final List<BookDto> BOOK_EXPECT = List.of(
            new BookDto(1L, "Faust", AUTHOR_EXPECT.get(0), GENRE_EXPECT.get(0)),
            new BookDto(2L, "The Gambler", AUTHOR_EXPECT.get(1), GENRE_EXPECT.get(1)));
    private static final long ID = 1L;
    private static final BookUpdateDto BOOK_UPDATE_EXPECT = new BookUpdateDto(1L, "Faust", 1L, 1L);
    private static final BookCreateDto BOOK_CREATE_EXPECT = new BookCreateDto("Faust", 1L, 1L);

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private BookService bookService;

    @Test
    @DisplayName("Should correct return list books")
    void shouldReturnCorrectBookList() throws Exception {
        given(bookService.findAll()).willReturn(BOOK_EXPECT);
        mvc.perform(get("/api/v1/book"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BOOK_EXPECT)));
    }

    @Test
    @DisplayName("Should correct return book by id")
    void shouldReturnCorrectBookById() throws Exception {
        given(bookService.findById(ID)).willReturn(BOOK_EXPECT.get(0));
        mvc.perform(get("/api/v1/book/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BOOK_EXPECT.get(0))));
    }

    @Test
    @DisplayName("Should correct delete book by id")
    void shouldCorrectDeleteBookById() throws Exception {
        mvc.perform(delete("/api/v1/book/{id}", ID))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should correct update book")
    void shouldUpdateBook() throws Exception {
        given(bookService.update(any(BookUpdateDto.class))).willReturn(BOOK_EXPECT.get(0));
        mvc.perform(put("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(BOOK_UPDATE_EXPECT)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Should correct create new book")
    void shouldCreateNewBook() throws Exception {
        given(bookService.create(any())).willReturn(BOOK_EXPECT.get(0));
        mvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(BOOK_CREATE_EXPECT)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(BOOK_EXPECT.get(0))));
    }

}