package adapter;

import com.cohere.api.Cohere;
import io.github.cdimascio.dotenv.Dotenv;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;

import java.time.LocalDateTime;
import java.util.Optional;

public class CohereClient {
    private final Cohere cohere;

    public CohereClient() {
        // Load API key from .env
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COHERE_API_KEY");
        // System.out.println("API Key: " + apiKey);  // Check if the API key is loaded correctly

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing or invalid.");
        }

        this.cohere = Cohere.builder().token(apiKey).clientName("scheduling-app").build();
    }

    /**
     * Sends a request to Cohere to extract the start and end time of an event conflict inquiry.
     *
     * Prompt: Extract and return only 2 strings in ISO-8601 format separated by a comma representing the start time
     * and end time of the event described below. If the date is not specified, assume the current time of "now".
     * If there is no time/day mentioned at all, return: eNoTimeSpecified. Event description: "description"
     *
     * Example response from cohere with the question "I want an event on wednesday 6pm to 9pm":
     * "2024-05-15T18:00:00, 2024-05-15T21:00:00"
     *
     * @param userChatInput the user input into the chat
     * @return a string response from Cohere or null indicating error with API call
     */
    public Optional<String> getTimePeriodForEventConflict(String userChatInput) {
        LocalDateTime now = LocalDateTime.now();
        String prompt = "Extract and return only 2 strings in ISO-8601 format separated by a comma representing the " +
                "start time and end time of the event described below. If the date is not specified, assume the " +
                "current time of, " + now + ". If there no time/day mentioned at all, return: eNoTimeSpecified." +
                "Event description:" + userChatInput;

        try {
            NonStreamedChatResponse response = this.cohere.chat(
                    ChatRequest.builder()
                            .message(prompt).build());

            if (response != null && response.getText() != null) {
                System.out.println("Cohere response: " + response.getText());
                return Optional.of(response.getText());
            } else {
                System.out.println("No valid response received from the API.");
                return Optional.empty();
            }
        } catch (Exception e) {
            System.out.println("Error occurred while making API request: " + e.getMessage());
            return Optional.empty();
        }
    }
}
