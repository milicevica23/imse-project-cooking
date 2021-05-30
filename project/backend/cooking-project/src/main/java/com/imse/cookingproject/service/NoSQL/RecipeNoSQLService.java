package com.imse.cookingproject.service.NoSQL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class RecipeNoSQLService {
    @Value("${app.mongo-host}")
    private String mongoHost;


    public HashMap<String, Object> insertNewRecipe(HashMap<String, Object> payload) {
        return new HashMap<>();
    }

    public HashMap<String, Object> getOneRecipe(String recipeId) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> recipesCollection = database.getCollection("recipes");
        ArrayList<HashMap<String,Object>> result_list = new ArrayList<>();
        Document document = recipesCollection.aggregate(
                Arrays.asList(
                        Aggregates.match(Filters.eq("recipe._id", "60b3ddef75116868391ae4d4"))
                )
        ).first();//.forEach(document -> result_list.add(new HashMap<String,Object>(document)));
        return new HashMap<>(document);

    }

    public List<HashMap<String, Object>> getRecipes(String recipeName, String filterOrder) {
        return new ArrayList<>();
    }

    public HashMap<String, Object> addComment(HashMap<String, Object> payload) {
        return new HashMap<>();
    }
}
