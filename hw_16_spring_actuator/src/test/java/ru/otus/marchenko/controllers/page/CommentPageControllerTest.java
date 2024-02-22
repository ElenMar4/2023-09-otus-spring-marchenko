package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.repositories.UserRepository;
import ru.otus.marchenko.security.SecurityConfiguration;
import ru.otus.marchenko.services.UserServiceImpl;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CommentPageController.class)
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class CommentPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return correct all comments by book for admin")
    void shouldReturnCorrectAllCommentByBookIdInPathForRoleAdmin() throws Exception {
        mvc.perform(get("/comment/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/list"))
                .andExpect(model().attribute("bookId", 1L));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return correct all comments by book for user")
    void shouldReturnCorrectAllCommentByBookIdInPathForRoleUser() throws Exception {
        mvc.perform(get("/comment/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/list"))
                .andExpect(model().attribute("bookId", 1L));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return view comment/add for admin")
    public void shouldReturnAddViewForRoleAdmin() throws Exception {
        mvc.perform(get("/comment/add/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/add"))
                .andExpect(model().attribute("bookId", 1L));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return view comment/add for user")
    public void shouldReturnAddViewForRoleUser() throws Exception {
        mvc.perform(get("/comment/add/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("comment/add"))
                .andExpect(model().attribute("bookId", 1L));
    }
}