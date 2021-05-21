package com.imse.cookingproject.service;

import com.imse.cookingproject.model.Filter;
import com.imse.cookingproject.model.Recipe;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FilterServiceSQL {

    public List<String> getRecipesWithCoverList() {
        return Filter.getRecipesWithCoverList();
    }

    public List<String> getListRatingDesc() {
        return Filter.getDescRatingList();
    }

    public List<JSONObject> getListRatingSelectedName(String recipeName) {
        return Filter.getSelectedRecipeList(recipeName);
    }
}
