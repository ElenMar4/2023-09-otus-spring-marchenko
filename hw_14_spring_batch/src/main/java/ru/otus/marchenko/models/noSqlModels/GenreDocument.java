package ru.otus.marchenko.models.noSqlModels;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "genres")
public class GenreDocument {
    @Id
    private String id;

    @Setter
    private String name;
}