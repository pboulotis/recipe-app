package com.recipeapp.repository;

import com.recipeapp.model.Category;
import com.recipeapp.model.Recipe;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @NotNull
    List<Recipe> findByName(String name);
    @Query("SELECT r FROM Recipe r JOIN r.ingredients i WHERE i.name = :name")
    List<Recipe> findByIngredientName(@Param("name") String name);
    List<Recipe> findByCategory(Category category);

}
