package ru.otus.marchenko.models.dto.comment;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDto{

    private String id;
    private String message;
    private String bookId;
}
