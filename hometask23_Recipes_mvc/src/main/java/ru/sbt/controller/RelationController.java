package ru.sbt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sbt.dto.RecipeDto;
import ru.sbt.dto.RelationDto;

import javax.servlet.http.HttpSession;
import java.util.List;

;

/**
 * Этот контроллер работает с ингредиентами существующего рецепта.
 */
@Controller
@RequestMapping("relation/")
public class RelationController {

    /**
     * Этот обработчик берет из сессии текущий рецепт и удаляет из него ингредиент.
     */
    @RequestMapping(value = "delete/{ingredientName}", method = RequestMethod.POST)
    public String delete(HttpSession httpSession, @PathVariable("ingredientName") String ingredientName, Model model) {
        RecipeDto recipeDto = (RecipeDto) httpSession.getAttribute("recipe");
        recipeDto.getRelations().removeIf(relation -> relation.getIngredientName().equals(ingredientName));
        List<String> ingredientNames = (List<String>) httpSession.getAttribute("ingredients");
        ingredientNames.add(ingredientName);
        prepareModel(model, httpSession, recipeDto);
        return "recipe";
    }

    /**
     * Этот обработчик берет из сессии текущий рецепт и добавляет в него ингредиент.
     */
    @RequestMapping(value = "add", method = RequestMethod.POST)
    public String add(HttpSession httpSession, RelationDto relationDto, Model model) {
        RecipeDto recipeDto = (RecipeDto) httpSession.getAttribute("recipe");
        recipeDto.getRelations().add(relationDto);
        relationDto.setRecipeName(recipeDto.getName());
        List<String> ingredientNames = (List<String>) httpSession.getAttribute("ingredients");
        ingredientNames.remove(relationDto.getIngredientName());
        prepareModel(model, httpSession, recipeDto);
        return "recipe";
    }

    private void prepareModel(Model model, HttpSession session, RecipeDto recipeDto) {
        model.addAttribute(recipeDto);
        model.addAttribute("ingredients", session.getAttribute("ingredients"));
        model.addAttribute(new RelationDto());
    }
}
