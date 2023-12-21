package ru.otus.marchenko.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
import ru.otus.marchenko.models.Comment;
import ru.otus.marchenko.services.CommentService;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class CommentController {

    private final CommentService service;

    @GetMapping("/comment/{bookId}")
    public String listPage(@PathVariable("bookId") Long bookId, Model model) {
        List<CommentDto> commentsByBook = service.findAllByBookId(bookId);
        model.addAttribute("comments", commentsByBook);
        return "comment/list";
    }

    @GetMapping("/comment/add/{bookId}")
    public String addCommentByBook(@PathVariable("bookId") Long bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "comment/add";
    }

    @PostMapping("/comment/add/{bookId}")
    public String saveNewComment(CommentCreateDto commentDto) {
        service.insert(commentDto);
        return "redirect:/comment/{bookId}";
    }

    @GetMapping("/comment/delete/{id}")
    public String deleteComment(@PathVariable("id") Long id,
                                @RequestParam("bookId") Long bookId,
                                RedirectAttributes redirectAttributes) {
        service.deleteById(id);
        redirectAttributes.addAttribute("bookId", bookId);
        return "redirect:/comment/{bookId}";
    }
}
