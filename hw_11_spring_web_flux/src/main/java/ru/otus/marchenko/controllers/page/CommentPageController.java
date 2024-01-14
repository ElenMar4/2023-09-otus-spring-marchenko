package ru.otus.marchenko.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CommentPageController {

    @GetMapping("/comment/{bookId}")
    public String listPage(@PathVariable("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "comment/list";
    }

    @GetMapping("/comment/add/{bookId}")
    public String addCommentByBook(@PathVariable("bookId") String bookId, Model model) {
        model.addAttribute("bookId", bookId);
        return "comment/add";
    }
}
