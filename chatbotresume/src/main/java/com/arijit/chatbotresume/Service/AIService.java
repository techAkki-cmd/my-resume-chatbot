package com.arijit.chatbotresume.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AIService {

    // WARNING: Hardcoding API keys in code is insecure and should only be used for testing/demo.
    private static final String GEMINI_API_KEY = "AIzaSyCn92vJsDygvOgdne4ftbq6Oml-jGeiw5k";

    // Adjust model name or version here if needed
    private static final String GEMINI_ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.0-flash:generateContent?key="
                    + GEMINI_API_KEY;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AIService() {
        this.webClient = WebClient.builder()
                .baseUrl(GEMINI_ENDPOINT)
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Build a valid JSON request using Jackson to avoid any malformed JSON errors
     * from user input containing quotes, newlines, etc.
     */
    public String getAIResponse(String userPrompt) {
        try {
            // 1) Create the JSON structure:
            // {
            //   "contents": [
            //     {
            //       "parts": [
            //         { "text": "userPrompt" }
            //       ]
            //     }
            //   ]
            // }

            // Root object
            ObjectNode root = objectMapper.createObjectNode();

            // contents array
            ArrayNode contentsArray = objectMapper.createArrayNode();
            ObjectNode contentObj = objectMapper.createObjectNode();

            // parts array
            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode textObj = objectMapper.createObjectNode();
            textObj.put("text", userPrompt); // Jackson escapes special chars automatically

            // Build hierarchy
            partsArray.add(textObj);
            contentObj.set("parts", partsArray);
            contentsArray.add(contentObj);
            root.set("contents", contentsArray);

            // Convert to pretty-printed JSON string (or use writeValueAsString(root))
            String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);

            // 2) Send to Gemini endpoint, logging errors if any
            String response = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody) // the safe, Jackson-built JSON
                    .retrieve()
                    .onStatus(
                            // if status is 4xx or 5xx
                            httpStatus -> httpStatus.isError(),
                            clientResponse -> clientResponse
                                    .bodyToMono(String.class)
                                    .flatMap(errorBody -> {
                                        System.err.println("Gemini API returned error body: " + errorBody);
                                        return Mono.error(new RuntimeException("API error: " + errorBody));
                                    })
                    )
                    .bodyToMono(String.class)
                    .block();

            return (response != null) ? response : "No response from Gemini.";
        } catch (Exception e) {
            e.printStackTrace();
            return "Error calling Gemini API: " + e.getMessage();
        }
    }
}