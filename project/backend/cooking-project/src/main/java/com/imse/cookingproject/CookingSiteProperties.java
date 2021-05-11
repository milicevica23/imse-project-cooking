package com.imse.cookingproject;

public class CookingSiteProperties {
    private static final Integer userAmount = 20;
    private static final Integer recipeAmount = 30;
    private static final Integer ratingAmount = 200;
    private static final Integer commentAmount = 45;
    private static final Integer photoAmount = 90;
    private static final Integer instructionAmount = 90;
    private static final Integer ingredientAmount = 150;

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
}
