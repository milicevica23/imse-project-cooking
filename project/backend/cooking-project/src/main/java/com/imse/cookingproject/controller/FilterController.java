package com.imse.cookingproject.controller;

import com.imse.cookingproject.model.Recipe;
import com.imse.cookingproject.service.FilterService;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
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
    public ArrayList<HashMap<String, Object>> getRecipesSelected(@RequestParam("recipename") String recipeName) throws JSONException {
        ArrayList a = new ArrayList<HashMap<String, Object>>();
        for(var el : filterService.getRecipesSelected(recipeName)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("recipe_id", el.get("recipe_id"));
            String str = "";
            try{
                str = (String) el.get("link");
            }catch (JSONException e) {
                str = "data:image/jpeg;base64,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";
            }
            map.put("link", str);
            a.add(map);
        }
        return a; //new ResponseEntity<Object>(filterService.getRecipesSelected(recipeName), HttpStatus.OK);
    }
}
