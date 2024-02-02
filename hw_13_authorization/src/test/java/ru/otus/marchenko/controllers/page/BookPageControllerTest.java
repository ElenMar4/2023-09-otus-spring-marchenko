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

@WebMvcTest(BookPageController.class)
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return model books and list view for admin")
    public void shouldReturnBooksAndListViewForAdmin() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return model books and list view for user")
    public void shouldReturnBooksAndListViewForUser() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"));
    }


    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return add view for admin")
    public void shouldReturnAddViewForAdmin() throws Exception {
        mvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/add"));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return error when user tries to go to the page of creating a new book")
    public void ShouldReturnErrorWhenUserTriesToGoToPageOfCreatingNewBook() throws Exception {
        mvc.perform(get("/book/add"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return model book and view book/edit for admin")
    public void shouldReturnBookAndEditViewForAdmin() throws Exception {
        mvc.perform(get("/book/edit/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("id", 1L));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return error when user tries to go to the page of editing the book")
    public void ShouldReturnErrorWhenUserTriesToGoToPageOfEditingBook() throws Exception {
        mvc.perform(get("/book/edit/{id}", "1"))
                .andExpect(status().is4xxClientError());
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {"ROLE_ADMIN"}
    )
    @DisplayName("Should return model book and view book/details for admin")
    public void shouldReturnBookAndDetailsViewForAdmin() throws Exception {
        mvc.perform(get("/book/details/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/details"))
                .andExpect(model().attribute("id", 1L));
    }

    @Test
    @WithMockUser(
            username = "user",
            authorities = {"ROLE_USER"}
    )
    @DisplayName("Should return model book and view book/details for user")
    public void shouldReturnBookAndDetailsViewForUser() throws Exception {
        mvc.perform(get("/book/details/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/details"))
                .andExpect(model().attribute("id", 1L));
    }

    @Test
    @WithMockUser(
            username = "admin",
            authorities = {}
    )
    @DisplayName("Should successful logout")
    public void shouldSuccessfulLogout() throws Exception {
        mvc.perform(get("/logout"))
                .andExpect(status().is2xxSuccessful());
    }
}