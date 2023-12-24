package ru.otus.marchenko.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ru.otus.marchenko.dto.genre.GenreCreateDto;
import ru.otus.marchenko.services.GenreService;

@Controller
@RequiredArgsConstructor
public class GenreController {

    private final GenreService service;

    @GetMapping("/genre/add")
    public String addNewGenre(GenreCreateDto genreCreateDto, Model model) {
        model.addAttribute("genreCreateDto", genreCreateDto);
        return "genre/add";
    }

    @PostMapping("/genre/add")
    public String saveNewGenre(@Valid GenreCreateDto genreCreateDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "/genre/add";
        }
        service.create(genreCreateDto);
        return "redirect:../book/add";
    }
}
