package com.imse.cookingproject.controller;

import com.imse.cookingproject.model.UserRecipeRating;
import com.imse.cookingproject.service.UserRecipeRatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("rating")
public class UserRecipeRatingController {
    @Autowired
    private UserRecipeRatingService ratingService;

    @PostMapping("/add")
    public void postRatings(@RequestBody UserRecipeRating rating) {
        ratingService.saveToDatabase(rating);
    }

    @PostMapping("/dropRatings")
    public void dropTable() {
        ratingService.dropTable();
    }

    @GetMapping("/listRatings")
    public List<UserRecipeRating> getRatings() {
        return ratingService.getRatingsList();
    }

    @PutMapping("/deleteRating/{rating_id}")
    public void deleteRating(@PathVariable int rating_id) {
        ratingService.deleteRating(rating_id);
    }

    @PostMapping("/generateData")
    public void generateData() {
        ratingService.generateData();
    }
}
