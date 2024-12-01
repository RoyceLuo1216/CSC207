package usecase.chatbot_time_estimation;

/**
 * Input Boundary for actions which are related to asking chatbot for time estimation.
 */
public interface TimeEstimationInputBoundary {
    /**
     * Executes the chatbotEventConflict use case.
     * @param chatbotInputData the input data
     */
    void execute(ChatbotInputData chatbotInputData);

    /**
     * Returns the back to main schedule view.
     */
    void backToMainView();
}
