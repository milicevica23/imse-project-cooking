package com.imse.cookingproject.service;

import com.imse.cookingproject.model.Recipe;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FilterService {
    private final boolean SOURCE_OF_DATA = true;
    @Autowired
    private FilterServiceSQL filterServiceSQL;
    @Autowired
    private FilterServiceNoSQL filterServiceNoSQL;

    public List<String> getRecipesWithCoverList() {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getRecipesWithCoverList();
        } else {
            return null;
        }
    }

    public List<String> getRecipesRatingDesc() {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getListRatingDesc();
        } else {
            return null;
        }

    }

    public List<JSONObject> getRecipesSelected(String recipeName) {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getListRatingSelectedName(recipeName);
        } else {
            return null;
        }
    }
    

  /*

    public String getFilterRecipe(String recipeName) {
        if(SOURCE_OF_DATA) {
            return filterServiceSQl.getFilteredList(recipeName);
        } else {
            return "NO SQL";
        }
    }*/
}
