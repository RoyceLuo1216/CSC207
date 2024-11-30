package usecase.chatbot_time_estimation;

/**
 * The Input Data for the Chatbot Time Estimation Use Case.
 */
public class ChatbotInputData {
    private final String query;

    public ChatbotInputData(String query) {
        this.query = query;
    }

    public String getQuery() {
        return query;
    }
}
