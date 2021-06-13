package com.imse.cookingproject.service.NoSQL;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.CreateCollectionOptions;
import com.mongodb.client.model.Indexes;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;

@Service
@Slf4j
public class ConfigurationNoSQLService {
    @Value("${app.mongo-host}")
    private String mongoHost;



    public void createSchema(){
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        log.info(connectionString);
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        ArrayList<String> listOfCollections = database.listCollectionNames()
                .into(new ArrayList<String>());
        if(listOfCollections.contains("users")){
           database.getCollection("users").drop();
        }
        if(listOfCollections.contains("recipes")){
            database.getCollection("recipes").drop();
        }
        CreateCollectionOptions createCollectionOptionsUsers = new CreateCollectionOptions();
        database.createCollection("users",createCollectionOptionsUsers);
        CreateCollectionOptions createCollectionOptionsRecipe = new CreateCollectionOptions();
        database.createCollection("recipes",createCollectionOptionsRecipe);
        MongoCollection<Document> recipesCollections = database.getCollection("recipes");
        recipesCollections.createIndex(Indexes.ascending("user_id"));
        recipesCollections.createIndex(Indexes.ascending("recipe_name"));
    }

    public void migrateData() {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        log.info(connectionString);
        MongoClient mongoClient = MongoClients.create(connectionString);
        HashMap<Integer, ObjectId> mapping_user_id = UtilsNoSQL.migrateUser(mongoClient);

        UtilsNoSQL.migrateRecipe(mongoClient,mapping_user_id);
    }
}
