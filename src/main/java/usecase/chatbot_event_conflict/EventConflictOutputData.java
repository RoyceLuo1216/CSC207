package usecase.chatbot_event_conflict;

/**
 * Output Data for the Chatbot Event Conflict Use Case.
 */
public class EventConflictOutputData {
    private final String response;

    private final boolean useCaseFailed;

    public EventConflictOutputData(String response, boolean useCaseFailed) {
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
