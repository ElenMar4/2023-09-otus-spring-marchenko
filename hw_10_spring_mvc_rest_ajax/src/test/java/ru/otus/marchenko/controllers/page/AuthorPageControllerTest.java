package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthorPageController.class)
class AuthorPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should return view authors/add")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/author/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/add"));
    }
}