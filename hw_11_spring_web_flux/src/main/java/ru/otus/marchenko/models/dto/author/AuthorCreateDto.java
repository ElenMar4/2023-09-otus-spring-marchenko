package ru.otus.marchenko.models.dto.author;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorCreateDto{

    @NotBlank(message = "Name field should not be blank")
    private String fullName;
}
