package ru.sbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sbt.service.RecipeService;

/**
 * Контроллер главной страницы
 */
@Controller
public class MainController {

    @Autowired
    private RecipeService recipeService;

    @RequestMapping("/")
    public String show(Model model) {
        model.addAttribute(recipeService.getAll());
        return "index";
    }
}
