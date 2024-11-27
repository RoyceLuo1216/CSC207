package view;

import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.ChatbotInputData;
import usecase.chatbot_event_conflict.EventConflictInteractor;

/**
 * The Controller for the Chatbot Event Conflict Use Case.
 */
public class EventConflictController {

    // private final EventConflictInputBoundary eventConflictUseCaseInteractor;

//    public EventConflictController(EventConflictInputBoundary eventConflictUseCaseInteractor) {
//        this.eventConflictUseCaseInteractor = eventConflictUseCaseInteractor;
//    }

    EventConflictInputBoundary eventConflictUseCaseInteractor = new EventConflictInteractor();

    /**
     * Executes the Chatbot Event Conflict Use Case.
     * @param question the username to sign up
     */
    public String execute(String question) {
        final ChatbotInputData chatbotInputData = new ChatbotInputData(question);

        return eventConflictUseCaseInteractor.execute(chatbotInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView(){
        System.out.println("back to main view function called");
    }
}
