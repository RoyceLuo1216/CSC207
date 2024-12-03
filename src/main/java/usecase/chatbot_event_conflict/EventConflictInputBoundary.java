package usecase.chatbot_event_conflict;

/**
 * Input Boundary for actions which are related to asking chatbot for event conflicts.
 */
public interface EventConflictInputBoundary {
    /**
     * Executes the chatbotEventConflict use case.
     *
     * @param chatbotInputData the input data
     */
    void execute(ChatbotInputData chatbotInputData);

    /**
     * Returns the back to main schedule view.
     */
    void backToScheduleView();
}
