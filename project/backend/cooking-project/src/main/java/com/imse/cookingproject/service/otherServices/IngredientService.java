package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.Ingredient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IngredientService {
    private final Ingredient ingredient = new Ingredient();

    public void saveToDatabase(Ingredient ingredient) {
        ingredient.saveInsertToDatabase();
    }

    public void dropTable() {
        ingredient.dropTable();
    }

    public List<Ingredient> getIngredientsList() {
        return Ingredient.returnList();
    }

    public void deleteIngredient(int ingredient_id) {
        Ingredient.deleteIngredient(ingredient_id);
    }

    public void generateData() {
        Ingredient.generateData();
    }
}
