package com.imse.cookingproject.controller;

import com.imse.cookingproject.model.Ingredient;
import com.imse.cookingproject.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("ingredient")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @PostMapping("/add")
    public void postUsers(@RequestBody Ingredient ingredient) {
        ingredientService.saveToDatabase(ingredient);
    }

    @PostMapping("/dropIngredients")
    public void dropTable() {
        ingredientService.dropTable();
    }

    @GetMapping("/listIngredients")
    public List<Ingredient> getUsers() {
        return ingredientService.getIngredientsList();
    }

    @PutMapping("/deleteIngredient/{ingredient_id}")
    public void deleteIngredient(@PathVariable int ingredient_id) {
        ingredientService.deleteIngredient(ingredient_id);
    }

    @PostMapping("/generateData")
    public void generateData() {
        ingredientService.generateData();
    }
}
