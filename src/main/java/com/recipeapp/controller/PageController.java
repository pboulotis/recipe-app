package com.recipeapp.controller;

import com.recipeapp.model.Recipe;
import com.recipeapp.repository.RecipeRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/recipes")
public class PageController {

    private final RecipeRepository recipeRepository;

    public PageController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public String showRecipes(Model model) {
        model.addAttribute("recipes", recipeRepository.findAll());
        model.addAttribute("newRecipe", new Recipe());
        return "recipes";
    }

    @PostMapping
    public String addRecipe(@ModelAttribute Recipe newRecipe) {
        recipeRepository.save(newRecipe);
        return "redirect:/recipes";
    }

    @GetMapping("/new")
    public String newRecipeForm(Model model) {
        model.addAttribute("newRecipe", new Recipe());
        return "new-recipe";
    }

    @GetMapping("/edit/{id}")
    public String editRecipe(@PathVariable Long id, Model model) {
        Recipe recipe = recipeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
        model.addAttribute("recipe", recipe);
        return "edit-recipe";
    }

    @PostMapping("/edit/{id}")
    public String updateRecipe(@PathVariable Long id, @ModelAttribute Recipe updatedRecipe) {
        Recipe existingRecipe = recipeRepository.findById(id).
            orElseThrow(() -> new IllegalArgumentException("Invalid recipe Id:" + id));
            
        existingRecipe.setName(updatedRecipe.getName());
        existingRecipe.setDescription(updatedRecipe.getDescription());
        existingRecipe.setIngredients(updatedRecipe.getIngredients());
        existingRecipe.setPrepTime(updatedRecipe.getPrepTime());
        existingRecipe.setCategory(updatedRecipe.getCategory());
        existingRecipe.setProcedure(updatedRecipe.getProcedure());

        recipeRepository.save(existingRecipe);
        return "redirect:/recipes";
    }

    @GetMapping("/delete/{id}")
    public String deleteRecipe(@PathVariable Long id) {
        recipeRepository.deleteById(id);
        return "redirect:/recipes";
    }
}
