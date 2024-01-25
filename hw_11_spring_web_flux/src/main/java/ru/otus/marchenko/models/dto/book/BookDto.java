package ru.otus.marchenko.models.dto.book;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.marchenko.models.dto.author.AuthorDto;
import ru.otus.marchenko.models.dto.genre.GenreDto;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {

    private String id;
    private String title;
    private AuthorDto authorDto;
    private GenreDto genreDto;
}
