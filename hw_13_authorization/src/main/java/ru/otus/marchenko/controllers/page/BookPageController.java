package ru.otus.marchenko.controllers.page;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class BookPageController {

    @GetMapping("/book")
    public String listPageBook(Model model) {
        return "book/list";
    }

    @GetMapping("/book/add")
    public String addNewBook(Model model) {
        return "book/add";
    }

    @GetMapping("/book/edit/{id}")
    public String updateBook(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "book/edit";
    }

    @GetMapping("/book/details/{id}")
    public String getBookDetails(@PathVariable("id") Long id, Model model) {
        model.addAttribute("id", id);
        return "book/details";
    }
}
