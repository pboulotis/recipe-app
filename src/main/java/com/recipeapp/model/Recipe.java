package com.recipeapp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.List;

@Entity
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String name;

    @ElementCollection
    private List<Ingredient> ingredients;

    private String description;
    private String category;
    private Integer prepTime;

    @Column(columnDefinition = "TEXT")
    private String procedure;

    @ElementCollection
    private List<String> tags;

    public Recipe() {

    }

    public Recipe(String name, String description, List<Ingredient> ingredients, String category, Integer prepTime, String procedure, List<String> tags) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.category = category;
        this.prepTime = prepTime;
        this.procedure = procedure;
        this.tags = tags;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Integer getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(Integer prepTime) {
        this.prepTime = prepTime;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
