package services;

import okhttp3.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;

public class GeminiClient {
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent";
    private static final String API_KEY = "AIzaSyA0OHCDF717mmB4lrjwpUTLhQChDJOlMZY";

    private final OkHttpClient client;
    private final ObjectMapper objectMapper;

    public GeminiClient() {
        this.client = new OkHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public String generateText(String prompt) throws IOException {
        String requestBody = "{ \"contents\": [{ \"parts\": [{ \"text\": \"" + prompt + "\" }]}]}";

        Request request = new Request.Builder()
                .url(API_URL + "?key=" + API_KEY)
                .post(RequestBody.create(requestBody, MediaType.get("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            JsonNode jsonNode = objectMapper.readTree(response.body().string());
            return jsonNode.path("candidates").get(0).path("content").path("parts").get(0).path("text").asText();
        }
    }
}
