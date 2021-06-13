package com.imse.cookingproject;

import com.imse.cookingproject.service.SQL.UtilsSQL;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;

@Slf4j
public class CookingSiteProperties {
    private static final Integer userAmount = 20;
    private static final Integer recipeAmount = 30;
    private static final Integer ratingAmount = 5;
    private static final Integer commentAmount = 5;
    private static final Integer photoAmount = 3;
    private static final Integer instructionAmount = 8;
    private static final Integer ingredientAmount = 4;

    public static Integer getUserAmount() {
        return userAmount;
    }

    public static Integer getRecipeAmount() {
        return recipeAmount;
    }

    public static Integer getRatingAmount() {
        return ratingAmount;
    }

    public static Integer getCommentAmount() {
        return commentAmount;
    }

    public static Integer getPhotoAmount() {
        return photoAmount;
    }

    public static Integer getInstructionAmount() {
        return instructionAmount;
    }

    public static Integer getIngredientAmount() {
        return ingredientAmount;
    }


    public static HashMap<String, Object> getConfiguration() {
        HashMap<String, Object> configuration = new HashMap();
        configuration.put("Users", UtilsSQL.getSizeOfTable("users"));
        configuration.put("Recipe", UtilsSQL.getSizeOfTable("recipe"));
        configuration.put("Rating", UtilsSQL.getSizeOfTable("user_recipe_rating"));
        configuration.put("Comment", UtilsSQL.getSizeOfTable("user_recipe_comment"));
        configuration.put("Photo", UtilsSQL.getSizeOfTable("photo"));
        configuration.put("Instructions", UtilsSQL.getSizeOfTable("instruction"));
        configuration.put("Ingredients", UtilsSQL.getSizeOfTable("ingredient"));
        return configuration;
    }
}
