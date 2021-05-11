package com.imse.cookingproject.controller;

import com.imse.cookingproject.model.Recipe;
import com.imse.cookingproject.service.FilterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("filter")
public class FilterController {
    @Autowired
    private FilterService filterService;

    @GetMapping("/listCoverRecipes")
    public List<String> getRecipesWithCover() {
        return filterService.getRecipesWithCoverList();
    }

    @GetMapping("/listRecipesRatingDesc")
    public List<String> getRecipesRatingDesc() {
        return filterService.getRecipesRatingDesc();
    }

    @GetMapping("/listRecipesSelected")
    @ResponseBody
    public List<String> getRecipesSelected(@RequestParam("recipename") String recipeName) {
        return filterService.getRecipesSelected(recipeName);
    }
}
