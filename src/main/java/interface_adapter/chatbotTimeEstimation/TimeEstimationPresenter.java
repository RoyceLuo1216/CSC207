package interface_adapter.chatbotTimeEstimation;

import interface_adapter.ViewManagerModel;
import usecase.chatbot_time_estimation.ChatbotOutputData;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;

/**
 * The Presenter for the Chatbot Time Estimation Use Case.
 */
public class TimeEstimationPresenter implements TimeEstimationOutputBoundary {
    private final ChatbotViewModel chatbotViewModel;
    private final ViewManagerModel viewManagerModel;

    public TimeEstimationPresenter(ViewManagerModel viewManagerModel, ChatbotViewModel chatbotViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatbotViewModel = chatbotViewModel;
    }

    @Override
    public void prepareSuccessView(ChatbotOutputData outputData) {
        // On success, send the response to the chatbot view.
        final ChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponse(outputData.getTimeEstimation());
        System.out.println("chatbotState.setResponse(" + outputData.getTimeEstimation() + ")");
        this.chatbotViewModel.setState(chatbotState);
        chatbotViewModel.firePropertyChanged();

        viewManagerModel.setState(chatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final ChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponseError(error);
        chatbotViewModel.firePropertyChanged();
    }

    @Override
    public void backToMainView() {
        viewManagerModel.setState(chatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
