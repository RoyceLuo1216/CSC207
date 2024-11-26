package adapter;

import com.cohere.api.Cohere;
import io.github.cdimascio.dotenv.Dotenv;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;
import view.ChatbotView;

public class CohereClient {
    private final Cohere cohere;

    public CohereClient() {
        // Load API key from .env
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COHERE_API_KEY");
        System.out.println("API Key: " + apiKey);  // Check if the API key is loaded correctly

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing or invalid.");
        }

        this.cohere = Cohere.builder().token(apiKey).clientName("scheduling-app").build();
    }

    /**
     * Sends a request to Cohere to extract the start and end time of an event conflict inquiry.
     *
     * @param userChatInput the user input into the chat
     * @return String response from Cohere or an error message
     */
    public String getTimeForEventConflictWithCohere(String userChatInput) {
        String prompt = "Extract and return only the start time and end time as a list of 2 LocalDateTime " +
                "objects in Java for the event described below. If the date is not specified, assume today's date. " +
                "Event description: " + userChatInput + ".";

        try {
            NonStreamedChatResponse response = this.cohere.chat(
                    ChatRequest.builder()
                            .message(prompt).build());

            if (response != null && response.getText() != null) {
                return response.getText();
            } else {
                return "No valid response received from the API.";
            }
        } catch (Exception e) {
            return "Error occurred while making API request: " + e.getMessage();
        }
    }

}
