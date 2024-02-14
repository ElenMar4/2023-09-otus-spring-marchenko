package ru.otus.marchenko.services;

import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
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

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ConversionService {

    private final Map<Long, String> mapAuthorRelations = new HashMap<>();
    private final Map<Long, String> mapGenreRelations = new HashMap<>();
    private final Map<Long, String> mapBookRelations = new HashMap<>();

    private final MongoTemplate mongoTemplate;

    public AuthorDocument convertAuthor(AuthorTable authorTable) {
        AuthorDocument authorDocument = new AuthorDocument(authorTable.getId().toString(), authorTable.getFullName());
        mapAuthorRelations.put(authorTable.getId(), authorDocument.getId());
        return authorDocument;
    }

    public GenreDocument convertGenre(GenreTable genreTable) {
        GenreDocument genreDocument = new GenreDocument(genreTable.getId().toString(), genreTable.getName());
        mapGenreRelations.put(genreTable.getId(), genreDocument.getId());
        return genreDocument;
    }

    public BookDocument convertBook(BookTable bookTable) {

        String authorDocumentId = mapAuthorRelations.get(bookTable.getAuthor().getId());
        AuthorDocument authorDocument;
        if (authorDocumentId != null){
            authorDocument = mongoTemplate.findById(authorDocumentId, AuthorDocument.class);
        } else {
            authorDocument = convertAuthor(bookTable.getAuthor());
        }

        String genreDocumentId = mapGenreRelations.get(bookTable.getGenre().getId());
        GenreDocument genreDocument;
        if (genreDocumentId != null) {
            genreDocument = mongoTemplate.findById(genreDocumentId, GenreDocument.class);
        }else {
            genreDocument = convertGenre(bookTable.getGenre());
        }

        BookDocument bookDocument = new BookDocument(bookTable.getId().toString(), bookTable.getTitle(), authorDocument, genreDocument);
        mapBookRelations.put(bookTable.getId(), bookDocument.getId());
        return bookDocument;
    }

    public CommentDocument convertComment(CommentTable commentTable) {

        String bookDocumentId = mapBookRelations.get(commentTable.getBook().getId());
        BookDocument bookDocument;
        if (bookDocumentId != null){
            bookDocument = mongoTemplate.findById(bookDocumentId, BookDocument.class);
        } else{
            bookDocument = convertBook(commentTable.getBook());
        }
        return new CommentDocument(commentTable.getId().toString(), commentTable.getMessage(), bookDocument);
    }
}
