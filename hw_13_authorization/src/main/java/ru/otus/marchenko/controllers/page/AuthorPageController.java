package ru.otus.marchenko.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthorPageController {

    @GetMapping("/author/add")
    public String addNewAuthor(Model model) {
        return "author/add";
    }
}