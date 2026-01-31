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


    private static final String GEMINI_API_KEY = "AIzaSyCZeDrntxOhy753rc71bC8hSBQ8DDxSqLE";


    private static final String GEMINI_ENDPOINT =
            "https://generativelanguage.googleapis.com/v1beta/models/gemini-2.5-flash:generateContent?key="
                    + GEMINI_API_KEY;

    private final WebClient webClient;
    private final ObjectMapper objectMapper;

    public AIService() {
        this.webClient = WebClient.builder()
                .baseUrl(GEMINI_ENDPOINT)
                .build();
        this.objectMapper = new ObjectMapper();
    }


    public String getAIResponse(String userPrompt) {
        try {

            ObjectNode root = objectMapper.createObjectNode();


            ArrayNode contentsArray = objectMapper.createArrayNode();
            ObjectNode contentObj = objectMapper.createObjectNode();


            ArrayNode partsArray = objectMapper.createArrayNode();
            ObjectNode textObj = objectMapper.createObjectNode();
            textObj.put("text", userPrompt); // Jackson escapes special chars automatically


            partsArray.add(textObj);
            contentObj.set("parts", partsArray);
            contentsArray.add(contentObj);
            root.set("contents", contentsArray);

        String requestBody = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);


            String response = webClient.post()
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(

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
