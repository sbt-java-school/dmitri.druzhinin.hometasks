package ru.sbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.sbt.dto.IngredientDto;
import ru.sbt.exception.BusinessException;
import ru.sbt.service.IngredientService;

import javax.validation.Valid;

/**
 * Этот контроллер ответственен за работу с ингредиентами.
 */
@Controller
@RequestMapping("/ingredient/")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    /**
     * @return форму для создания нового ингредиента и просмотра существующих.
     */
    @RequestMapping(value = "create", method = RequestMethod.GET)
    public String showNewIngredientForm(Model model) {
        return prepareModel(model);
    }

    /**
     * Этот обработчик сохраняет новый ингредиент.
     */
    @RequestMapping(value = "create", method = RequestMethod.POST)
    public String save(@Valid IngredientDto ingredientDto, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "redirect:create";
        }
        ingredientService.save(ingredientDto);
        return prepareModel(model);
    }

    /**
     * @param name название ингредиента, который необходимо удалить.
     */
    @RequestMapping(value = "delete/{name}", method = RequestMethod.POST)
    public String delete(@PathVariable("name") String name, Model model) {
        try {
            ingredientService.delete(name);
        } catch (DataIntegrityViolationException e) {
            model.addAttribute("errorMessage", "This ingredient used in recipes");
            return "error";
        }
        return prepareModel(model);
    }

    private String prepareModel(Model model) {
        model.addAttribute(ingredientService.getAll());
        model.addAttribute(new IngredientDto());
        return "ingredients";
    }

}
