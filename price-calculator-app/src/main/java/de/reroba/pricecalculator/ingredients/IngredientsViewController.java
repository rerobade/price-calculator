package de.reroba.pricecalculator.ingredients;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping(path = "ingredients", produces = MediaType.TEXT_HTML_VALUE)
public class IngredientsViewController {

    private final IngredientsApiController ingredientsApiController;

    @Autowired
    public IngredientsViewController(IngredientsApiController ingredientsApiController) {
        this.ingredientsApiController = ingredientsApiController;
    }

    @GetMapping(consumes = MediaType.ALL_VALUE)
    public String showAllIngredients(Model model) {
        model.addAttribute("ingredients", ingredientsApiController.getIngredients(null));
        return "ingredients/index";
    }

    @GetMapping("/create")
    public String showAddForm(IngredientDto ingredient, Model model) {
        model.addAttribute("ingredient", ingredient);
        return "ingredients/create";
    }

    @PostMapping("/create")
    public String createIngredient(@Valid @ModelAttribute("ingredient") IngredientDto ingredient, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "ingredients/create";
        }

        ingredientsApiController.createIngredient(ingredient);

        return this.showAllIngredients(model);
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        IngredientDto ingredient = ingredientsApiController.getIngredient(id);
                //.orElseThrow(() -> new IllegalArgumentException("Invalid ingredient Id:" + id));

        model.addAttribute("ingredient", ingredient);
        return "ingredients/update";
    }

    @PostMapping("/update/{id}")
    public String updateIngredientDto(@PathVariable("id") long id, @Valid IngredientDto ingredient,
                             BindingResult result, Model model) {
        if (result.hasErrors()) {
            ingredient.setId(id);
            return "ingredients/update";
        }

        ingredientsApiController.updateIngredient(id, ingredient);

        return this.showAllIngredients(model);
    }

    @GetMapping("/delete/{id}")
    public String deleteIngredientDto(@PathVariable("id") long id, Model model) {
        /*IngredientDto ingredient = ingredientsApiController.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ingredient Id:" + id));*/
        ingredientsApiController.deleteIngredient(id);

        return this.showAllIngredients(model);
    }
}
