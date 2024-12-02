package usecase.chatbot_event_conflict;

/**
 * The output boundary for the Chatbot Event Conflict Use Case.
 */
public interface EventConflictChatbotOutputBoundary {

    /**
     * Prepares the success view for the Event Conflict Use Case.
     *
     * @param outputData the output data
     */
    void prepareSuccessView(EventConflictChatbotOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches back to the Main Schedule View.
     */
    void backToMainView();
}
