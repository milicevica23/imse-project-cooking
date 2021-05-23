package com.imse.cookingproject.service;

import lombok.RequiredArgsConstructor;
import org.json.JSONException;
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

    public List<JSONObject> getRecipesWithCoverList() throws JSONException {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getRecipesWithCoverList();
        } else {
            return null;
        }
    }

    public List<JSONObject> getRecipesRatingDesc() throws JSONException {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getListRatingDesc();
        } else {
            return null;
        }

    }

    public List<JSONObject> getRecipesSelected(String recipeName) throws JSONException {
        if(SOURCE_OF_DATA) {
            return filterServiceSQL.getListRatingSelectedName(recipeName);
        } else {
            return null;
        }
    }
}
