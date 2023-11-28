package ru.otus.marchenko.commands;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import ru.otus.marchenko.converters.CommentConverter;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.services.CommentService;

import java.util.Set;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ShellComponent
public class CommentCommands {

    private final CommentService commentService;

    private final CommentConverter commentConverter;

    @ShellMethod(value = "Find all comments for book", key = "cbb")
    public String findAllCommentsByBook(Long bookId){
        return commentService.findAllByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById (Long id){
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Author with id %d not found".formatted(id));
    }

    //ac  1  ddddd ddd sdsd ddd
    @ShellMethod(value = "Add comment for Book", key = "cins")
    public String addCommentByBook (Long bookId, Set<String> text){
        String message = String.join(" ", text);
        Comment newComment = commentService.insert(bookId, message);
        return String.format("New comment save: %s", commentConverter.commentToString(newComment));
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public String deleteCommentById (Long id){
        Comment deleteComment = commentService.deleteById(id);
        return String.format("Comment delete: %s", commentConverter.commentToString(deleteComment));
    }
}
