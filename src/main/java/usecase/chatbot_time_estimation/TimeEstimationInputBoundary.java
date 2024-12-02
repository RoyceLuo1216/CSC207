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
     * Switch back to schedule view.
     */
    void backToSchedule();
}
