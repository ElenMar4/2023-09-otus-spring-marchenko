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
    public String findAllCommentsByBook(String bookId){
        return commentService.findAllByBookId(bookId).stream()
                .map(commentConverter::commentToString)
                .collect(Collectors.joining("," + System.lineSeparator()));
    }

    @ShellMethod(value = "Find comment by id", key = "cbid")
    public String findCommentById (String id){
        return commentService.findById(id)
                .map(commentConverter::commentToString)
                .orElse("Author with id %s not found".formatted(id));
    }

    @ShellMethod(value = "Add comment for Book", key = "cins")
    public String addCommentByBook (Set<String> text, String bookId){
        String message = String.join(" ", text);
        Comment newComment = commentService.insert(null, message, bookId);
        return newComment == null ? "Comment not save" : String.format(
                "New comment save: %s", commentConverter.commentToString(newComment));
    }

    @ShellMethod(value = "Update comment", key = "cupd")
    public String updateComment(String id, Set<String> newText, String bookId){
        String newMessage = String.join(" ", newText);
        Comment newComment = commentService.update(id, newMessage, bookId);
        return newText == null ?
                String.format("Comment with id: %s not found", id)
                : String.format("New comment save: %s", commentConverter.commentToString(newComment));
    }

    @ShellMethod(value = "Delete comment by id", key = "cdel")
    public String deleteCommentById (String id){
        commentService.deleteById(id);
        return "Comment delete";
    }
}