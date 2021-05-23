package com.imse.cookingproject.controller;

import com.imse.cookingproject.service.FilterService;
import org.json.JSONException;
import org.json.JSONObject;
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
    public List<JSONObject> getRecipesWithCover() throws JSONException {
        return filterService.getRecipesWithCoverList();
    }

    @GetMapping("/listRecipesRatingDesc")
    public List<JSONObject> getRecipesRatingDesc() throws JSONException  {
        return filterService.getRecipesRatingDesc();
    }

    @GetMapping("/listRecipesSelected")
    @ResponseBody
    public List<JSONObject> getRecipesSelected(@RequestParam("recipename") String recipeName) throws JSONException {
        return filterService.getRecipesSelected(recipeName);
    }
}
