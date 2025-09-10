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
}
