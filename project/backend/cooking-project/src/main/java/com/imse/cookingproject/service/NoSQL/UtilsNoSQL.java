package com.imse.cookingproject.service.NoSQL;

import com.imse.cookingproject.model.DatabaseSession;
import com.imse.cookingproject.service.SQL.UtilsSQL;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.ValidationOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UtilsNoSQL {

    public static ValidationOptions createValidatorForRecipe(){
        /*
        ValidationOptions collOptions = new ValidationOptions().validator(
                Filters.or(Filters.exists("email"), Filters.exists("phone")));
        database.createCollection("contacts",
                new CreateCollectionOptions().validationOptions(collOptions));*/
        ValidationOptions validationOptions = new ValidationOptions();
        return validationOptions;
    }

    public static ValidationOptions createValidatorForUsers() {
        ValidationOptions validationOptions = new ValidationOptions();
        return validationOptions;
    }

    public static Document createUser() {
        Integer userId = 1;
        String userName = "aleks";
        String password = "12345";
        String name = "aleks1";
        String email = "milicoa@gmail.com";
        //String follows =
        Document document = new Document("user_id", userId)
                .append("username", userName)
                .append("password", password)
                .append("name",name)
                .append("email",email);
        return document;
    }

    public static HashMap<Integer, ObjectId> migrateUser(MongoClient mongoClient) {
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> userCollection = database.getCollection("users");

        List<Document> users = new ArrayList<Document>();
        HashMap<Integer,ObjectId> mapping_user_id = new HashMap<>();
        String query = "select * from users";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return mapping_user_id;
            do {
                Integer userId = resultSet.getInt("user_id");
                String userName = resultSet.getString("username");
                String password = resultSet.getString("password");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                ObjectId id = new ObjectId();
                mapping_user_id.put(userId,id);
                Document document = new Document("_id", id)
                        .append("username", userName)
                        .append("password", password)
                        .append("name",name)
                        .append("email",email);
                users.add(document);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        userCollection.insertMany(users);
        return mapping_user_id;
    }

    public static void migrateRecipe(MongoClient mongoClient, HashMap<Integer,ObjectId> mapping_user_id) {
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");

        List<Document> recipes = new ArrayList<>();//UtilsSQL.getAllRecipe();
        String query = "select * from recipe";
        ResultSet resultSet = DatabaseSession.executeQuery(query);
        try{
            if(resultSet == null || !resultSet.next()) return;
            do {
                Integer recipeId = resultSet.getInt("recipe_id");
                String recipeName = resultSet.getString("recipe_name");
                String date = resultSet.getString("date");
                String preparationTime = resultSet.getString("preparation_time");
                String cookingTime = resultSet.getString("cooking_time");
                String course = resultSet.getString("course");
                String cuisine = resultSet.getString("cuisine");
                Integer userId = resultSet.getInt("user_id");

                ObjectId id = new ObjectId();
                Document document = new Document("_id", id)
                        .append("recipe_id",recipeId)
                        .append("recipe_name", recipeName)
                        .append("date", date)
                        .append("preparation_time",preparationTime)
                        .append("cooking_time",cookingTime)
                        .append("course",course)
                        .append("cuisine",cuisine)
                        .append("user_id",mapping_user_id.get(userId));
                recipes.add(document);
            }while (resultSet.next());
        } catch (SQLException e) {
            e.printStackTrace();
        }

        for(Document recipe : recipes){
            //extract photos
            List<Document> recipe_photos = UtilsSQL.getRecipePhotos(recipe, recipe.get("recipe_id"));
            recipe.append("photos", recipe_photos);

            //extract ingredients
            List<Document> recipe_ingredient = UtilsSQL.getRecipeIngredient(recipe.get("recipe_id"));
            recipe.append("ingredient", recipe_ingredient);

            //extract comments
            List<Document> recipe_comments = UtilsSQL.getRecipeComments(recipe.get("recipe_id"),mapping_user_id);
            recipe.append("comments", recipe_comments);

            //extract ratings
            List<Document> recipe_ratings = UtilsSQL.getRecipeRatings(recipe.get("recipe_id"),mapping_user_id);
            recipe.append("ratings", recipe_ratings);

            recipe.remove("recipe_id");
        }

        recipesCollection.insertMany(recipes);

    }

    public void createRecipe(){
        /*.append("email", "cafeconleche@example.com")
                .append("location", Arrays.asList(-73.92502, 40.8279556)))
                .append("stars", 3)
                .append("categories", Arrays.asList("Bakery", "Coffee", "Pastries"));*/
    }
}
