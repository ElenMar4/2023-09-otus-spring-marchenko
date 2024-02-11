package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import ru.otus.marchenko.models.noSqlModels.AuthorDocument;
import ru.otus.marchenko.models.noSqlModels.BookDocument;
import ru.otus.marchenko.models.noSqlModels.CommentDocument;
import ru.otus.marchenko.models.noSqlModels.GenreDocument;
import ru.otus.marchenko.models.sqlModels.AuthorTable;
import ru.otus.marchenko.models.sqlModels.BookTable;
import ru.otus.marchenko.models.sqlModels.CommentTable;
import ru.otus.marchenko.models.sqlModels.GenreTable;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final Map<Long, AuthorDocument> mapAuthorRelations = new HashMap<>();
    private final Map<Long, GenreDocument> mapGenreRelations = new HashMap<>();
    private final Map<Long, BookDocument> mapBookRelations = new HashMap<>();

    public AuthorDocument convertAuthor(AuthorTable authorTable) {
        AuthorDocument authorDocument = new AuthorDocument(new ObjectId().toString(), authorTable.getFullName());
        mapAuthorRelations.put(authorTable.getId(), authorDocument);
        return authorDocument;
    }

    public GenreDocument convertGenre(GenreTable genreTable) {
        GenreDocument genreDocument = new GenreDocument((new ObjectId().toString()), genreTable.getName());
        mapGenreRelations.put(genreTable.getId(), genreDocument);
        return genreDocument;
    }

    public BookDocument convertBook(BookTable bookTable) {
        AuthorDocument authorDocument = mapAuthorRelations.get(bookTable.getAuthor().getId());
        if (authorDocument == null){
            authorDocument = convertAuthor(bookTable.getAuthor());
        }
        GenreDocument genreDocument = mapGenreRelations.get(bookTable.getGenre().getId());
        if (genreDocument == null) {
            genreDocument = convertGenre(bookTable.getGenre());
        }
        BookDocument bookDocument = new BookDocument(new ObjectId().toString(), bookTable.getTitle(), authorDocument, genreDocument);
        mapBookRelations.put(bookTable.getId(), bookDocument);
        return bookDocument;
    }

    public CommentDocument convertComment(CommentTable commentTable) {
        BookDocument bookDocument = mapBookRelations.get(commentTable.getBook().getId());
        if (bookDocument == null){
            bookDocument = convertBook(commentTable.getBook());
        }
        return new CommentDocument(new ObjectId().toString(), commentTable.getMessage(), bookDocument);
    }
}
