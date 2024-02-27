package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import ru.otus.marchenko.models.noSqlModels.AuthorDocument;
import ru.otus.marchenko.models.noSqlModels.BookDocument;
import ru.otus.marchenko.models.noSqlModels.CommentDocument;
import ru.otus.marchenko.models.noSqlModels.GenreDocument;
import ru.otus.marchenko.models.sqlModels.AuthorTable;
import ru.otus.marchenko.models.sqlModels.BookTable;
import ru.otus.marchenko.models.sqlModels.CommentTable;
import ru.otus.marchenko.models.sqlModels.GenreTable;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final MongoTemplate mongoTemplate;

    @Cacheable(cacheNames = "authors", key = "#authorTable.getId()")
    public AuthorDocument convertAuthor(AuthorTable authorTable) {
        return new AuthorDocument(authorTable.getId().toString(), authorTable.getFullName());
    }

    @Cacheable(cacheNames = "genres", key = "#genreTable.getId()")
    public GenreDocument convertGenre(GenreTable genreTable) {
        return new GenreDocument(genreTable.getId().toString(), genreTable.getName());
    }

    @Cacheable(cacheNames = "books", key = "#bookTable.getId()")
    public BookDocument convertBook(BookTable bookTable) {

        AuthorDocument authorDocument = convertAuthor(bookTable.getAuthor());

        GenreDocument genreDocument = convertGenre(bookTable.getGenre());

        return new BookDocument(bookTable.getId().toString(), bookTable.getTitle(), authorDocument, genreDocument);
    }

    public CommentDocument convertComment(CommentTable commentTable) {

        BookDocument bookDocument = convertBook(commentTable.getBook());
        return new CommentDocument(commentTable.getId().toString(), commentTable.getMessage(), bookDocument);
    }
}
