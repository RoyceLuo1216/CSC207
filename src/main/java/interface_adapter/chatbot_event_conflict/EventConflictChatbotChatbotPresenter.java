package interface_adapter.chatbot_event_conflict;

import interface_adapter.ViewManagerModel;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.chatbot_event_conflict.EventConflictChatbotOutputBoundary;
import usecase.chatbot_event_conflict.EventConflictChatbotOutputData;

/**
 * The Presenter for the Chatbot Event Conflict Use Case.
 */
public class EventConflictChatbotChatbotPresenter implements EventConflictChatbotOutputBoundary {
    private final EventConflictChatbotViewModel chatbotViewModel;
    private final ScheduleViewModel scheduleViewModel;
    private final ViewManagerModel viewManagerModel;

    public EventConflictChatbotChatbotPresenter(ViewManagerModel viewManagerModel, ScheduleViewModel scheduleViewModel,
                                                EventConflictChatbotViewModel chatbotViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.scheduleViewModel = scheduleViewModel;
        this.chatbotViewModel = chatbotViewModel;
    }

    @Override
    public void prepareSuccessView(EventConflictChatbotOutputData outputData) {
        // On success, send the response to the chatbot view.
        final EventConflictChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponse(outputData.getResponse());
        System.out.println("chatbotState.setResponse(" + outputData.getResponse() + ")");
        this.chatbotViewModel.setState(chatbotState);
        chatbotViewModel.firePropertyChanged();

        viewManagerModel.setState(chatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareFailView(String error) {
        final EventConflictChatbotState chatbotState = chatbotViewModel.getState();
        chatbotState.setResponseError(error);
        chatbotViewModel.firePropertyChanged();
    }

    @Override
    public void backToScheduleView() {
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }
}
