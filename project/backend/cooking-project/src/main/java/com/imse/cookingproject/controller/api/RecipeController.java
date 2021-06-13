package com.imse.cookingproject.controller.api;

import com.imse.cookingproject.service.main.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@CrossOrigin
@Slf4j
@RestController
@RequestMapping("recipe")
public class RecipeController {
    @Autowired
    RecipeService recipeService;

    @PostMapping("insertNewRecipe")
    public HashMap<String, Object> insertNewRecipe(@RequestBody HashMap<String,Object> payload, @RequestParam String dbType){
        return recipeService.insertNewRecipe(payload,dbType);
    }

    @GetMapping("getOneRecipe")
    public Document getOneRecipe(@RequestParam String recipeId, @RequestParam String dbType){
        log.info("recipe_id: " + recipeId + " " + "dbtype: " + dbType);
        return recipeService.getOneRecipe(recipeId,dbType);
    }

    @GetMapping("getRecipes")
    public List<Document> getRecipes(@RequestParam(defaultValue = "") String recipeName, @RequestParam(defaultValue = "") String filterOrder, @RequestParam String dbType, @RequestParam(defaultValue = "10") Integer limit){
        log.info("recipeName: " + recipeName + " " + "dbtype: " + dbType + " " + "filterOrder: " + filterOrder + "limit: " + limit);
        return recipeService.getRecipes(recipeName,filterOrder,dbType,limit);
    }

    @PostMapping("addComment")
    public HashMap<String,Object> addComment(@RequestBody HashMap<String,Object> payload,@RequestParam String dbType){
        return recipeService.addComment(payload,dbType);
    }

    @PostMapping("addRating")
    public HashMap<String,Object> addRating(@RequestBody HashMap<String,Object> payload,@RequestParam String dbType){
        return recipeService.addRating(payload,dbType);
    }
}
