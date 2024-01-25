package ru.otus.marchenko.models.dto.comment;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentCreateDto{

    @NotBlank(message = "Message field should not be blank")
    private String message;

    @NotBlank(message = "Book field should not be null")
    private String bookId;
}
