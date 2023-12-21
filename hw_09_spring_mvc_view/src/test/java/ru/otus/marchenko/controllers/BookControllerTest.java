package ru.otus.marchenko.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.services.AuthorService;
import ru.otus.marchenko.services.BookService;
import ru.otus.marchenko.services.GenreService;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService bookService;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    private final static List<Author> AUTHORS = List.of(new Author(1L, "J. W. Goethe"),
            new Author(2L, "F. Dostoevsky"));
    private final static List<Genre> GENRES = List.of(new Genre(1L, "Tragedy"),
            new Genre(2L, "Novel"));
    private final static List<BookDto> EXPECT_BOOK = List.of(
            new BookDto(1L, "Faust", AUTHORS.get(0), GENRES.get(0)),
            new BookDto(2L, "The Gambler", AUTHORS.get(1), GENRES.get(1)));

    @Test
    @DisplayName("Should return model books and list view")
    public void shouldReturnBooksAndListView() throws Exception {
        given(bookService.findAll()).willReturn(EXPECT_BOOK);
        mvc.perform(get("/book"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/list"))
                .andExpect(model().attribute("bookList", EXPECT_BOOK));
    }

    @Test
    @DisplayName("Should return add view")
    public void shouldReturnAddView() throws Exception {
        given(bookService.findAll()).willReturn(EXPECT_BOOK);
        given(authorService.findAll()).willReturn(AUTHORS);
        given(genreService.findAll()).willReturn(GENRES);
        mvc.perform(get("/book/add"))
                .andExpect(status().isOk())
                .andExpect(model().attribute("authors", AUTHORS.stream().map(Author::getFullName).toList()))
                .andExpect(model().attribute("genres", GENRES.stream().map(Genre::getName).toList()))
                .andExpect(view().name("book/add"));
    }

    @Test
    @DisplayName("Should create book and return redirect")
    public void shouldCreateBookAndReturnRedirect() throws Exception {
        mvc.perform(post("/book/add")
                        .param("title", "Faust")
                        .param("authorName", "J. W. Goethe")
                        .param("genreName", "Tragedy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

        verify(bookService).insert(eq(new BookCreateDto(EXPECT_BOOK.get(0).title(),
                EXPECT_BOOK.get(0).author().getFullName(),
                EXPECT_BOOK.get(0).genre().getName())));
    }

    @Test
    @DisplayName("Should return model book and view book/edit")
    public void shouldReturnBookAndEditView() throws Exception {
        given(bookService.findById(anyLong())).willReturn(EXPECT_BOOK.get(0));
        given(authorService.findAll()).willReturn(AUTHORS);
        given(genreService.findAll()).willReturn(GENRES);
        mvc.perform(get("/book/edit/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/edit"))
                .andExpect(model().attribute("book", EXPECT_BOOK.get(0)))
                .andExpect(model().attribute("authors", AUTHORS.stream().map(Author::getFullName).toList()))
                .andExpect(model().attribute("genres", GENRES.stream().map(Genre::getName).toList()));
    }

    @Test
    @DisplayName("Should update book and return redirect")
    public void shouldUpdateBookAndReturnRedirect() throws Exception {
        mvc.perform(post("/book/edit")
                        .param("id", "1")
                        .param("title", "The Faust")
                        .param("authorName", "Goethe")
                        .param("genreName", "Dramaturgy"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

        verify(bookService).update(new BookUpdateDto(1L, "The Faust", "Goethe", "Dramaturgy"));
    }

    @Test
    @DisplayName("Should return model book and view book/details")
    public void shouldReturnBookAndDetailsView() throws Exception {
        given(bookService.findById(anyLong())).willReturn(EXPECT_BOOK.get(0));
        mvc.perform(get("/book/details/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(view().name("book/details"))
                .andExpect(model().attribute("book", EXPECT_BOOK.get(0)));
    }

    @Test
    @DisplayName("Should delete book and return redirect")
    public void shouldDeleteBookAndReturnRedirect() throws Exception {
        mvc.perform(get("/book/delete/{id}", "1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/book"));

        verify(bookService).deleteById(eq(1L));
    }
}