package adapter;

import com.cohere.api.Cohere;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.ChatMessageV2;
import com.cohere.api.types.ChatResponse;
import com.cohere.api.types.UserMessage;
import com.cohere.api.types.UserMessageContent;
import io.github.cdimascio.dotenv.Dotenv;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CohereClient {
    private final Cohere cohere;

    public CohereClient() {
        // Load API key from .env
        Dotenv dotenv = Dotenv.load();
        String apiKey = dotenv.get("COHERE_API_KEY");
        this.cohere = Cohere.builder().token(apiKey).clientName("scheduling-app").build();
    }

    public static void main(String[] args) {
        CohereClient client = new CohereClient();

        // Define event information prompt here
        String eventInfo = "Fixed Event: MAT237 Midterm on 2024-11-09 from 18:00 to 20:00, Recurring Event: Sleep daily from 00:00 to 08:00, Flexible Event: Stats Studying needing 10 hours from 2024-11-09 to 2024-11-12.";

        // Get the schedule JSON from Cohere
        JSONObject scheduleJson = client.scheduleWithCohere(eventInfo);

        // Save to JSON file for OR-Tools use
        client.saveScheduleToFile(scheduleJson, "schedule.json");
    }

    /**
     * Sends a request to Cohere to process scheduling.
     *
     * @param eventInfo the formatted event information string
     * @return the response from Cohere as a structured JSON
     */
    public JSONObject scheduleWithCohere(String eventInfo) {
        ChatResponse response =
                cohere.v2()
                        .chat(
                                V2ChatRequest.builder()
                                        .model("command-r-plus")
                                        .messages(
                                                List.of(
                                                        ChatMessageV2.user(
                                                                UserMessage.builder()
                                                                        .content(
                                                                                UserMessageContent.of(eventInfo))
                                                                        .build())))
                                        .build());

        // Extract text response from Cohere
        String responseText = String.valueOf(response.getMessage());

        // Parse the response text to JSON format (assuming JSON response is provided)
        return parseResponseToJSON(responseText);
    }

    /**
     * Parses the response text from Cohere into a structured JSON format for flexible scheduling.
     * This method assumes the response is formatted in a structured way for parsing.
     *
     * @param responseText the response text from Cohere
     * @return a JSON object containing the structured schedule
     */
    private JSONObject parseResponseToJSON(String responseText) {
        JSONObject scheduleJson = new JSONObject();
        JSONArray eventsArray = new JSONArray();

        // Here, we'll mock the parsed response assuming Cohere returns details in a readable way.
        // Example parsing logic for demonstration purposes:
        String[] lines = responseText.split("\n");
        for (String line : lines) {
            if (line.contains("Event:")) {
                JSONObject eventJson = new JSONObject();
                String[] parts = line.split(", ");

                for (String part : parts) {
                    if (part.contains("Event Name:")) {
                        eventJson.put("event_name", part.split(":")[1].trim());
                    } else if (part.contains("Start Time:")) {
                        eventJson.put("start_time", part.split(":")[1].trim());
                    } else if (part.contains("End Time:")) {
                        eventJson.put("end_time", part.split(":")[1].trim());
                    } else if (part.contains("Type:")) {
                        eventJson.put("type", part.split(":")[1].trim());
                    }
                }
                eventsArray.put(eventJson);
            }
        }

        scheduleJson.put("schedule", eventsArray);
        return scheduleJson;
    }

    /**
     * Saves the structured schedule JSON to a file.
     *
     * @param scheduleJson The JSON object representing the schedule
     * @param filename     The filename where the schedule will be saved
     */
    public void saveScheduleToFile(JSONObject scheduleJson, String filename) {
        try (FileWriter file = new FileWriter(filename)) {
            file.write(scheduleJson.toString(4)); // Pretty-print with indentation
            file.flush();
            System.out.println("Schedule saved to " + filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
