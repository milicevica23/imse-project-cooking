package com.imse.cookingproject.service.NoSQL;

import com.imse.cookingproject.model.Filter;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.print.Doc;
import java.util.Arrays;
import java.util.HashMap;

@Service
@Slf4j
public class RegistrationNoSQLService {
    @Value("${app.mongo-host}")
    private String mongoHost;

    public Document checkLogin(String userName, String userPassword) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document response = usersCollection.find(new Document("$and", Arrays.asList(Filters.eq("username",userName), Filters.eq("password",userPassword)))).projection(new Document("username",1)).first();
        if (response == null){
            response = new Document("status", "no user found");
        }else{
            response.append("status", "userExists");
            response.append("_id", response.get("_id").toString());
        }
        return response;
    }

    public HashMap<String, Object> registerUser(HashMap<String, Object> payload) {
        String connectionString = "mongodb://admin:admin@" + mongoHost;
        MongoClient mongoClient = MongoClients.create(connectionString);
        MongoDatabase database = mongoClient.getDatabase("cookingproject");
        MongoCollection<Document> usersCollection = database.getCollection("users");
        Document user_find = usersCollection.find(new Document("$and", Arrays.asList(Filters.eq("username",payload.get("username"))))).projection(new Document("username",1)).first();
        HashMap<String,Object> response = new HashMap<>();
        response.put("status", "not defined");
        if (user_find == null){
            Document user = new Document("username", payload.get("username")).append("password",payload.get("password")).append("name",payload.get("name")).append("email",payload.get("email"));
            usersCollection.insertOne(user);
            response.put("message", "user added with username " + payload.get("username") + " please login again");
            response.put("status", "ok");
        }else{
            response.put("status", "user exists");
        }
        return response;
    }
}
