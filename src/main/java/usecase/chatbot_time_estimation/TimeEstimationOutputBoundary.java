package usecase.chatbot_time_estimation;

/**
 * The output boundary for the Chatbot Time Estimation Use Case.
 */
public interface TimeEstimationOutputBoundary {

    /**
     * Prepares the success view for the Time Estimation Use Case.
     * @param outputData the output data
     */
    void prepareSuccessView(ChatbotOutputData outputData);

    /**
     * Prepares the failure view for the Time Estimation Use Case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches back to the Main Schedule View.
     */
    void backToSchedule();
}
