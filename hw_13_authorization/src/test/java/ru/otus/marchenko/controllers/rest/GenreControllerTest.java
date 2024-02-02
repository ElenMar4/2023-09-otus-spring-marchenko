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
import ru.otus.marchenko.models.dto.genre.GenreDto;
import ru.otus.marchenko.repositories.UserRepository;
import ru.otus.marchenko.security.SecurityConfiguration;
import ru.otus.marchenko.services.GenreService;
import ru.otus.marchenko.services.UserServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GenreController.class)
@WithMockUser(
        username = "admin",
        authorities = {"ROLE_ADMIN"}
)
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class GenreControllerTest {

    private static final List<GenreDto> GENRE_EXPECT = List.of(
            new GenreDto(1L, "Tragedy"),
            new GenreDto(2L, "Novel"));

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private GenreService genreService;

    @Test
    @DisplayName("Should correct return list genres")
    void shouldReturnCorrectGenreList() throws Exception {
        given(genreService.findAll()).willReturn(GENRE_EXPECT);
        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_EXPECT)));
    }

    @Test
    @DisplayName("Should correct create new genre")
    public void shouldCorrectCreateGenre() throws Exception {
        given(genreService.create(any())).willReturn(GENRE_EXPECT.get(0));
        mvc.perform(post("/api/v1/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(GENRE_EXPECT.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(GENRE_EXPECT.get(0))));
    }


    @Test
    @WithAnonymousUser
    @DisplayName("Should fail when the user is anonymous")
    public void shouldFailWhenUserIsNotAuthorized() throws Exception{
        mvc.perform(get("/api/v1/genre"))
                .andExpect(status().isUnauthorized());
    }
}