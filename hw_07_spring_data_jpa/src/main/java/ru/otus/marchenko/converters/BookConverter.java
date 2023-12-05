package ru.otus.marchenko.converters;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.marchenko.models.Book;
import ru.otus.marchenko.models.Comment;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class BookConverter {
    private final AuthorConverter authorConverter;

    private final GenreConverter genreConverter;

    private final CommentConverter commentConverter;

    public String bookToString(Book book) {
        var genresString = book.getGenres().stream()
                .map(genreConverter::genreToString)
                .map("{%s}"::formatted)
                .collect(Collectors.joining(", "));
        return "Id: %d, title: %s, author: {%s}, genres: [%s]".formatted(
                book.getId(),
                book.getTitle(),
                authorConverter.authorToString(book.getAuthor()),
                genresString);
    }

    public String bookWithCommentsToString(Book book, List<Comment> comments){
        var commentsString = comments.stream()
                .map(commentConverter::commentToString).collect(Collectors.joining("\n  - "));
        return "%s\nComments:\n  - %s".formatted(
                bookToString(book),
                commentsString);
    }
}