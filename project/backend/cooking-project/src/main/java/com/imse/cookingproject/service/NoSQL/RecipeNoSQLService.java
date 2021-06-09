package com.imse.cookingproject.service.NoSQL;

import com.mongodb.client.*;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Field;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeNoSQLService {
    @Value("${app.mongo-host}")
    private String mongoHost;


    public HashMap<String, Object> insertNewRecipe(HashMap<String, Object> payload) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");

        Document recipe = new Document();
        ObjectId recipe_new_id = new ObjectId();
        recipe.append("recipe_name", payload.get("recipe_name"))
                .append("_id",recipe_new_id)
                .append("date",payload.get("date"))
                .append("preparation_time",payload.get("preparation_time"))
                .append("cooking_time",payload.get("cooking_time"))
                .append("course", payload.get("course"))
                .append("cuisine",payload.get("cuisine"))
                .append("user_id",new ObjectId((String) payload.get("user_id")));

        Document cover_photo = new Document();
        cover_photo.append("_id", new ObjectId())
                .append("description", "Cover Photo")
                .append("date", payload.get("date") )
                .append("link", ((HashMap<String, Object>)payload.get("cover_photo")).get("link"));
        recipe.append("cover_photo", cover_photo);
        recipe.append("photos", new ArrayList<>());

        ArrayList<Document> insert_ingredients = new ArrayList<>();
        List<HashMap<String,Object>> ingredients = (List<HashMap<String, Object>>) payload.get("ingredient");
        for(var ingredient: ingredients){
            insert_ingredients.add(new Document("_id", new ObjectId())
                                                        .append("ingredient_name", ingredient.get("ingredient_name"))
                                                        .append("amount", ingredient.get("amount")));
        }
        recipe.append("ingredient", insert_ingredients);

        ArrayList<Document> insert_instructions = new ArrayList<>();
        List<HashMap<String,Object>> instructions = (List<HashMap<String, Object>>) payload.get("instructions");

        for(var instruction : instructions) {
            insert_instructions.add(new Document("_id", new ObjectId()).append("step_number", instruction.get("step_number")).append("content",instruction.get("content")));
        }
        recipe.append("instructions", insert_instructions);
        recipe.append("comments", new ArrayList<>());

        recipesCollection.insertOne(recipe);
        HashMap<String, Object> response = new HashMap<>();
        response.put("status", "ok");
        response.put("_id", recipe_new_id.toString());
        return response;
    }

    public Document getOneRecipe(String recipeId) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");
        Field f1  = new Field<>("avg_rating", new Document("$avg","$ratings.rating"));
        Field f2  = new Field<>("user_name", new Document("$arrayElemAt", Arrays.asList("$user",0)));

        Document document = recipesCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("_id", new ObjectId(recipeId))),
                        Aggregates.lookup("users", "user_id","_id","user"),
                        Aggregates.lookup("users", "comments.user_id","_id","comment_user"),
                        Aggregates.addFields(f1), Aggregates.addFields(f2)
                        ,Aggregates.project(new Document("user_name","$user_name.username")
                                .append("_id",1)
                                .append("user_id",1)
                                .append("recipe_name",1)
                                .append("date",1)
                                .append("preparation_time",1)
                                .append("avg_rating",1)
                                .append("cooking_time",1)
                                .append("course",1)
                                .append("cuisine",1)
                                .append("cover_photo",1)
                                .append("photos",1)
                                .append("ingredient",1)
                                .append("comments", new Document("$map", new Document("input", "$comments")
                                                                                    .append("as", "comment")
                                                                                    .append("in", new Document(
                                                                                            "user",  new Document("$arrayElemAt" , Arrays.asList(new Document("$filter", new Document(
                                                                                                                                    "input", "$comment_user")
                                                                                                                                    .append("as","current_user")
                                                                                                                                    .append("cond", new Document(
                                                                                                                                            "$eq",Arrays.asList("$$current_user._id", "$$comment.user_id")
                                                                                                                                                                )
                                                                                                                                            )
                                                                                                                                        )
                                                                                                                                        ,0)
                                                                                                                )
                                                                                            ).append("date", "$$comment.date")
                                                                                            .append("content", "$$comment.content")
                                                                                            .append("_id", "$$comment._id")
                                                                                        )
                                                                )
                                )

                        )
                )
        ).first();

        document.append("_id", document.get("_id").toString());
        document.append("user_id", document.get("user_id").toString());
        Document cover_photo = document.get("cover_photo",Document.class);
        cover_photo.append("_id", cover_photo.get("_id").toString());

       for(Document photo: (ArrayList<Document>)document.get("photos")){
            photo.append("_id",photo.get("_id").toString());
        }

        for(Document ingredient: (ArrayList<Document>)document.get("ingredient")){
            ingredient.append("_id",ingredient.get("_id").toString());
        }

        for(Document comment: (ArrayList<Document>)document.get("comments")){
            comment.append("_id",comment.get("_id").toString());
            comment.append("username", ((Document)comment.get("user")).get("username"));
            comment.remove("user");
        }
        return document;
    }

    public List<Document> getRecipes(String recipeName, String filterOrder) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");

        Field f1  = new Field<>("avg_rating", new Document("$avg","$ratings.rating"));
        Field f2  = new Field<>("user_name", new Document("$arrayElemAt", Arrays.asList("$user",0)));
        Bson filter;
        if(recipeName.equals("")){
            filter  = Filters.ne("recipe_name", recipeName);
        }else{
            filter  =  Filters.eq("recipe_name", recipeName);
        }

        AggregateIterable<Document> result = recipesCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(filter)
                        ,Aggregates.lookup("users", "user_id","_id","user"),
                        Aggregates.addFields(f1), Aggregates.addFields(f2),
                        Aggregates.project(new Document("username","$user_name.username")
                                                        .append("_id",1)
                                                        .append("user_id",1)
                                                        .append("recipe_name",1)
                                                        .append("date",1)
                                                        .append("preparation_time",1)
                                                        .append("avg_rating",1)
                                                        .append("cooking_time",1)
                                                        .append("course",1)
                                                        .append("cuisine",1)
                                                        .append("cover_photo.link",1)
                        )
                ));
        ArrayList<Document> response = new ArrayList<>();
        for(Document doc : result){
            doc.append("_id", doc.get("_id").toString());
            doc.append("user_id", doc.get("user_id").toString());
            response.add(doc);
        }
        return response;
    }

    public HashMap<String, Object> addComment(HashMap<String, Object> payload) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");
        Document comment = new Document("_id", new ObjectId()).append("user_id", new ObjectId((String) payload.get("user_id"))).append("date", payload.get("date")).append("content",payload.get("content"));
        recipesCollection.updateOne(new Document("_id", new ObjectId((String) payload.get("recipe_id"))), new Document("$push", new Document("comments", comment)));

        return new HashMap<>();
    }
}
