package ru.otus.marchenko.controllers.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.dto.book.BookDto;
import ru.otus.marchenko.models.dto.comment.CommentCreateDto;
import ru.otus.marchenko.models.dto.comment.CommentDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.repositories.UserRepository;
import ru.otus.marchenko.security.SecurityConfiguration;
import ru.otus.marchenko.services.CommentService;
import ru.otus.marchenko.services.UserServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@WithMockUser("User_1")
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class CommentControllerTest {
    private static final BookDto BOOK_EXPECT = new BookDto(
            1L, "Faust",
            new AuthorDto(1L, "J. W. Goethe"),
            new GenreDto(1L, "Tragedy"));
    private final static List<CommentDto> COMMENTS_LIST_EXPECTED = List.of(new CommentDto(1L, "ok", BOOK_EXPECT.id()));
    private final static CommentCreateDto COMMENTS_CREATED = new CommentCreateDto("super", BOOK_EXPECT.id());

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Should correct return list comments by book")
    void shouldReturnCorrectListCommentsByBookId() throws Exception {
        given(commentService.findAllByBookId(any(Long.class))).willReturn(COMMENTS_LIST_EXPECTED);
        mvc.perform(get("/api/v1/comment/{id}", BOOK_EXPECT.id()))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(COMMENTS_LIST_EXPECTED)));
    }

    @Test
    @DisplayName("Should correct create new comment by book")
    void shouldCreateNewCommentByBook() throws Exception {
        given(commentService.create(any(CommentCreateDto.class))).willReturn(COMMENTS_LIST_EXPECTED.get(0));
        mvc.perform(post("/api/v1/comment")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(COMMENTS_CREATED)))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(COMMENTS_LIST_EXPECTED.get(0))));
    }

    @Test
    @DisplayName("Should correct delete comment by id")
    void shouldCorrectDeleteCommentById() throws Exception {
        mvc.perform(delete("/api/v1/comment/{id}", BOOK_EXPECT.id()))
                .andExpect(status().isNoContent());
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Should fail when the user is anonymous")
    public void shouldFailWhenUserIsNotAuthorized() throws Exception{
        mvc.perform(get("/api/v1/comment"))
                .andExpect(status().isUnauthorized());
    }
}