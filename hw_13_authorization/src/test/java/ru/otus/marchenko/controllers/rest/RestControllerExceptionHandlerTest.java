package ru.otus.marchenko.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.models.dto.book.BookCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.mappers.AuthorMapper;
import ru.otus.marchenko.models.mappers.BookMapper;
import ru.otus.marchenko.models.mappers.CommentMapper;
import ru.otus.marchenko.models.mappers.GenreMapper;
import ru.otus.marchenko.repositories.*;
import ru.otus.marchenko.security.SecurityConfiguration;
import ru.otus.marchenko.services.*;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(classes = {RestControllerExceptionHandler.class,
        BookController.class,
        BookServiceImpl.class,
        AuthorController.class,
        AuthorServiceImpl.class,
        GenreController.class,
        GenreServiceImpl.class,
        CommentController.class,
        CommentServiceImpl.class})
@AutoConfigureWebMvc
@AutoConfigureMockMvc
@WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
)
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class RestControllerExceptionHandlerTest {

    @MockBean
    private AuthorRepository authorRepository;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private GenreRepository genreRepository;
    @MockBean
    private CommentRepository commentRepository;
    @MockBean
    private BookMapper bookMapper;
    @MockBean
    private AuthorMapper authorMapper;
    @MockBean
    private GenreMapper genreMapper;
    @MockBean
    private CommentMapper commentMapper;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    //____________________404_________________________
    @Test

    @DisplayName("Correct return httpStatus=404, when book not found")
    void shouldHandleNotFoundExceptionForBook() throws Exception {
        Mockito
                .when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(get("/api/v1/book/{id}", 123456))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Book not found"));
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when author not found by create book")
    void shouldHandleNotFoundExceptionForAuthorByCreateBook() throws Exception {
        Mockito
                .when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.empty());
        Mockito
                .when(genreRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Genre()));

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new BookCreateDto("title", 123456L, anyLong())))                       )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Author not found"));
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when genre not found by create book")
    void shouldHandleNotFoundExceptionForGenreByCreateBook() throws Exception {
        Mockito
                .when(authorRepository.findById(anyLong()))
                .thenReturn(Optional.of(new Author()));
        Mockito
                .when(genreRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new BookCreateDto("title", anyLong(), 123456L)))                       )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Genre not found"));
    }

    @Test
    @DisplayName("Correct return httpStatus=404, when book not found by create comment")
    void shouldHandleNotFoundExceptionForBookByCreateComment() throws Exception {
        Mockito
                .when(bookRepository.findById(anyLong()))
                .thenReturn(Optional.empty());

        mockMvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new CommentCreateDto("message", 123456L)))                       )
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.valueOf("text/plain;charset=UTF-8")))
                .andExpect(content().string("Book not found"));
    }

    //    ____________________500_________________________
    @Test
    @DisplayName("Correct return httpStatus=500, when server throw RuntimeException")
    void shouldHandle() throws Exception {
        Mockito
                .when(bookRepository.findAll())
                .thenThrow(new RuntimeException());
        mockMvc.perform(get("/api/v1/book"))
                .andExpect(status().is5xxServerError());
    }

    //   ____________________400________________________
    @Test
    @DisplayName("Correct return httpStatus=400, when client enter incorrect request")
    void shouldHandleRuntimeException() throws Exception{
        mockMvc.perform(post("/api/v1/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(
                                new BookCreateDto(null, null, null)))                       )
                .andExpect(status().isBadRequest());
    }
}
