package com.iassure.incident.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class EmbeddingService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

    @Value("${openai.api-key}")
    private String openAiApiKey;

    @Value("${qdrant.url}")
    private String qdrantUrl;

    private static final String OPENAI_COMPLETION_ENDPOINT = "https://api.openai.com/v1/chat/completions";
    private static final String OPENAI_EMBEDDING_ENDPOINT = "https://api.openai.com/v1/embeddings";

    // Method to generate embeddings for a given text
    public float[] generateEmbedding(String text) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + openAiApiKey);
        headers.set("Content-Type", "application/json");

        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("model", "text-embedding-ada-002");
        requestBody.addProperty("input", text);

        HttpEntity<String> entity = new HttpEntity<>(requestBody.toString(), headers);

        ResponseEntity<String> response = restTemplate.exchange(OPENAI_EMBEDDING_ENDPOINT, HttpMethod.POST, entity, String.class);
        JsonObject responseBody = JsonParser.parseString(response.getBody()).getAsJsonObject();
        JsonObject embeddingData = responseBody.getAsJsonArray("data").get(0).getAsJsonObject();
        return gson.fromJson(embeddingData.getAsJsonArray("embedding"), float[].class);
    }

}