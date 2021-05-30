package com.imse.cookingproject.controller.other;

import com.imse.cookingproject.model.Recipe;
import com.imse.cookingproject.service.otherServices.RecipeService_1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("recipe")
public class RecipeController_1 {
    @Autowired
    private RecipeService_1 recipeService;

    @PostMapping("/add")
    public void postRecipes(@RequestBody Recipe recipe) {
        recipeService.saveToDatabase(recipe);
    }

    @PostMapping("/dropRecipes")
    public void dropTable() {
        recipeService.dropTable();
    }

    @GetMapping("/listRecipes")
    public List<Recipe> getRecipes() {
        return recipeService.getRecipesList();
    }

    @PutMapping("/deleteRecipe/{recipe_id}")
    public void deleteRecipe(@PathVariable int recipe_id) {
        recipeService.deleteRecipe(recipe_id);
    }

    @PostMapping("/generateData")
    public void generateData() {
        recipeService.generateData();
    }

}