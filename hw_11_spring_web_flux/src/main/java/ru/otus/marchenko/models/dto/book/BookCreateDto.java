package ru.otus.marchenko.models.dto.book;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookCreateDto{

    @NotBlank(message = "Title field should not be blank")
    private String title;

    @NotBlank(message = "Author field should not be null")
    private String authorId;

    @NotBlank(message = "Genre field should not be null")
    private String genreId;
}
