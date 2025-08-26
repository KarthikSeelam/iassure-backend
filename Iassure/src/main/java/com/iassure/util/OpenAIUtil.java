package com.iassure.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.iassure.incident.entity.IncidentDetailsById;
import com.iassure.incident.service.IncidentService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class OpenAIUtil {
    @Value("${openai.api-key}")
    String apiKey;

    @Value("${openai.url}")
    String url;

    @Value("${openai.chat-url}")
    String chatUrl;

    @PersistenceContext
    private EntityManager entityManager;

    static LocalDate currentDate = LocalDate.now();  // Get today's date
    static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    static String formattedDate = currentDate.format(formatter);
    private static final int MAX_RETRIES = 3;
    private static final int RETRY_DELAY_MS = 1000;
    private static final String CREATE_INCIDENT_PROMPT = "{" +
            "    \"source\": \"<SOURCE>\",\n" +
            "    \"category\": \"<CATEGORY>\",\n" +
            "    \"title\": \"<TITLE>\",\n" +
            "    \"description\": \"<DESCRIPTION>\",\n" +
            "    \"department\": \"<DEPARTMENT>\",\n" +
            "    \"severity\": \"<SEVERITY>\"\n" +
            "}\n\n" +
            "Create a JSON object for a new incident using this format. Strictly return only JSON:";

    private static final String CREATE_TASKS_PROMPT = "{\n" +
            "  \"taskName\": \"Task name\",\n" +
            "  \"dueDate\": \"<YYYY-MM-DD>\",\n" +
            "  \"isResolved\": 0\n" +
            "}\n" +
            "Assume today's date is \"" + formattedDate + "\". Estimate the dueDate based on today's date and create a similar JSON for the given task:\n";
    private static final String CREATE_DASHBOARD = "Generate a React component named 'Dashboard' that displays a dashboard based on dynamic data from a JSON String given by the user. " +
            "The component should parse the  and display the data in a table format. Each JSON key should serve as a table header, with corresponding values in rows beneath each header. " +
            "The component should use semantic HTML elements and be visually appealing with inline CSS for a professional layout, including table borders, headers, alternating row background colors, and padding. " +
            "Include a heading 'Dashboard' above the table. " +
            "Ignore all the IDs in the JSON while rendering."+
            "Do not include import statements and DON'T INCLUDE 'ReactDOM.render'. Provide the component as a single unescaped JSX string ready for rendering.";
    private static final String CREATE_SQL_QUERY =
            "Generate SQL queries based on these scenarios:\n" +
            "1. Retrieve active users along with their department names.\n" +
            "2. Find users by specific UserTypeID.\n" +
            "3. List departments with more than a specified number of users.\n" +
            "4. Display active sources filtered by SourceType.\n" +
            "5. Retrieve incident records by severity, status, and assigned user.\n\n" +
            "Use SELECT, JOIN, WHERE, and aggregate functions as needed, focusing on these table relationships and fields. Give me only one single query a string";
    public String summarizeIncident(IncidentDetailsById incidentDetailsById){
        OkHttpClient client = new OkHttpClient();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("prompt", "Summarize the following incident details in a detailed way as a paragraph and keep the values in double quotes.:\n\n" + incidentDetailsById.toString());
        jsonObject.put("max_tokens", 1000);
        jsonObject.put("model", "gpt-3.5-turbo-instruct");
        jsonObject.put("temperature", 0);
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        int attempt = 0;
        while (attempt < MAX_RETRIES) {
            try (Response response = client.newCall(request).execute()) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    JSONObject responseJson = new JSONObject(response.body().string());
                    return responseJson.getJSONArray("choices").getJSONObject(0).getString("text").trim();
                } else if (response.code() == 429) {
                    // Rate limit exceeded, wait and retry
                    attempt++;
                    Thread.sleep(RETRY_DELAY_MS);
                } else {
                    throw new IOException("Unexpected code " + response);
                }
            } catch (IOException | InterruptedException e) {
                if (attempt >= MAX_RETRIES) {
                    return "Error generating summary: " + e.getMessage();
                }
            }
        }
        return "Error generating summary: exceeded retry attempts";
    }

        public JSONObject createIncidentResponse(String userPrompt) throws IOException {
            OkHttpClient client = new OkHttpClient();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("prompt", CREATE_INCIDENT_PROMPT+" "+userPrompt);
            jsonObject.put("max_tokens", 1000);
            jsonObject.put("model", "gpt-3.5-turbo-instruct");
            jsonObject.put("temperature", 0);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

            Request request = new Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();

            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            assert response.body() != null;
            return new JSONObject(response.body().string());
        }

    public JSONObject createTasksResponse(String userPrompt) throws IOException {
        OkHttpClient client = new OkHttpClient();

        // Create the message array for chat completion
        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content", CREATE_TASKS_PROMPT + " " + userPrompt);

        // Construct the main JSON object with model and messages
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo");
        jsonObject.put("messages", new JSONArray().put(messageObject));
        jsonObject.put("max_tokens", 1000);
        jsonObject.put("temperature", 0);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        // Parse the response
        assert response.body() != null;
        return new JSONObject(response.body().string());
    }
    public JSONObject createDashboardResponse(String userPrompt) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        // Create the message array for chat completion
        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content", CREATE_DASHBOARD + " " + userPrompt);

        // Construct the main JSON object with model and messages
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo");
        jsonObject.put("messages", new JSONArray().put(messageObject));
        jsonObject.put("max_tokens", 4000);
        jsonObject.put("temperature", 0);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        // Parse the response
        assert response.body() != null;
        return new JSONObject(response.body().string());
    }

    public JSONObject createSqlResponse(String userPrompt) throws IOException {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        // Get schema details dynamically
        String tableDetails = getSchemaDetails();

        // Refine the prompt with specific instructions
        String refinedPrompt = tableDetails +
                "\nGenerate a unique SQL query with the following requirements:\n" +
                "- Use `JOIN` clauses to connect `IncidentDetails` with `MasterSource` tables for related data.\n" +
                "- Display `SourceType` from `MasterSource` with aliases: 'Incident Source', 'Incident Category', 'Incident Severity', and 'Incident Status'.\n" +
                "- Treat `IncidentDetails.IssueType` as 'Incident Type'.\n" +
                "- Concatenate `FirstName` and `LastName` as 'FullName' for name fields.\n" +
                "- Format all date fields as `DATE_FORMAT(column, '%Y-%m-%d')`.\n" +
                "- Use `SELECT DISTINCT` to ensure unique records.\n" +
                "- Add filtering conditions (`WHERE`, `IS NOT NULL`, etc.) as needed.\n" +
                "- Handle table joins via IDs.\n" +
                "- Convert `Status` fields to human-readable values (e.g., 'Closed' for 2).\n" +
                "- Group results by `IncidentID` or any specified field.\n" +
                "- Apply aggregation (e.g., `SUM`, `AVG`) for relevant numeric fields when required.\n" +
                "- Incorporate multi-conditional filtering within the `WHERE` clause as necessary.\n" +
                "- Exclude `OrgID` and organization names in joins.\n" +
                "- Use aliases like `M`, `M1`, `M2`, and `M3` for `MasterSource` tables based on `SourceName` values.\n" +
                "- Handle SourceType filtering in the `WHERE` clause.\n" +
                "- Ensure all column names are one word (e.g., 'IncidentSource' instead of 'Incident Source').\n\n" +
                "Sample query structure:\n" +
                "\n" +
                "SELECT DISTINCT I.IncidentID, I.SourceID, I.CategoryID, I.CaseSummary, I.CaseDescription, I.SeverityID, I.AttachmentURL, I.Comments, \n" +
                "I.AssignedUserID, I.IncidentStatusID, I.CreatedBy, M.SourceType AS Category, M1.SourceType AS Severity, \n" +
                "DATE_FORMAT(I.CreatedAt, '%Y-%m-%d') AS CreatedAt, M2.SourceType AS Status, M3.SourceType AS Source, \n" +
                "CONCAT(US.FirstName, ' ', US.LastName) AS AssignedUser, DM.DepartmentName, DM.DepartmentID\n" +
                "FROM IncidentDetails I\n" +
                "INNER JOIN MasterSource M ON I.CategoryID = M.SourceID AND M.SourceName = 'Incident Category'\n" +
                "INNER JOIN MasterSource M1 ON I.SeverityID = M1.SourceID AND M1.SourceName = 'Incident Severity'\n" +
                "INNER JOIN MasterSource M2 ON I.IncidentStatusID = M2.SourceID AND M2.SourceName = 'Incident Status'\n" +
                "INNER JOIN MasterSource M3 ON I.SourceID = M3.SourceID AND M3.SourceName = 'Incident Source'\n" +
                "INNER JOIN DepartmentMaster DM ON I.DepartmentID = DM.DepartmentID\n" +
                "INNER JOIN Users US ON I.AssignedUserID = US.UserID\n" +
                "WHERE I.OrgID = piOrgID\n" +
                "AND I.IncidentID = CASE WHEN piIncidentID = 0 THEN I.IncidentID ELSE piIncidentID END;\n\n" +
                "Generate only the SQL query based on these conventions and the specific input provided below, excluding ID fields and preserving column names. Include all columns, and address additional filters or groupings based on the input:\n" +
                userPrompt;


        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content", refinedPrompt);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo");
        jsonObject.put("messages", new JSONArray().put(messageObject));
        jsonObject.put("max_tokens", 1500);  // Adjust max tokens to avoid repetitive responses
        jsonObject.put("temperature", 0.7);  // Increase temperature for more diverse results

        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        assert response.body() != null;
        return new JSONObject(response.body().string());
    }

    public JSONObject generateSuggestions(String input) throws IOException {

        OkHttpClient client = new OkHttpClient();

        // Create the message array for chat completion
        JSONObject messageObject = new JSONObject();
        messageObject.put("role", "user");
        messageObject.put("content",
                "Provide exactly 2 concise suggestions for improving the following prompt, formatted as a JSON array. " +
                        "Each suggestion should enhance clarity, specificity, or context of the prompt. " +
                        "Respond in JSON format only, structured as follows:\n\n" +
                        "{ \"suggestions\": [ \"Suggestion 1\", \"Suggestion 2\" ] }\n\n" +
                        "Prompt: " + input
        );

        // Construct the main JSON object with model and messages
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("model", "gpt-3.5-turbo");
        jsonObject.put("messages", new JSONArray().put(messageObject));
        jsonObject.put("max_tokens", 2000);
        jsonObject.put("temperature", 0);

        // Create the request body
        RequestBody body = RequestBody.create(
                MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://api.openai.com/v1/chat/completions")
                .post(body)
                .addHeader("Authorization", "Bearer " + apiKey)
                .build();

        // Execute the request
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

        // Parse the response
        assert response.body() != null;
        return new JSONObject(response.body().string());
    }

    @Transactional
    public String getSchemaDetails() {
        String schemaQuery =
                "SELECT C.TABLE_NAME, C.COLUMN_NAME, C.DATA_TYPE, " +
                        "CASE WHEN TC.CONSTRAINT_TYPE = 'PRIMARY KEY' THEN 'PK' " +
                        "     WHEN TC.CONSTRAINT_TYPE = 'FOREIGN KEY' THEN 'FK' " +
                        "     ELSE NULL END AS KEY_TYPE, " +
                        "KCU.REFERENCED_TABLE_NAME, KCU.REFERENCED_COLUMN_NAME " +
                        "FROM INFORMATION_SCHEMA.COLUMNS C " +
                        "LEFT JOIN INFORMATION_SCHEMA.KEY_COLUMN_USAGE KCU " +
                        "ON C.TABLE_SCHEMA = KCU.TABLE_SCHEMA " +
                        "AND C.TABLE_NAME = KCU.TABLE_NAME " +
                        "AND C.COLUMN_NAME = KCU.COLUMN_NAME " +
                        "LEFT JOIN INFORMATION_SCHEMA.TABLE_CONSTRAINTS TC " +
                        "ON TC.CONSTRAINT_SCHEMA = KCU.CONSTRAINT_SCHEMA " +
                        "AND TC.TABLE_NAME = KCU.TABLE_NAME " +
                        "AND TC.CONSTRAINT_NAME = KCU.CONSTRAINT_NAME " +
                        "WHERE C.TABLE_SCHEMA = 'iAssure_Dev' " +
                        "ORDER BY C.TABLE_NAME, C.ORDINAL_POSITION";

        List<Object[]> schemaData = entityManager.createNativeQuery(schemaQuery).getResultList();
        StringBuilder schemaDetails = new StringBuilder("You are a SQL Query Generator. The database has the following schemas:\n");

        String currentTable = "";
        for (Object[] row : schemaData) {
            String tableName = (String) row[0];
            String columnName = (String) row[1];
            String dataType = (String) row[2];
            String keyType = row[3] != null ? (String) row[3] : "";
            String referencedTable = row[4] != null ? (String) row[4] : "";
            String referencedColumn = row[5] != null ? (String) row[5] : "";

            if (!tableName.equals(currentTable)) {
                schemaDetails.append("\n- ").append(tableName).append(": ");
                currentTable = tableName;
            }

            schemaDetails.append(columnName).append(" (").append(dataType).append(")");

            // Add key type if it exists
            if ("PK".equals(keyType)) {
                schemaDetails.append(" [Primary Key]");
            } else if ("FK".equals(keyType)) {
                schemaDetails.append(" [Foreign Key -> ").append(referencedTable).append(".").append(referencedColumn).append("]");
            }

            schemaDetails.append(", ");
        }

        // Clean up the last comma and format the result
        return schemaDetails.toString().replaceAll(", $", ".\n");
    }

    public  List<float[]> getEmbeddings(List<String> texts) throws IOException {
        List<float[]> embeddings = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();

        for (String text : texts) {
            RequestBody body = RequestBody.create(
                    new JSONObject().put("input", text).put("model", "text-embedding-ada-002").toString(),
                    MediaType.parse("application/json")
            );

            Request request = new Request.Builder()
                    .url("https://api.openai.com/v1/embeddings")
                    .post(body)
                    .addHeader("Authorization", "Bearer " + apiKey)
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                JSONObject jsonResponse = new JSONObject(response.body().string());
                JSONArray embeddingArray = jsonResponse.getJSONArray("data").getJSONObject(0).getJSONArray("embedding");

                float[] embedding = new float[embeddingArray.length()];
                for (int i = 0; i < embeddingArray.length(); i++) {
                    embedding[i] = embeddingArray.getFloat(i);
                }
                embeddings.add(embedding);
            }
            response.close();
        }
        return embeddings;
    }

}

