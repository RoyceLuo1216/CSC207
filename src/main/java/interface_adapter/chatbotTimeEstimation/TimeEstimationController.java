package interface_adapter.chatbotTimeEstimation;

import usecase.chatbot_time_estimation.ChatbotInputData;
import usecase.chatbot_time_estimation.TimeEstimationInputBoundary;

/**
 * The Controller for the Chatbot Time Estimation Use Case.
 */
public class TimeEstimationController {

    private final TimeEstimationInputBoundary timeEstimationInteractor;

    public TimeEstimationController(TimeEstimationInputBoundary timeEstimationInteractor) {
        this.timeEstimationInteractor = timeEstimationInteractor;
    }

    /**
     * Executes the Chatbot Time Estimation Use Case.
     * @param query: A string query from user
     */
    public void execute(String query) {
        System.out.println("EventConflictController.execute(" + query + ")");
        final ChatbotInputData chatbotInputData = new ChatbotInputData(query);

        timeEstimationInteractor.execute(chatbotInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView(){
        timeEstimationInteractor.backToMainView();
    }
}
