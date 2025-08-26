package com.iassure.incident.service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class QdrantService {
    @Value("${qdrant.url}")
    private String qdrantUrl;



    private final RestTemplate restTemplate = new RestTemplate();
    private final Gson gson = new Gson();

    public void createCollection(String collectionName) {
        String url = qdrantUrl + "/collections/" + collectionName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        try {
            // Check if the collection already exists
            restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), String.class);
            System.out.println("Collection already exists. Skipping creation.");
        } catch (HttpClientErrorException.NotFound e) {
            // Collection does not exist, proceed to create it
            JsonObject vectorsConfig = new JsonObject();
            vectorsConfig.addProperty("size", 1536); // Vector dimension size
            vectorsConfig.addProperty("distance", "Cosine"); // Distance metric (Cosine, Euclidean, etc.)

            JsonObject payload = new JsonObject();
            payload.add("vectors", vectorsConfig);

            try {
                HttpEntity<String> entity = new HttpEntity<>(payload.toString(), headers);
                ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    System.out.println("Collection created successfully.");
                } else {
                    System.err.println("Failed to create collection: " + response.getBody());
                }
            } catch (Exception ex) {
                System.err.println("Error during collection creation: " + ex.getMessage());
            }
        } catch (Exception e) {
            System.err.println("Error while checking or creating collection: " + e.getMessage());
        }
    }


    public void uploadVector(String collectionName, String fileName, String content, float[] vector) {
        String url = qdrantUrl + "/collections/" + collectionName + "/points";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Validate inputs
        if (fileName == null || content == null || vector == null) {
            throw new IllegalArgumentException("fileName, content, and vector must not be null");
        }

        JsonObject point = new JsonObject();
        point.addProperty("id", System.currentTimeMillis()); // Unique ID for the point
        point.add("vector", gson.toJsonTree(vector));        // Add vector

        JsonObject payloadMeta = new JsonObject();
        payloadMeta.addProperty("fileName", fileName);       // Metadata: fileName
        payloadMeta.addProperty("content", content);         // Metadata: content
        point.add("payload", payloadMeta);                   // Attach metadata

        JsonArray pointsArray = new JsonArray();
        pointsArray.add(point);

        JsonObject payload = new JsonObject();
        payload.add("points", pointsArray);

        try {
            HttpEntity<String> entity = new HttpEntity<>(payload.toString(), headers);
            ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, entity, String.class);

            if (response.getStatusCode().is2xxSuccessful()) {
                System.out.println("Vector uploaded successfully for file: " + fileName);
            } else {
                System.err.println("Failed to upload vector: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("Error while uploading vector: " + e.getMessage());
        }
    }


    public String search(String collectionName, float[] queryVector, Map<String, String> filters, int limit) {
        String searchUrl = qdrantUrl + "/collections/" + collectionName + "/points/search";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Build search payload
        JsonObject searchPayload = new JsonObject();
        searchPayload.add("vector", gson.toJsonTree(queryVector));
        searchPayload.addProperty("with_payload", true);
        searchPayload.addProperty("limit", limit * 2); // Fetch extra results to account for duplicates

        // Add filters if provided
        if (filters != null && !filters.isEmpty()) {
            JsonObject filterObject = new JsonObject();
            JsonArray mustConditions = new JsonArray();

            for (Map.Entry<String, String> entry : filters.entrySet()) {
                JsonObject condition = new JsonObject();
                condition.add("key", gson.toJsonTree(entry.getKey()));
                JsonObject matchObject = new JsonObject();
                matchObject.add("value", gson.toJsonTree(entry.getValue()));
                condition.add("match", matchObject);
                mustConditions.add(condition);
            }

            JsonObject filter = new JsonObject();
            filter.add("must", mustConditions);
            filterObject.add("filter", filter);

            searchPayload.add("filter", filterObject.get("filter"));
        }

        // Execute search query
        HttpEntity<String> searchEntity = new HttpEntity<>(searchPayload.toString(), headers);
        ResponseEntity<String> searchResponse;
        try {
            searchResponse = restTemplate.exchange(searchUrl, HttpMethod.POST, searchEntity, String.class);
        } catch (Exception e) {
            return gson.toJson(Map.of("error", "Failed to query Qdrant: " + e.getMessage()));
        }

        // Parse and process the response
        JsonObject searchResponseBody;
        try {
            searchResponseBody = JsonParser.parseString(Objects.requireNonNull(searchResponse.getBody())).getAsJsonObject();
        } catch (Exception e) {
            return gson.toJson(Map.of("error", "Invalid response from Qdrant: " + e.getMessage()));
        }

        JsonArray results = searchResponseBody.getAsJsonArray("result");

        JsonArray outputResults = new JsonArray();
        Set<String> seenFileNames = new HashSet<>(); // Track included file names

        if (results != null && !results.isEmpty()) {
            for (int i = 0; i < results.size(); i++) {
                if (outputResults.size() >= limit) break; // Stop once we reach the desired limit

                JsonObject result = results.get(i).getAsJsonObject();
                JsonObject payload = result.has("payload") ? result.getAsJsonObject("payload") : null;

                if (payload != null) {
                    String fileName = payload.has("fileName") ? payload.get("fileName").getAsString() : "Unknown";

                    if (!seenFileNames.contains(fileName)) {
                        seenFileNames.add(fileName); // Mark file name as seen

                        JsonObject output = new JsonObject();
                        output.addProperty("fileName", fileName);
                        output.addProperty("content", payload.has("content") ? payload.get("content").getAsString() : "No content available");
                        output.addProperty("score", result.has("score") ? result.get("score").getAsFloat() : 0.0f); // Include similarity score
                        outputResults.add(output);
                    }
                }
            }
        }

        JsonObject finalResponse = new JsonObject();
        if (!outputResults.isEmpty()) {
            finalResponse.add("top_results", outputResults);
        } else {
            finalResponse.addProperty("message", "No relevant results found.");
        }

        return gson.toJson(finalResponse);
    }




}
