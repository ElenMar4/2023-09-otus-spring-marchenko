package ru.otus.marchenko.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.services.GenreService;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(GenreController.class)
class GenreControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Should return view genre/add")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/genre/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("genre/add"));
    }

    @Test
    @DisplayName("Should create genre and return redirect")
    public void shouldCreateGenreAndReturnRedirect() throws Exception {
        mvc.perform(post("/genre/add")
                        .param("name", "Classic"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:../book/add"));

        verify(genreService).create(eq(new GenreCreateDto("Classic")));
    }
}