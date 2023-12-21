package ru.otus.marchenko.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.dto.author.AuthorCreateDto;
import ru.otus.marchenko.services.AuthorService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthorController.class)
class AuthorControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Should return view authors/add")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/author/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("author/add"));
    }

    @Test
    @DisplayName("Should create author and return redirect")
    public void shouldCreateAuthorAndReturnRedirect() throws Exception {
        mvc.perform(post("/author/add")
                        .param("fullName", "Gogol"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:../book/add"));

        verify(authorService).insert(eq(new AuthorCreateDto("Gogol")));
    }
}