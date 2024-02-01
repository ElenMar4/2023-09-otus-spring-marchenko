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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(AuthorPageController.class)
@WithMockUser("User_1")
@Import({SecurityConfiguration.class, UserServiceImpl.class})
@MockBean(UserRepository.class)
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