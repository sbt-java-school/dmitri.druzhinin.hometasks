package ru.sbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.sbt.dto.IngredientDto;
import ru.sbt.dto.RecipeDto;
import ru.sbt.dto.RelationDto;
import ru.sbt.exception.BusinessException;
import ru.sbt.service.IngredientService;
import ru.sbt.service.RecipeService;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер, ответственный за работу с рецептом.
 */
@Controller
@RequestMapping("/recipe/")
public class RecipeController {
    private final RecipeService recipeService;

    private final IngredientService ingredientService;

    @Autowired
    public RecipeController(IngredientService ingredientService, RecipeService recipeService) {
        this.ingredientService = ingredientService;
        this.recipeService = recipeService;
    }

    /**
     * @param name название рецепта, который должен быть помещен в модель.
     */
    @RequestMapping("{name}")
    public String show(HttpSession httpSession, @PathVariable("name") String name, Model model) {
        RecipeDto recipeDto = recipeService.get(name);
        httpSession.setAttribute("ingredients", ingredientService.getAll().stream().map(IngredientDto::getName).collect(Collectors.toList()));
        prepareModel(model, httpSession, recipeDto);
        return "recipe";
    }

    /**
     * @return форму для создания имени нового рецепта.
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showNewRecipeForm(Model model) {
        RecipeDto recipeDto = new RecipeDto();
        model.addAttribute(recipeDto);
        return "createRecipe";
    }

    /**
     * @return форму для добавления новых ингредиентов в рецепт.
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String showNewRelationFormForRecipe(HttpSession httpSession, Model model, @Valid RecipeDto recipeDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            return "redirect:create";
        }
        httpSession.setAttribute("ingredients", ingredientService.getAll().stream().map(IngredientDto::getName).collect(Collectors.toList()));
        prepareModel(model, httpSession, recipeDto);
        return "addRelation";
    }

    /**
     * @return форму для добавления новых ингредиентов в создаваемый рецепт.
     */
    @RequestMapping(value = "create/relation", method = RequestMethod.POST)
    public String addRelation(HttpSession httpSession, Model model, RelationDto relationDto) {
        RecipeDto recipeDto = (RecipeDto) httpSession.getAttribute("recipe");
        relationDto.setRecipeName(recipeDto.getName());
        recipeDto.getRelations().add(relationDto);
        prepareModel(model, httpSession, recipeDto);
        return "addRelation";
    }

    /**
     * @return форму для создания описания создаваемого рецепта.
     */
    @RequestMapping(value = "create/description", method = RequestMethod.GET)
    public String showDescriptionForm(Model model) {
        return "description";
    }

    /**
     * Этот обработчик добавляет описание в создаваемый рецепт и сохраняет его.
     */
    @RequestMapping(value = "create/description", method = RequestMethod.POST)
    public String addDescription(HttpSession httpSession, @RequestParam("description") String description, Model model){
        RecipeDto recipeDto=(RecipeDto) httpSession.getAttribute("recipe");
        recipeDto.setDescription(description);
        try {
            recipeService.create(recipeDto);
        }catch(BusinessException e){
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
            return "redirect:/";
    }

    /**
     *Этот обработчие
     */
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public String save(HttpSession httpSession, @RequestParam("description") String description) {
        RecipeDto recipeDto = (RecipeDto) httpSession.getAttribute("recipe");
        recipeDto.setDescription(description);
        recipeService.save(recipeDto);
        return "redirect:/";
    }

    @RequestMapping(value = "delete/{name}", method = RequestMethod.POST)
    public String delete(@PathVariable("name") String name, Model model){
        recipeService.delete(name);
        return "redirect:/";
    }

    private void prepareModel(Model model, HttpSession session, RecipeDto recipeDto) {
        List<String> ingredientNames = (List<String>) session.getAttribute("ingredients");
        ingredientNames.removeAll(recipeDto.getRelations().stream().map(RelationDto::getIngredientName).collect(Collectors.toList()));
        model.addAttribute("ingredients", ingredientNames);
        model.addAttribute(new RelationDto());
        model.addAttribute(recipeDto);
        session.setAttribute("recipe", recipeDto);
    }
}
