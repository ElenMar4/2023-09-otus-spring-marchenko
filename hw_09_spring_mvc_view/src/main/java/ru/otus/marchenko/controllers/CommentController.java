package ru.otus.marchenko.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.otus.marchenko.dto.comment.CommentCreateDto;
import ru.otus.marchenko.dto.comment.CommentDto;
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
        CommentCreateDto commentCreateDto = new CommentCreateDto(null, bookId);
        model.addAttribute("bookId", bookId);
        model.addAttribute("commentCreateDto", commentCreateDto);
        return "comment/add";
    }

    @PostMapping("/comment/add/{bookId}")
    public String saveNewComment(@PathVariable("bookId") Long bookId, @Valid @ModelAttribute("commentCreateDto") CommentCreateDto commentCreateDto,
                                 BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/comment/add";
        }
        service.create(commentCreateDto);
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
