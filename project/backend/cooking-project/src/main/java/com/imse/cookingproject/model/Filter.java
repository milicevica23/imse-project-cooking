package com.imse.cookingproject.model;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Filter {

    public static List<JSONObject> getRecipesWithCoverList() {
        String selectQuery = "SELECT rec.recipe_id, rec.recipe_name, rec.preparation_time, rec.cooking_time, us.username, ph.description from recipe rec left join users us " +
                "on rec.user_id = us.user_id inner join (select recipe_id, description from photo where description = 'Cover Photo') ph " +
                "on rec.recipe_id = ph.recipe_id; ";

        List<JSONObject> recipeList= new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return recipeList;
            while (resultSet.next()) {
                recipeList.add(new JSONObject()
                        .put("recipe_id", resultSet.getInt("recipe_id"))
                        .put("recipe_name", resultSet.getString("recipe_name"))
                        .put("username", resultSet.getString("username"))
                        .put("description", resultSet.getString("description"))
                        .put("preparation_time", resultSet.getString("preparation_time"))
                        .put("cooking_time", resultSet.getString("cooking_time"))
                        );
            }
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return recipeList;
    }

    public static List<JSONObject> getDescRatingList() {
        String selectQuery = "select * from (select temp.recipe_id, avg(temp.rating) average_r from " +
                "(select recipe.recipe_id, user_recipe_rating.rating, recipe.date from recipe left join user_recipe_rating on recipe.recipe_id = user_recipe_rating.recipe_id) temp " +
                "group by temp.recipe_id) average_table left join (SELECT rec.recipe_id, rec.preparation_time, rec.recipe_name, us.username, ph.link from recipe rec left join users us on rec.user_id = us.user_id " +
                "left join (select recipe_id, description, link from photo where description = 'Cover Photo') " +
                "ph on rec.recipe_id = ph.recipe_id) recipe_info on average_table.recipe_id = recipe_info.recipe_id " +
                "order by average_r desc;";

        List<JSONObject> list = new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return list;
            while (resultSet.next()) {
                list.add(new JSONObject()
                        .put("recipe_id", resultSet.getInt("recipe_id"))
                        .put("recipe_name", resultSet.getString("recipe_name"))
                        .put("username", resultSet.getString("username"))
                        .put("link", resultSet.getString("link"))
                        .put("average_r", resultSet.getLong("average_r"))
                        .put("preparation_time", resultSet.getLong("preparation_time"))
                        );
            }

        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return list;
    }

    public static List<JSONObject> getSelectedRecipeList(String recipeName) {
        String selectQuery = "select * from (select temp.recipe_id, avg(temp.rating) average_r from " +
                "(select recipe.recipe_id, user_recipe_rating.rating, recipe.date from recipe left join user_recipe_rating on recipe.recipe_id = user_recipe_rating.recipe_id) temp " +
                "group by temp.recipe_id) average_table left join (SELECT rec.recipe_id, rec.preparation_time, rec.recipe_name, us.username, ph.link from recipe rec left join users us on rec.user_id = us.user_id " +
                "left join (select recipe_id, description, link from photo where description = 'Cover Photo') " +
                "ph on rec.recipe_id = ph.recipe_id) recipe_info on average_table.recipe_id = recipe_info.recipe_id " +
                "where recipe_name like '" + recipeName + "%' " +
                "order by average_r desc;";

        log.info(selectQuery);

        List<JSONObject> list = new ArrayList<>();
        ResultSet resultSet = DatabaseSession.executeQuery(selectQuery);
        try{
            if(resultSet.wasNull()) return list;
            while (resultSet.next()) {
                list.add(new JSONObject()
                        .put("recipe_id", resultSet.getInt("recipe_id"))
                        .put("recipe_name", resultSet.getString("recipe_name"))
                        .put("username", resultSet.getString("username"))
                        .put("link", resultSet.getString("link"))
                        .put("average_r", resultSet.getLong("average_r"))
                        .put("preparation_time", resultSet.getLong("preparation_time"))

                );
            }

            for(var a: list){
                log.info("vars: " + a);
            }
        } catch (SQLException | JSONException e) {
            e.printStackTrace();
        }
        return list;
    }
}
