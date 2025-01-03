package adapter;

import java.time.LocalDateTime;
import java.util.Optional;

import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;
import io.github.cdimascio.dotenv.Dotenv;

/**
 * Cohere Client that calls the cohere api.
 */
public class CohereClient {
    private final Cohere cohere;

    public CohereClient() {
        // Load API key from .env
        final Dotenv dotenv = Dotenv.load();
        final String apiKey = dotenv.get("COHERE_API_KEY");
        // System.out.println("API Key: " + apiKey);  // Check if the API key is loaded correctly

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing or invalid.");
        }

        this.cohere = Cohere.builder().token(apiKey).clientName("scheduling-app").build();
    }

    /**
     * Sends a request to Cohere to extract the start and end time of an event conflict inquiry.
     * Prompt: Extract and return only 2 strings in ISO-8601 format separated by a comma representing the start time
     * and end time of the event described below. If the date is not specified, assume the current time of "now".
     * If there is no time/day mentioned at all, return: eNoTimeSpecified. Event description: "description"
     * Example response from cohere with the question "I want an event on wednesday 6pm to 9pm":
     * "2024-05-15T18:00:00, 2024-05-15T21:00:00"
     *
     * @param userChatInput the user input into the chat
     * @return a string response from Cohere or null indicating error with API call
     */
    public Optional<String> getTimePeriodForEventConflict(String userChatInput) {
        final LocalDateTime now = LocalDateTime.now();
        final String prompt = "Extract and return only 2 strings in ISO-8601 format separated by a "
                + "comma representing the "
                + "start time and end time of the event described below. If the date is not specified, assume the "
                + "current time of, " + now + ". If there no time/day mentioned at all, return: eNoTimeSpecified."
                + "Event description:" + userChatInput;

        try {
            final NonStreamedChatResponse response = this.cohere.chat(
                    ChatRequest.builder()
                            .message(prompt).build());

            if (response != null && response.getText() != null) {
                System.out.println("Cohere response: " + response.getText());
                return Optional.of(response.getText());
            }
            else {
                System.out.println("No valid response received from the API.");
                return Optional.empty();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while making API request: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Sends a request to Cohere to process time estimation.
     * String prompt = "Give me a time estimate on how long it would take to \" " + userQuery + " \".
     * Return only the time " + "estimate and nothing else.
     * If the given user prompt does not make sense, return \"invalid user prompt! \" ";
     * Example response from cohere with the question "Eat dinner and go on a walk":
     * "30 minutes"
     *
     * @param userQuery the formatted event information string
     * @return String response from Cohere
     *
     */
    public Optional<String> timeAllocationWithCohere(String userQuery) {
        final String prompt = "Give me a time estimate on how long it would take to \" "
                + userQuery + " \". Return only the time "
                + "estimate and nothing else. If the given user prompt is not relevant or does not make sense, return "
                + "\"invalid user prompt! \" ";
        try {
            final NonStreamedChatResponse response = this.cohere.chat(
                    ChatRequest.builder()
                            .message(prompt).build());

            if (response != null && response.getText() != null) {
                System.out.println("Cohere response: " + response.getText());
                return Optional.of(response.getText());
            }
            else {
                System.out.println("No valid response received from the API.");
                return Optional.empty();
            }
        }
        catch (Exception e) {
            System.out.println("Error occurred while making API request: " + e.getMessage());
            return Optional.empty();
        }
    }

}
