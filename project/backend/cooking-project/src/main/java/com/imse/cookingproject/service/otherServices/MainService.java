package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
//@RequiredArgsConstructor    //neden neden calismiyor
public class MainService {
    private final Users user = new Users();
    private final Recipe recipe = new Recipe();
    private final Ingredient ingredient = new Ingredient();
    private final Photo photo = new Photo();
    private final Instruction instruction = new Instruction();
    private final UserRecipeComment comment = new UserRecipeComment();
    private final UserRecipeRating rating = new UserRecipeRating();

    public void createAllTables() {
        log.info("create tables");
        user.createTable();
        recipe.createTable();
        comment.createTable();
        rating.createTable();
        ingredient.createTable();
        photo.createTable();
        instruction.createTable();
    }

    public void dropAllTables() {
        log.info("drop tables");
        instruction.dropTable();
        photo.dropTable();
        ingredient.dropTable();
        comment.dropTable();
        rating.dropTable();
        recipe.dropTable();
        user.dropTable();
    }

    public void generateData() {
        Users.generateData();
        Recipe.generateData();
        UserRecipeRating.generateData();
        UserRecipeComment.generateData();
        Photo.generateData();
        Ingredient.generateData();
        Instruction.generateData();
    }

}
