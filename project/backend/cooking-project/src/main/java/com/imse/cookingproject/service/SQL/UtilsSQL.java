package com.imse.cookingproject.service.SQL;

import com.imse.cookingproject.model.DatabaseSession;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;

import javax.print.Doc;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
public class UtilsSQL {

    public static Integer getSizeOfTable(String tableName) {
        String query = "select count(*) from " + tableName;
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || resultSet.wasNull()) return 0;
            while (resultSet.next()) {
                return resultSet.getInt("count");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }




    public static List<Document> getRecipeRatings(Object recipe_id, HashMap mapping_user_id) {
        List<Document> ratings = new ArrayList<Document>();
        String query = "select * from user_recipe_rating where recipe_id=" + recipe_id;
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return ratings;
            while (resultSet.next()) {
                Integer userId = resultSet.getInt("user_id");
                String date = resultSet.getString("date");
                Integer rating = resultSet.getInt("rating");

                Document document = new Document("_id", new ObjectId())
                        .append("user_id", mapping_user_id.get(userId))
                        .append("date", date)
                        .append("rating", rating);
                ratings.add(document);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ratings;
    }

    public static List<Document> getRecipeComments(Object recipe_id, HashMap mapping_user_id) {
        List<Document> comments = new ArrayList<Document>();
        String query = "select * from user_recipe_comment where recipe_id=" + recipe_id + " order by date";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return comments;
            do  {
                Integer userId = resultSet.getInt("user_id");
                Integer commentId = resultSet.getInt("comment_id");
                String date = resultSet.getString("date");
                String content = resultSet.getString("content");

                Document document = new Document("_id", new ObjectId())
                        .append("user_id", mapping_user_id.get(userId))
                        .append("date", date)
                        .append("content", content);
                comments.add(document);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static List<Document> getRecipeIngredient(Object recipe_id) {
        List<Document> ingredients = new ArrayList<Document>();
        String query = "select * from (select * from recipe_ingredient where recipe_id="+ recipe_id +") as r_i left join ingredient on r_i.recipe_id=ingredient.ingredient_id";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return ingredients;
            do {
                Integer ingredientId = resultSet.getInt("ingredient_id");
                String ingredientName = resultSet.getString("ingredient_name");
                String amount = resultSet.getString("amount");
                ObjectId id = new ObjectId();
                Document document = new Document("_id", id)
                        .append("ingredient_name", ingredientName)
                        .append("amount", amount);
                ingredients.add(document);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ingredients;
    }

    public static List<Document> getRecipePhotos(Document recipe, Object recipe_id) {
        ArrayList<Document> photos = new ArrayList<>();
        String query = "select * from photo where recipe_id=" + recipe_id;
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return photos;
            do{
                Integer photoId = resultSet.getInt("photo_id");
                String description = resultSet.getString("description");
                String date = resultSet.getString("date");
                String link = resultSet.getString("link");

                if(description.equals("Cover Photo")){
                    Document document = new Document("_id", new ObjectId())
                            .append("description", description)
                            .append("date", date)
                            .append("link",link);
                    recipe.append("cover_photo", document);
                }else{
                    Document document = new Document("_id", new ObjectId())
                            .append("description", description)
                            .append("date", date)
                            .append("link",link);
                    photos.add(document);
                }
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return photos;
    }


    public static Document checkUser(String userName, String userPassword) {
        String query = "select * from users where username='" + userName + "' and password='" + userPassword + "'";
        Document response = new Document();
        response.append("status", "not defined");
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(!resultSet.next()) return response;
            do{
                response.append("status", "userExists");
                response.append("_id",resultSet.getInt("user_id"));
                response.append("username",resultSet.getString("username"));
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return response;
    }

    public static HashMap<String, Object> registerUser(HashMap<String, Object> payload) {
        HashMap<String,Object> response = new HashMap<>();
        response.put("status", "not defined");

        //check user doesnt exits
        String query = "select * from users where username='" + payload.get("username") + "'";
        log.info(query);
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet.next()) {
                    response.put("status", "user exists");
                    return response;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // find max id
        Integer max_id = findMaxId("user_id", "users");
        Integer new_id = max_id + 1;

        // insert user with new id and payload
        String insertQuery = "INSERT INTO users(user_id, username, password, name, email) VALUES " +
                "(" + new_id + ", '" + payload.get("username") + "', '" + payload.get("password") + "', '" + payload.get("name") +"' , '" + payload.get("email") + "')";
        DatabaseSession.executeUpdate(insertQuery);

        response.put("status", "ok");
        response.put("message", "user added with username " + payload.get("username") + " please login again");

        return response;
    }


    public static Integer findMaxId(String columName, String tableName){
        String max_id_query = "select max("+ columName +") max_id from "+ tableName;
        ResultSet resultSet = DatabaseSession.executeQuery(max_id_query);
        Integer max_id = -1;
        try{
            if(!resultSet.wasNull()) {
                while (resultSet.next()) {
                    return resultSet.getInt("max_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return max_id;
    }

    public static List<Document> getrecipeInstructions(Object recipe_id) {
        List<Document> instructions = new ArrayList<Document>();
        String query = "select * from instruction where recipe_id="+ recipe_id + " order by step_number asc";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return instructions;
            do {
                Integer step_number = resultSet.getInt("step_number");
                String content = resultSet.getString("content");
                ObjectId id = new ObjectId();
                Document document = new Document("_id", id)
                        .append("step_number", step_number)
                        .append("content", content);
                instructions.add(document);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return instructions;
    }
}
