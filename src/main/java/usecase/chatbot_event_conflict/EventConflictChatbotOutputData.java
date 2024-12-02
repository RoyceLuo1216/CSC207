package usecase.chatbot_event_conflict;

/**
 * Output Data for the Signup Use Case.
 */
public class EventConflictChatbotOutputData {

    private final String response;

    private final boolean useCaseFailed;

    public EventConflictChatbotOutputData(String response, boolean useCaseFailed) {
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
