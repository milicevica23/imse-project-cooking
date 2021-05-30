package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeService_1 {
    private final Recipe recipe = new Recipe();

    public void saveToDatabase(Recipe recipe) {
        recipe.saveInsertToDatabase();
    }

    public void dropTable() {
        recipe.dropTable();
    }

    public void deleteRecipe(int id) {
        Recipe.deleteRecipe(id);
    }

    public List<Recipe> getRecipesList() {
        return Recipe.returnList();
    }

    public void generateData() {
        Recipe.generateData();
    }

}