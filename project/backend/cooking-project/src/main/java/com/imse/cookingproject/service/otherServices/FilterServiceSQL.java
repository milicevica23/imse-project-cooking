package com.imse.cookingproject.service.otherServices;

import com.imse.cookingproject.model.Filter;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class FilterServiceSQL {

    public List<JSONObject> getRecipesWithCoverList() throws JSONException {
        ArrayList arrayList = new ArrayList<HashMap<String, Object>>();
        for(var el : Filter.getRecipesWithCoverList()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("recipe_id", el.get("recipe_id"));
            map.put("recipe_name", el.get("recipe_name"));
            map.put("cooking_time", el.get("cooking_time"));
            map.put("preparation_time", el.get("preparation_time"));
            map.put("username", el.get("username"));
            String str = "";
            try{
                str = (String) el.get("link");
            }catch (JSONException e) {
                str = "https://img.huffingtonpost.com/asset/5dfa93ca250000730698e94e.png?cache=df49dffHCQ&ops=scalefit_720_noupscale";
            }
            map.put("link", str);
            arrayList.add(map);
        }
        return arrayList;
    }

    public List<JSONObject> getListRatingDesc() throws JSONException {
        ArrayList arrayList = new ArrayList<HashMap<String, Object>>();
        for(var el : Filter.getDescRatingList()){
            HashMap<String, Object> map = new HashMap<>();
            map.put("recipe_id", el.get("recipe_id"));
            map.put("recipe_name", el.get("recipe_name"));
            map.put("average_r", el.get("average_r"));
            map.put("username", el.get("username"));
            map.put("preparation_time", el.get("preparation_time"));
            String str = "";
            try{
                str = (String) el.get("link");
            }catch (JSONException e) {
                str = "https://img.huffingtonpost.com/asset/5dfa93ca250000730698e94e.png?cache=df49dffHCQ&ops=scalefit_720_noupscale";
            }
            map.put("link", str);
            arrayList.add(map);
        }
        return arrayList;
    }

    public List<JSONObject> getListRatingSelectedName(String recipeName) throws JSONException {
        ArrayList arrayList = new ArrayList<HashMap<String, Object>>();

        for(var el : Filter.getSelectedRecipeList(recipeName)){
            HashMap<String, Object> map = new HashMap<>();
            map.put("recipe_id", el.get("recipe_id"));
            map.put("recipe_name", el.get("recipe_name"));
            map.put("average_r", el.get("average_r"));
            map.put("preparation_time", el.get("preparation_time"));
            map.put("username", el.get("username"));
            String str = "";
            try{
                str = (String) el.get("link");
            }catch (JSONException e) {
                str = "https://img.huffingtonpost.com/asset/5dfa93ca250000730698e94e.png?cache=df49dffHCQ&ops=scalefit_720_noupscale";
            }
            map.put("link", str);
            arrayList.add(map);
        }
        return arrayList;
    }
}
