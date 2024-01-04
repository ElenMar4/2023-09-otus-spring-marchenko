package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentPageController.class)
class CommentPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should return correct all comments by book")
    void shouldReturnCorrectAllCommentByBookIdInPath() throws Exception {
        mvc.perform(get("/comment/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/list"))
                .andExpect(model().attribute("bookId", 1L));
    }

    @Test
    @DisplayName("Should return view comment/add")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/comment/add/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/add"))
                .andExpect(model().attribute("bookId", 1L));
    }
}