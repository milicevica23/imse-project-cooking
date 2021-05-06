package com.imse.cookingproject.service;

import com.imse.cookingproject.model.UserRecipeRating;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserRecipeRatingService {
    private final UserRecipeRating rating = new UserRecipeRating();

    public void saveToDatabase(UserRecipeRating rating) {
        rating.saveInsertToDatabase();
    }

    public void dropTable() {
        rating.dropTable();
    }

    public void deleteRating(int id) {
        UserRecipeRating.deleteRating(id);
    }

    public List<UserRecipeRating> getRatingsList() {
        return UserRecipeRating.returnList();
    }

    public void generateData() {
        UserRecipeRating.generateData();
    }
}
