package com.example.firstdockerproject;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/test")
public class Controller {

    @GetMapping("/id_url")
    public String test(@RequestParam String id_url) throws IOException, InterruptedException {
        String url = "http://" + id_url +":5000/helloworld";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("accept", "application/json")
                .uri(URI.create(url))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
       // return "hello version 2";
    }

}
