package ru.otus.marchenko.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@Document("books")
public class Book {

    @Id
    private String id;

    private String title;
    @DBRef
    private Author author;
    @DBRef
    private Genre genre;
}