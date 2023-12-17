package ru.otus.marchenko.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.marchenko.converters.BookConverter;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.services.BookService;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class BookCommands {

    private final BookService bookService;

    private final BookConverter bookConverter;


    @ShellMethod(value = "Find all books", key = "ab")
    public String findAllBooks() {
        return bookService.findAll().stream()
                .map(bookConverter::bookToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find book by id", key = "bbid")
    public String findBookById(String id) {
        return bookService.findById(id)
                .map(bookConverter::bookToString)
                .orElse("Book with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Find book with comments by id", key = "bc")
    public String findBookWithCommentsByBookId(String id) {
        List<Comment> comments = bookService.findCommentsByBook(id);
        Optional<Book> book = bookService.findById(id);
        return bookConverter.bookWithCommentsToString(book.get(), comments);
    }

    @ShellMethod(value = "Insert book", key = "bins")
    public String insertBook(String title, String authorId, Set<String> genresIds) {
        var savedBook = bookService.insert(title, authorId, genresIds);
        return String.format("New book save into base: %s", bookConverter.bookToString(savedBook));
    }

    @ShellMethod(value = "Update book", key = "bupd")
    public String updateBook(String id, String title, String authorId, Set<String> genresIds) {
        var savedBook = bookService.update(id, title, authorId, genresIds);
        return String.format("Book update into base: %s", bookConverter.bookToString(savedBook));
    }

    @ShellMethod(value = "Delete book by id", key = "bdel")
    public String deleteBook(String id) {
        bookService.deleteById(id);
        return "Book delete";
    }
}