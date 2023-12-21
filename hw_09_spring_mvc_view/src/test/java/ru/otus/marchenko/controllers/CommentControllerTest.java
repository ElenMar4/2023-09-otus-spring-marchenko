package ru.otus.marchenko.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.services.CommentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentController.class)
class CommentControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private CommentService commentService;

    @Test
    @DisplayName("Should return correct all comments by book")
    void shouldReturnCorrectAllCommentByBookIdInPath() throws Exception {
        List<CommentDto> commentDtoList = List.of(
                new CommentDto(1L, "first", 1L),
                new CommentDto(2L, "second", 1L)
        );
        given(commentService.findAllByBookId(1L)).willReturn(commentDtoList);

        mvc.perform(get("/comment/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/list"))
                .andExpect(model().attribute("comments", commentDtoList));
    }

    @Test
    @DisplayName("Should return view comment/add")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/comment/add/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/add"));
    }

    @Test
    @DisplayName("Should create comment and return redirect")
    public void shouldCreateCommentAndReturnRedirect() throws Exception {
        mvc.perform(post("/comment/add/1")
                        .param("message", "message")
                        .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comment/{bookId}"));

        verify(commentService).insert(eq(new CommentCreateDto("message", 1L)));
    }

    @Test
    @DisplayName("Should delete comment and return redirect")
    public void shouldDeleteCommentAndReturnRedirect() throws Exception {
        mvc.perform(get("/comment/delete/{id}", "1")
                        .param("bookId", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/comment/{bookId}"))
                .andExpect(model().attributeExists("bookId"));

        verify(commentService).deleteById(eq(1L));
    }

}