package adapter;

import com.cohere.api.Cohere;
import io.github.cdimascio.dotenv.Dotenv;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.types.NonStreamedChatResponse;
import view.ChatbotView;

import java.time.LocalDateTime;
import java.util.ArrayList;

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
     * Sends the extracted start and end time of an event conflict inquiry.
     *
     * @param userChatInput the user input into the chat
     * @return list of 2 LocalDateTime objects representing the start and end times
     */
    public ArrayList<LocalDateTime> getTimePeriodForEventConflict(String userChatInput) {
        String timePeriod = getTimePeriodForEventConflictString(userChatInput);
        System.out.println(timePeriod);
        return toLocalDateTimeList(timePeriod);
    }

    /**
     * Sends a request to Cohere to extract the start and end time of an event conflict inquiry.
     *
     * @param userChatInput the user input into the chat
     * @return String response from Cohere of the start and end times or an error message
     */
    public String getTimePeriodForEventConflictString(String userChatInput) {
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

    /**
     * Convert a string into a list of 2 LocalDateTime objects
     * @param textResponse of the Cohere client, should be a string version of a list of 2 LocalDateTime objects
     * @return a list of 2 LocalDateTime objects
     */
    private ArrayList<LocalDateTime> toLocalDateTimeList(String textResponse) {
        // TODO: implement conversion function properly (see what format cohere returns)
        ArrayList<LocalDateTime> localDateTimeList = new ArrayList<>();
        localDateTimeList.add(LocalDateTime.parse(textResponse));
        return localDateTimeList;
    }

}
