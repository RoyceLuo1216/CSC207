package usecase.chatbot_event_conflict;

/**
 * The Input Data for the Chatbot Event Conflict Use Case.
 */
public class ChatbotInputData {
    private final String question;

    public ChatbotInputData(String question) {
        this.question = question;
    }

    String getQuestion() {
        return question;
    }
}
