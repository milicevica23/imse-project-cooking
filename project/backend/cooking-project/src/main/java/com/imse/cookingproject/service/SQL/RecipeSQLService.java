package com.imse.cookingproject.service.SQL;

import com.imse.cookingproject.CookingSiteProperties;
import com.imse.cookingproject.model.DatabaseSession;
import com.imse.cookingproject.model.Photo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeSQLService {
    public HashMap<String, Object> insertNewRecipe(HashMap<String, Object> payload) {

        Integer max_id = UtilsSQL.findMaxId("recipe_id", "recipe");
        Integer recipe_new_id = max_id + 1;

        String insertQuery = "INSERT INTO recipe(recipe_id, recipe_name, date, preparation_time, cooking_time, course, cuisine, user_id) VALUES " +
                "(" + recipe_new_id + ", '" + payload.get("recipe_name") + "', '" + payload.get("date") + "', " + payload.get("preparation_time") + ", " +
                payload.get("cooking_time") +" , '" + payload.get("course") + "' , '" +  payload.get("cuisine") +"' , " + payload.get("user_id") + ")";
        DatabaseSession.executeUpdate(insertQuery);

        max_id = UtilsSQL.findMaxId("photo_id", "photo");
        Integer new_id = max_id + 1;

        insertQuery = "INSERT INTO photo(photo_id, description, date, link, recipe_id) VALUES " +
                "(" + new_id + ", '" + "Cover Photo" + "', '" +
                payload.get("date") + "', '" + ((HashMap<String, Object>)payload.get("cover_photo")).get("link") + "', " +  recipe_new_id + ")";
        DatabaseSession.executeUpdate(insertQuery);

        List<HashMap<String,Object>> ingredients = (List<HashMap<String, Object>>) payload.get("ingredient");

        for(var ingredient: ingredients){
            String query = "select * from ingredient where ingredient_name ='"+ ingredient.get("ingredient_name") +"';";
            ResultSet resultSet = DatabaseSession.executeQuery(query);
            Integer ingredient_id = -1;
            try{
                if(resultSet == null || !resultSet.next()){
                    max_id = UtilsSQL.findMaxId("ingredient_id", "ingredient");
                    ingredient_id = max_id + 1;

                    insertQuery = "INSERT INTO ingredient(ingredient_id, ingredient_name) " +
                            "VALUES(" + ingredient_id + ", '" + ingredient.get("ingredient_name") + "')" ;
                    log.info(insertQuery);
                    DatabaseSession.executeUpdate(insertQuery);

                }else {
                    do{
                        ingredient_id = resultSet.getInt("ingredient_id");
                    }while (resultSet.next());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            insertQuery = "insert into recipe_ingredient(recipe_id,ingredient_id,amount) values("
                    + recipe_new_id + "," + ingredient_id +",'" + ingredient.get("amount")+"');" ;

            log.info(insertQuery);
            DatabaseSession.executeUpdate(insertQuery);
        }



        List<HashMap<String,Object>> instructions = (List<HashMap<String, Object>>) payload.get("instructions");

        for(var instruction : instructions){
            max_id = UtilsSQL.findMaxId("instruction_id", "instruction");
            new_id = max_id + 1;
            insertQuery = "INSERT INTO instruction(instruction_id, step_number, content, recipe_id) VALUES " +
            "(" + new_id + ", " + instruction.get("step_num") + ", '" + instruction.get("content") + "', " + recipe_new_id + ")";
            log.info(insertQuery);
            DatabaseSession.executeUpdate(insertQuery);
        }
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("_id", recipe_new_id);
        return response;
    }

    public Document getOneRecipe(String recipeId) {
        Document response = new Document();
        response.append("status", "not defined");

        //get recipe information
        String query = "select * from recipe where recipe_id ="+ recipeId +";";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        Integer user_id = -1;
        try{
            if(!resultSet.next()){
                response.append("status", "recipe with id " +recipeId +" not found");
                return response;
            }
            do {
                response.append("_id",resultSet.getInt("recipe_id"));
                response.append("recipe_name",resultSet.getString("recipe_name"));
                response.append("date",resultSet.getString("date"));
                response.append("preparation_time",resultSet.getInt("preparation_time"));
                response.append("cooking_time",resultSet.getInt("cooking_time"));
                response.append("course",resultSet.getString("course"));
                response.append("cuisine",resultSet.getString("cuisine"));
                user_id = resultSet.getInt("user_id");
                response.append("user_id",user_id);
                response.append("username",getUserName(user_id));
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // get photos
        query = "select * from photo where recipe_id ="+ recipeId +";";
        resultSet = DatabaseSession.executeQuery(query);
        ArrayList<Document> photos = new ArrayList<>();
        try{
            if(resultSet.next()) {
                 do {
                    Document photo = new Document();
                    String photoDescription = resultSet.getString("description");
                    photo.append("_id", resultSet.getString("photo_id"));
                    photo.append("description", resultSet.getString("description"));
                    photo.append("date", resultSet.getString("date"));
                    photo.append("link", resultSet.getString("link"));
                    if (photoDescription.equals("Cover Photo")) {
                        response.append("cover_photo", photo);
                    } else {
                        photos.add(photo);
                    }
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append("photos", photos);

        //get ingredient
        query = "select * from (select * from recipe_ingredient where recipe_id="+ recipeId +") ing left join ingredient on ing.ingredient_id=ingredient.ingredient_id";
        resultSet = DatabaseSession.executeQuery(query);
        ArrayList<Document> ingredients = new ArrayList<>();
        try{
            if(resultSet.next()) {
                do{
                    Document ingredient = new Document();
                    ingredient.append("_id", resultSet.getString("ingredient_id"));
                    ingredient.append("ingredient_name", resultSet.getString("ingredient_name"));
                    ingredient.append("amount", resultSet.getString("amount"));
                    ingredients.add(ingredient);
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append("ingredient", ingredients);


        // get instructions

        query = "select * from instruction where recipe_id="+ recipeId + " order by step_number";
        resultSet = DatabaseSession.executeQuery(query);
        ArrayList<Document> instructions = new ArrayList<>();
        try{
            if(resultSet.next()) {
                do {
                    Document instruction = new Document();
                    instruction.append("_id", resultSet.getInt("instruction_id"));
                    instruction.append("step_num", resultSet.getInt("step_number"));
                    instruction.append("content", resultSet.getString("content"));
                    instructions.add(instruction);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append("instructions", instructions);

        //get comments

        query = "select * from (select * from user_recipe_comment where recipe_id="+ recipeId +") u_c left join users on u_c.user_id=users.user_id order by date";
        resultSet = DatabaseSession.executeQuery(query);
        ArrayList<Document> comments = new ArrayList<>();
        try{
            if(resultSet.next()) {
                 do {
                    Document comment = new Document();
                    comment.append("_id", resultSet.getInt("comment_id"));
                    comment.append("username", resultSet.getString("username"));
                    comment.append("date", resultSet.getString("date"));
                    comment.append("content", resultSet.getString("content"));
                    comments.add(comment);
                } while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.append("comments", comments);

        // get average rating
        query = "select avg(rating) from user_recipe_rating where recipe_id=" + recipeId;
        resultSet = DatabaseSession.executeQuery(query);

        try{
            if(resultSet.next()) {
                do{
                    response.append("avg_rating", resultSet.getDouble("avg"));
                }while (resultSet.next());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.append("status", "ok");
        return response;
    }

    public String getUserName(Integer userId){
        String query = "select * from users where user_id="+ userId;
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(!resultSet.wasNull()) {
                while (resultSet.next()) {
                    return resultSet.getString("username");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "no user";
    }

    public List<Document> getRecipes(String recipeName, String filterOrder) {
        String query = "select * from (select temp.recipe_id, coalesce(avg(temp.rating),0) average_r from\n" +
                        "(select recipe.recipe_id, user_recipe_rating.rating, recipe.date from recipe left join user_recipe_rating on recipe.recipe_id = user_recipe_rating.recipe_id) temp\n" +
                        "group by temp.recipe_id) average_table left join (SELECT rec.recipe_id, rec.preparation_time, rec.recipe_name, rec.course, rec.cuisine, rec.preparation_time,rec.cooking_time, " +
                        "rec.date, us.username, us.user_id, ph.link from recipe rec left join users us on rec.user_id = us.user_id\n" +
                        "left join (select recipe_id, description, link from photo where description = 'Cover Photo')\n" +
                        "ph on rec.recipe_id = ph.recipe_id) recipe_info on average_table.recipe_id = recipe_info.recipe_id\n" +
                        "where recipe_name like '" + recipeName + "%'\n" +
                        "order by average_r " + filterOrder +" LIMIT " +  10;
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        ArrayList<Document> response = new ArrayList<>();
        try{
            if(resultSet == null || !resultSet.next()) return response;
            do {
                Document recipe = new Document();
                recipe.append("_id", resultSet.getInt("recipe_id"));
                recipe.append("date", resultSet.getString("date"));
                recipe.append("avg_rating", resultSet.getDouble("average_r"));
                recipe.append("recipe_name", resultSet.getString("recipe_name"));
                recipe.append("preparation_time", resultSet.getInt("preparation_time"));
                recipe.append("cooking_time", resultSet.getInt("cooking_time"));
                recipe.append("course", resultSet.getString("course"));
                recipe.append("cuisine", resultSet.getString("cuisine"));
                recipe.append("username", resultSet.getString("username"));
                recipe.append("user_id", resultSet.getInt("user_id"));
                Document cover_photo = new Document();
                cover_photo.append("link", resultSet.getString("link"));
                recipe.append("cover_photo", cover_photo);
                response.add(recipe);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public HashMap<String, Object> addComment(HashMap<String, Object> payload) {
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "not defined");

        //proof recipe existance
        String query = "select * from recipe where recipe_id =" + payload.get("recipe_id") +";";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(!resultSet.next()){
                response.put("status", "recipe with id " + payload.get("recipe_id") +" not found");
                return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //proof user existance
        query = "select * from users where user_id =" + payload.get("user_id") +";";
        resultSet = DatabaseSession.executeQuery(query);
        try{
            if(!resultSet.next()){
                response.put("status", "user with id " +  payload.get("user_id") +" not found");
                return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Integer max_id = UtilsSQL.findMaxId("comment_id", "user_recipe_comment");
        Integer new_id = max_id + 1;


        query = "INSERT INTO user_recipe_comment(comment_id, user_id, recipe_id, date, content) VALUES " +
        "(" + new_id + ", " + payload.get("user_id") + ", " + payload.get("recipe_id") + ", '" + payload.get("date") + "', '" + payload.get("content") + "')";
        DatabaseSession.executeUpdate(query);
        response.put("status", "ok");
        return new HashMap<>();
    }

    public HashMap<String, Object> addRating(HashMap<String, Object> payload){
        String query = "select * from user_recipe_rating where user_id =" + payload.get("user_id") + " and recipe_id = " + payload.get("recipe_id");
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        HashMap<String,Object> response = new HashMap<>();
        response.put("status", "you have already rated");
        try {
            if (!resultSet.next()){
                response.put("status", "ok");
                String insertQuery = "INSERT INTO user_recipe_rating(user_id, recipe_id, date, rating) VALUES " +
                        "(" + payload.get("user_id") + ", " +  payload.get("recipe_id") + ", '" + payload.get("date") + "', " + payload.get("rating") + ")";
                DatabaseSession.executeUpdate(insertQuery);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        query = "select recipe_id, avg(rating) as avg_rating from user_recipe_rating where recipe_id = " + payload.get("recipe_id") +" GROUP BY recipe_id";
        resultSet = DatabaseSession.executeQuery(query);

        try {
            if (!resultSet.next()) return response;
            response.put("new_avg_rating", resultSet.getDouble("avg_rating"));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return response;
    }
}

