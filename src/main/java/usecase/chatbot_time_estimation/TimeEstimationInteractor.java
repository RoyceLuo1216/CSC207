package usecase.chatbot_time_estimation;
import adapter.CohereClient;
import java.util.Optional;

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
        String query = chatbotInputData.getQuery();

        Optional<String> timeEstimation = client.timeAllocationWithCohere(query);

        if (timeEstimation.isPresent()) {
            if (timeEstimation.get().charAt(0) == 'e') {
                // Error from COHERE:
                outputBoundary.prepareFailView("No time estimation found");
                System.out.println("Error: No time estimation found");
                return;
            }
            String finalTimeEstimation = timeEstimation.get();

            ChatbotOutputData output = new ChatbotOutputData(finalTimeEstimation);

            outputBoundary.prepareSuccessView(output);
        } else {
            outputBoundary.prepareFailView("Error occured during API call");
            System.out.println("Error occured during API call");
        }


    }

    @Override
    public void backToMainView() {
        outputBoundary.backToMainView();
    }

}
