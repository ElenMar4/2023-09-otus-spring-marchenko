package ru.otus.marchenko.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.marchenko.converters.AuthorConverter;
import ru.otus.marchenko.models.Author;
import ru.otus.marchenko.services.AuthorService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class AuthorCommands {

    private final AuthorService authorService;

    private final AuthorConverter authorConverter;

    @ShellMethod(value = "Find all authors", key = "aa")
    public String findAllAuthors() {
        return authorService.findAll().stream()
                .map(authorConverter::authorToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find author by id", key = "abid")
    public String findById (long id){
        return authorService.findBiId(id)
                .map(authorConverter::authorToString)
                .orElse("Author with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Save new author", key = "ains")
    public String saveNewAuthor (String authorName){
        Author saveAuthor = authorService.insert(authorName);
        return String.format("New author save into base: %s", authorConverter.authorToString(saveAuthor));

    }

    @ShellMethod(value = "Update author name", key = "aupd")
    public String updateAuthor(long id, String newName){
        Author updateAuthor = authorService.update(id, newName);
        return String.format("Author update into base: %s", authorConverter.authorToString(updateAuthor));
    }

    @ShellMethod(value = "Remove author out base", key = "adel")
    public String removeAuthorById (long id){
        Author removeAuthor = authorService.deleteById(id);
        return String.format("Author remove out base: %s", authorConverter.authorToString(removeAuthor));
    }
}
