package com.recipeapp.repository;

import com.recipeapp.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByName(String name);
    List<Recipe> findByCategory(String category);
    List<Recipe> findByTagsContaining(String tag);
    List<Recipe> findByIngredientsName(String name);

    @Query("SELECT r FROM Recipe r WHERE "
            + "(:category IS NULL OR r.category = :category) AND "
            + "(:tag IS NULL OR :tag MEMBER OF r.tags) AND "
            + "(:ingredient IS NULL OR EXISTS (SELECT i FROM r.ingredients i WHERE i.name = :ingredient))")
    List<Recipe> searchRecipes(@Param("category") String category,
                               @Param("tag") String tag,
                               @Param("ingredient") String name);
}
