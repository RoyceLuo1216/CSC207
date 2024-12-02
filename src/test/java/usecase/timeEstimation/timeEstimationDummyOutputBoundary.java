package usecase.timeEstimation;
import usecase.chatbot_time_estimation.ChatbotOutputData;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;

public class timeEstimationDummyOutputBoundary implements TimeEstimationOutputBoundary {
    /**
     * Prepares the success view for the Time Estimation Use Case.
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(ChatbotOutputData outputData) {
        System.out.println(outputData.getTimeEstimation());
    };

    /**
     * Prepares the failure view for the Time Estimation Use Case.
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        System.out.println(errorMessage);
    }

    /**
     * Switches back to the Main Schedule View.
     */
    @Override
    public void backToSchedule() {
        System.out.println("back to schedule view");
    };
}
