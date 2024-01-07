package ru.otus.marchenko.controllers.page;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookPageController.class)
class BookPageControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("Should return model books and list view")
    public void shouldReturnBooksAndListView() throws Exception {
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"));
    }

    @Test
    @DisplayName("Should return add view")
    public void shouldReturnAddView() throws Exception {
        mvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/add"));
    }

    @Test
    @DisplayName("Should return model book and view book/edit")
    public void shouldReturnBookAndEditView() throws Exception {
        mvc.perform(get("/book/edit/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("id", 1L));
    }

    @Test
    @DisplayName("Should return model book and view book/details")
    public void shouldReturnBookAndDetailsView() throws Exception {
        mvc.perform(get("/book/details/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/details"))
                .andExpect(model().attribute("id", 1L));
    }
}