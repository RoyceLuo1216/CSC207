package adapter;

import com.cohere.api.Cohere;
import io.github.cdimascio.dotenv.Dotenv;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;
import view.ChatbotView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

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
     * Example response from cohere with the question: "I want an event on wednesday 6pm to 9pm"
     * "2024-05-15T18:00:00, 2024-05-15T21:00:00"
     *
     * @param userChatInput the user input into the chat
     * @return an array of 2 LocalDateTime objects from Cohere or null indicating error
     */
    public LocalDateTime[] getTimePeriodForEventConflict(String userChatInput) {
        LocalDateTime now = LocalDateTime.now();
        String prompt = "Extract and return only 2 strings in ISO-8601 format separated by a comma representing the " +
                "start time and end time of the event described below. If the date is not specified, assume the " +
                "current time of, " + now + ". Event description:" + userChatInput;

        try {
            NonStreamedChatResponse response = this.cohere.chat(
                    ChatRequest.builder()
                            .message(prompt).build());

            if (response != null && response.getText() != null) {
                System.out.println(response.getText());
                return toLocalDateTimeList(response.getText());
            } else {
                System.out.println("No valid response received from the API.");
                return null;
            }
        } catch (Exception e) {
            System.out.println("Error occurred while making API request: " + e.getMessage());
            return null;
        }
    }

    /**
     * Convert a string of two values in ISO-8601 format into a list of 2 LocalDateTime objects
     *
     * @param textResponse of the Cohere client, should be a string version of a list of 2 LocalDateTime objects
     * @return an array of 2 LocalDateTime objects
     */
    private LocalDateTime[] toLocalDateTimeList(String textResponse) {
        String[] stringLocalDateTimeList = textResponse.split(",");

        LocalDateTime[] dateTimes = new LocalDateTime[2];

        dateTimes[0] = LocalDateTime.parse(stringLocalDateTimeList[0]);
        dateTimes[1] = LocalDateTime.parse(stringLocalDateTimeList[1]);

        return dateTimes;
    }

}
