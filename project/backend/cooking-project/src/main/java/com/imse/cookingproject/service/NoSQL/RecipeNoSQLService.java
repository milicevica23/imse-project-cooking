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
            insert_instructions.add(new Document("_id", new ObjectId()).append("step_num", instruction.get("step_num")).append("content",instruction.get("content")));
        }
        log.info(insert_instructions.toString());
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
        Field f2  = new Field<>("user_name", "$user_name.username");

        Document document = recipesCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(new Document("_id", new Document("$eq", new ObjectId(recipeId)))),
                        new Document("$lookup", new Document("from", "users").append("let", new Document("user_id_temp","$user_id")).append("pipeline", Arrays.asList(
                                Aggregates.match(new Document("$expr", new Document("$eq", Arrays.asList("$_id", "$$user_id_temp")))),
                                Aggregates.project(new Document("username",1).append("_id", 0))
                        )).append("as", "user_name")
                        ),
                        Aggregates.unwind("$user_name"),
                        Aggregates.addFields(Arrays.asList(f1,f2)),
                        new Document("$lookup", new Document("from", "users")
                                                .append("let", new Document("comments", "$comments"))
                                                .append("pipeline", Arrays.asList(
                                                    Aggregates.match(new Document("$expr", new Document("$in", Arrays.asList("$_id","$$comments.user_id")))),
                                                       Aggregates.addFields(new Field<>("user_comments", new Document("$filter", new Document("input","$$comments")
                                                                                                                                                .append("as","comment")
                                                                                                                                                .append("cond", new Document(
                                                                                                                                                        "$eq", Arrays.asList("$$comment.user_id", "$_id")
                                                                                                                                                ))

                                                       ))),
                                                        Aggregates.unwind("$user_comments"),
                                                        Aggregates.project(new Document("username",1)
                                                                                .append("content", "$user_comments.content")
                                                                                .append("date","$user_comments.date")
                                                        )
                                                ))
                                                .append("as", "comments")
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

        for(Document ingredient: (ArrayList<Document>)document.get("instructions")){
            ingredient.append("_id",ingredient.get("_id").toString());
        }

        for(Document comment: (ArrayList<Document>)document.get("comments")){
            comment.append("_id",comment.get("_id").toString());
        }
        return document;
    }

    public List<Document> getRecipes(String recipeName, String filterOrder, Integer limit) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");

        Field f1  = new Field<>("avg_rating", new Document("$avg","$ratings.rating"));


        Bson filter;
        if(recipeName.equals("")){
            filter  = Filters.ne("recipe_name", recipeName);
        }else{
            filter  =  Filters.eq("recipe_name", recipeName);
        }

        AggregateIterable<Document> result = recipesCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(filter),
                        Aggregates.addFields(f1),
                        Aggregates.sort(new Document("avg_rating",-1)),
                        Aggregates.limit(limit),
                        new Document("$lookup", new Document("from", "users").append("let", new Document("user_id_temp","$user_id")).append("pipeline", Arrays.asList(
                                Aggregates.match(new Document("$expr", new Document("$eq", Arrays.asList("$_id", "$$user_id_temp")))),
                                Aggregates.project(new Document("username",1).append("_id", 0))
                                )).append("as", "user_name")
                        ),
                        Aggregates.unwind("$user_name"),
                        Aggregates.project(new Document("user_name", "$user_name.username")
                                                .append("user_id",1)
                                                .append("recipe_name",1)
                                                .append("date",1)
                                                .append("preparation_time",1)
                                                .append("cooking_time",1)
                                                .append("avg_rating",1)
                                                .append("cover_photo.link" ,1)
                                                .append("course",1)
                                                .append("cuisine",1)
                                                .append("user_id",1)
                        )
                    )
                );
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

    public HashMap<String, Object> addRating(HashMap<String, Object> payload) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");
        HashMap<String,Object> response = new HashMap<>();
        response.put("status", "you have already rated");
        Bson query= Filters.and(
                Filters.eq("_id", new ObjectId((String)payload.get("recipe_id"))),
                Filters.eq("ratings.user_id", new Document("$eq", new ObjectId((String)payload.get("user_id")))
                )
        );
        Document query_result = recipesCollection.find(query).first();
        if (query_result == null) {
            response.put("status", "ok");
            Document rating = new Document("_id", new ObjectId()).append("user_id", new ObjectId((String) payload.get("user_id"))).append("date", payload.get("date")).append("rating",(int)payload.get("rating"));
            recipesCollection.updateOne(new Document("_id", new ObjectId((String) payload.get("recipe_id"))), new Document("$push", new Document("ratings", rating)));
        }
        Field f1  = new Field<>("avg_rating", new Document("$avg","$ratings.rating"));
        Document query_response = recipesCollection.aggregate(
                Arrays.asList(
                    Aggregates.addFields(f1),
                    Aggregates.project(new Document("avg_rating", 1))
                )
        ).first();
        response.put("new_avg_rating",query_response.get("avg_rating"));
        return response;
    }
}
