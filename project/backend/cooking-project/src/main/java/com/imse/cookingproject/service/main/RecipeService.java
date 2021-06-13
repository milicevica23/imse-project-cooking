package com.imse.cookingproject.service.main;

import com.imse.cookingproject.service.NoSQL.RecipeNoSQLService;
import com.imse.cookingproject.service.SQL.RecipeSQLService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeService {
    @Autowired
    RecipeSQLService recipeSQLService;

    @Autowired
    RecipeNoSQLService recipeNoSQLService;

    public HashMap<String, Object> insertNewRecipe(HashMap<String, Object> payload, String dbType) {
        if(dbType.equals("SQL")) {
            return recipeSQLService.insertNewRecipe(payload);
        }else{
            return recipeNoSQLService.insertNewRecipe(payload);
        }
    }

    public Document getOneRecipe(String recipeId, String dbType) {
        if(dbType.equals("SQL")) {
            return recipeSQLService.getOneRecipe(recipeId);
        }else {
            return recipeNoSQLService.getOneRecipe(recipeId);
        }
    }

    public List<Document> getRecipes(String recipeName, String filterOrder, String dbType, Integer limit) {
        if(dbType.equals("SQL")) {
            return recipeSQLService.getRecipes(recipeName,filterOrder,limit);
        }else{
            return recipeNoSQLService.getRecipes(recipeName,filterOrder, limit);
        }

    }

    public HashMap<String, Object> addComment(HashMap<String, Object> payload, String dbType) {
        if(dbType.equals("SQL")) {
            return recipeSQLService.addComment(payload);
        }else{
            return recipeNoSQLService.addComment(payload);
        }
    }

    public HashMap<String, Object> addRating(HashMap<String, Object> payload, String dbType) {
        if(dbType.equals("SQL")) {
            return recipeSQLService.addRating(payload);
        }else{
            return recipeNoSQLService.addRating(payload);
        }
    }
}
