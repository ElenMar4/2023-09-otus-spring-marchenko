package ru.otus.marchenko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.marchenko.dto.author.AuthorDto;
import ru.otus.marchenko.dto.book.BookDto;
import ru.otus.marchenko.dto.book.BookUpdateDto;
import ru.otus.marchenko.dto.book.BookCreateDto;
import ru.otus.marchenko.dto.genre.GenreDto;
import ru.otus.marchenko.mappers.BookMapper;
import ru.otus.marchenko.services.AuthorService;
import ru.otus.marchenko.services.BookService;
import ru.otus.marchenko.services.GenreService;
import jakarta.validation.Valid;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookMapper bookMapper;

    @GetMapping("/book")
    public String listPage(Model model) {
        List<BookDto> bookList = bookService.findAll();
        model.addAttribute("bookList", bookList);
        return "book/list";
    }

    @GetMapping("/book/add")
    public String addNewBook(BookCreateDto bookCreateDto, Model model) {
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("bookCreateDto", bookCreateDto);
        return "book/add";
    }

    @PostMapping("/book/add")
    public String saveNewBook(@Valid @ModelAttribute("bookCreateDto") BookCreateDto bookCreateDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/book/add";
        }
        bookService.create(bookCreateDto);
        return "redirect:/book";
    }

    @GetMapping("/book/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, Model model) {
        BookUpdateDto bookUpdateDto = bookMapper.toDto(bookService.findById(id));
        List<AuthorDto> authors = authorService.findAll();
        List<GenreDto> genres = genreService.findAll();
        model.addAttribute("authors", authors);
        model.addAttribute("genres", genres);
        model.addAttribute("bookUpdateDto", bookUpdateDto);
        return "book/edit";
    }

    @PostMapping("/book/edit")
    public String updateBook(@Valid @ModelAttribute("bookUpdateDto") BookUpdateDto bookUpdateDto,
                             BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/book/edit";
        }
        bookService.update(bookUpdateDto);
        return "redirect:/book";
    }

    @GetMapping("/book/details/{id}")
    public String getBookDetails(@PathVariable("id") Long id, Model model) {
        BookDto book = bookService.findById(id);
        model.addAttribute("book", book);
        return "book/details";
    }

    @GetMapping("/book/delete/{id}")
    public String deleteBook(@PathVariable("id") Long id) {
        bookService.deleteById(id);
        return "redirect:/book";
    }
}
