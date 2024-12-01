package interface_adapter.chatbot_event_conflict;

import usecase.chatbot_event_conflict.ChatbotInputData;
import usecase.chatbot_event_conflict.EventConflictInputBoundary;

/**
 * The Controller for the Chatbot Event Conflict Use Case.
 */
public class EventConflictController {

    private final EventConflictInputBoundary eventConflictUseCaseInteractor;

    public EventConflictController(EventConflictInputBoundary eventConflictUseCaseInteractor) {
        this.eventConflictUseCaseInteractor = eventConflictUseCaseInteractor;
    }

    /**
     * Executes the Chatbot Event Conflict Use Case.
     * @param question the username to sign up
     */
    public void execute(String question) {
        System.out.println("EventConflictController.execute(" + question + ")");
        final ChatbotInputData chatbotInputData = new ChatbotInputData(question);

        eventConflictUseCaseInteractor.execute(chatbotInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView() {
        eventConflictUseCaseInteractor.backToMainView();
    }
}
