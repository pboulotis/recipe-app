package com.recipeapp.controller;

import com.recipeapp.model.Category;
import com.recipeapp.model.Ingredient;
import com.recipeapp.model.Recipe;
import com.recipeapp.repository.RecipeRepository;
import com.recipeapp.response.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeRepository recipeRepository;

    public RecipeController(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @GetMapping
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRecipeById(@PathVariable Long id) {
        return recipeRepository.findById(id)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse("Recipe not found")));
    }

    @PostMapping
    public ResponseEntity<String> addRecipe(@RequestBody Recipe newRecipe) {
        List<Recipe> existingRecipes = recipeRepository.findByName(newRecipe.getName());

        Set<Ingredient> newIngredientsSet = new HashSet<>(newRecipe.getIngredients());

        // Duplicate ingredients check
        for (Recipe recipe : existingRecipes) {
            Set<Ingredient> existingIngredientsSet = new HashSet<>(recipe.getIngredients());

            if (existingIngredientsSet.equals(newIngredientsSet)) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body("Error: Recipe with same name and ingredients already exists!");
            }
        }

        recipeRepository.save(newRecipe);
        return ResponseEntity.status(HttpStatus.CREATED).body("Recipe added successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable Long id, @RequestBody Recipe newRecipe) {
        return recipeRepository.findById(id)
                .map(existingRecipe -> {
                    existingRecipe.setName(newRecipe.getName());
                    existingRecipe.setDescription(newRecipe.getDescription());
                    existingRecipe.setIngredients(newRecipe.getIngredients());
                    existingRecipe.setPrepTime(newRecipe.getPrepTime());
                    existingRecipe.setProcedure(newRecipe.getProcedure());
                    existingRecipe.setCategory(newRecipe.getCategory());
                    recipeRepository.save(existingRecipe);
                    return ResponseEntity.ok("Recipe updated successfully!");
                })
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Error: Recipe not found"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable Long id) {
        if (recipeRepository.existsById(id)) {
            recipeRepository.deleteById(id);
            return ResponseEntity.ok("Recipe deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Recipe not found");
        }
    }

    @GetMapping("/ingredients/{name}")
    public List<Recipe> getByIngredient(@PathVariable String name) {
        return recipeRepository.findByIngredientName(name);
    }

    @GetMapping("/category/{category}")
    public List<Recipe> getByCategory(@PathVariable Category category) {
        return recipeRepository.findByCategory(category);
    }

}
