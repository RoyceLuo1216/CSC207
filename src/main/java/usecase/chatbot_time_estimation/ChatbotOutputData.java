package usecase.chatbot_time_estimation;

/**
 * Output Data for the Chatbot Time Estimation Use Case.
 */
public class ChatbotOutputData {
    private final String timeEstimation;

    public ChatbotOutputData(String timeEstimation) {
        this.timeEstimation = timeEstimation;
    }

    public String getTimeEstimation() {
        return timeEstimation;
    }

}
