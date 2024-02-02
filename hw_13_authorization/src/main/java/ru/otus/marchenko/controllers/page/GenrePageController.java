package ru.otus.marchenko.controllers.page;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class GenrePageController {

    @GetMapping("/genre/add")
    public String addNewGenre(Model model) {
        return "genre/add";
    }
}
