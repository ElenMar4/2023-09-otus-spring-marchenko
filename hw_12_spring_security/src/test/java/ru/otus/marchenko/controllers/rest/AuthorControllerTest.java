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
import ru.otus.marchenko.repositories.UserRepository;
import ru.otus.marchenko.security.SecurityConfiguration;
import ru.otus.marchenko.services.AuthorService;
import ru.otus.marchenko.services.UserServiceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthorController.class)
@WithMockUser("User_1")
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class AuthorControllerTest {

    private static final List<AuthorDto> AUTHOR_EXPECT = List.of(
            new AuthorDto(1L, "J. W. Goethe"),
            new AuthorDto(2L, "F. Dostoevsky"));

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private AuthorService authorService;

    @Test
    @DisplayName("Should correct return list authors")
    public void shouldReturnCorrectAuthorList() throws Exception {
        given(authorService.findAll()).willReturn(AUTHOR_EXPECT);
        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(AUTHOR_EXPECT)));
    }

    @Test
    @DisplayName("Should correct create new author")
    public void shouldCorrectCreateAuthor() throws Exception {
        given(authorService.create(any())).willReturn(AUTHOR_EXPECT.get(0));
        mvc.perform(post("/api/v1/author")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(AUTHOR_EXPECT.get(0))))
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(AUTHOR_EXPECT.get(0))));
    }

    @Test
    @WithAnonymousUser
    @DisplayName("Should fail when the user is anonymous")
    public void shouldFailWhenUserIsNotAuthorized() throws Exception{
        mvc.perform(get("/api/v1/author"))
                .andExpect(status().isUnauthorized());
    }
}