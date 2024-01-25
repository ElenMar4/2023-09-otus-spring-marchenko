package ru.otus.marchenko.models.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenreCreateDto{

    @NotBlank(message = "Name field should not be blank")
    private String name;

}
