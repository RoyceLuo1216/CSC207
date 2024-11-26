package usecase.chatbot_event_conflict;

/**
 * The output boundary for the Chatbot Event Conflict Use Case.
 */
public interface EventConflictOutputBoundary {

    // TODO: connect to fixed event view to create a fixed task
//    /**
//     * Prepares the chatbot view for the Fixed Event Use Case.
//     * @param outputData the output data
//     */
//    void prepareFixedEventView(ChatbotOutputData outputData);


    /**
     * Switches back to the Main View.
     */
    void backToMainView();
}
