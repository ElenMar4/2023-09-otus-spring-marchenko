package ru.otus.marchenko.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.marchenko.converters.GenreConverter;
import ru.otus.marchenko.models.Genre;
import ru.otus.marchenko.services.GenreService;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class GenreCommands {

    private final GenreService genreService;

    private final GenreConverter genreConverter;

    @ShellMethod(value = "Find all genres", key = "ag")
    public String findAllGenres() {
        return genreService.findAll().stream()
                .map(genreConverter::genreToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find genre by id", key = "gbid")
    public String findById(long id){
        return genreService.findById(id)
                .map(genreConverter::genreToString)
                .orElse("Genre with id %d not found".formatted(id));
    }

    @ShellMethod(value = "Save new genre", key = "gins")
    public String saveNewGenre (String genreName){
        Genre saveGenre = genreService.insert(genreName);
        return String.format("New genre save into base: %s", genreConverter.genreToString(saveGenre));
    }

    @ShellMethod(value = "Update genre name", key = "gupd")
    public String updateGenre(long id, String newName){
        Genre genre = genreService.update(id, newName);
        return genre == null ?
                String.format("Genre with id = %d not found", id)
                : String.format("Genre update: %s", genreConverter.genreToString(genre));
    }

    @ShellMethod(value = "Remove genre out base", key = "gdel")
    public String removeGenreById (long id){
        Genre removeGenre = genreService.deleteById(id);
        return String.format("Genre remove out base: %s", genreConverter.genreToString(removeGenre));
    }
}
