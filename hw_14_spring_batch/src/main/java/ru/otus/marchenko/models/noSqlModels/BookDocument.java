package ru.otus.marchenko.models.noSqlModels;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document(collection = "books")
public class BookDocument {
    @Id
    private String id;

    private String title;

    private AuthorDocument author;

    private GenreDocument genre;

}