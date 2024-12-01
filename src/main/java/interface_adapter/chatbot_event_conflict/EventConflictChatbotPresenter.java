package interface_adapter.chatbot_event_conflict;

import interface_adapter.ViewManagerModel;
import usecase.chatbot_event_conflict.ChatbotOutputData;
import usecase.chatbot_event_conflict.EventConflictOutputBoundary;

/**
 * The Presenter for the Chatbot Event Conflict Use Case.
 */
public class EventConflictPresenter implements EventConflictOutputBoundary {
    private final EventConflictChatbotViewModel chatbotViewModel;
    private final ViewManagerModel viewManagerModel;

    public EventConflictPresenter(ViewManagerModel viewManagerModel, EventConflictChatbotViewModel chatbotViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.chatbotViewModel = chatbotViewModel;
    }

    @Override
    public void prepareSuccessView(ChatbotOutputData outputData) {
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
    public void backToMainView() {
        viewManagerModel.setState(chatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
