package usecase.chatbot_time_estimation;

import java.util.Optional;

import adapter.CohereClient;

/**
 * The Chatbot Time Estimation Interactor.
 */
public class TimeEstimationInteractor implements TimeEstimationInputBoundary {
    private final TimeEstimationOutputBoundary outputBoundary;
    private final CohereClient client;

    public TimeEstimationInteractor(TimeEstimationOutputBoundary outputBoundary) {
        this.outputBoundary = outputBoundary;
        this.client = new CohereClient();
    }

    @Override
    public void execute(ChatbotInputData chatbotInputData) {
        final String query = chatbotInputData.getQuery();
        final Optional<String> timeEstimation = client.timeAllocationWithCohere(query);
        if (timeEstimation.get().charAt(0) != 'i') {
            final String finalTimeEstimation = timeEstimation.get();
            final ChatbotOutputData output = new ChatbotOutputData(finalTimeEstimation);
            outputBoundary.prepareSuccessView(output);
        }
        else {
            // Error from COHERE:
            outputBoundary.prepareFailView("No time estimation found");
            System.out.println("Error: No time estimation found");
        }
    }

    /**
     * Switch back to schedule view.
     */
    @Override
    public void backToScheduleView() {
        outputBoundary.backToScheduleView();
    }

}
