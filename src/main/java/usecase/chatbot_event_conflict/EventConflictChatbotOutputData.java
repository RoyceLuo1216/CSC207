package usecase.chatbot_event_conflict;

/**
 * Output Data for the Signup Use Case.
 */
public class ChatbotOutputData {

    private final String response;

    private final boolean useCaseFailed;

    public ChatbotOutputData(String response, boolean useCaseFailed) {
        this.response = response;
        this.useCaseFailed = useCaseFailed;
    }

    public String getResponse() {
        return response;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}
