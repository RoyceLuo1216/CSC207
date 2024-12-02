package interface_adapter.chatbotTimeEstimation;

import interface_adapter.ViewManagerModel;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.chatbot_time_estimation.ChatbotOutputData;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;

/**
 * The Presenter for the Chatbot Time Estimation Use Case.
 */
public class TimeEstimationPresenter implements TimeEstimationOutputBoundary {
    private final TimeEstimationChatbotViewModel chatbotViewModel;
    private final ScheduleViewModel scheduleViewModel;
    private final ViewManagerModel viewManagerModel;

    public TimeEstimationPresenter(ViewManagerModel viewManagerModel, ScheduleViewModel scheduleViewModel,
                                   TimeEstimationChatbotViewModel chatbotViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.scheduleViewModel = scheduleViewModel;
        this.chatbotViewModel = chatbotViewModel;
    }

    @Override
    public void prepareSuccessView(ChatbotOutputData outputData) {
        // On success, send the response to the chatbot view.
        final TimeEstimationChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponse(outputData.getTimeEstimation());
        System.out.println("chatbotState.setResponse(" + outputData.getTimeEstimation() + ")");
        this.chatbotViewModel.setState(chatbotState);
        chatbotViewModel.firePropertyChanged();

        viewManagerModel.setState(chatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final TimeEstimationChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponseError(error);
        chatbotViewModel.firePropertyChanged();
    }

    /**
     * Switches back to the Main Schedule View.
     */
    @Override
    public void backToScheduleView() {
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }
}
