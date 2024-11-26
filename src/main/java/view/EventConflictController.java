package view;

import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.ChatbotInputData;

/**
 * The Controller for the Chatbot Event Conflict Use Case.
 */
public class EventConflictController {
    // private String question;

    private final EventConflictInputBoundary eventConflictUseCaseInteractor;

    public EventConflictController(EventConflictInputBoundary eventConflictUseCaseInteractor) {
        this.eventConflictUseCaseInteractor = eventConflictUseCaseInteractor;
    }

//    /**
//     * Method that takes user inputted question and assigns each to a corresponding instance variable.
//     * @param question  user asked
//     */
//    public void askQuestion(String question) {
//        this.question = question;
//    }
//
//    public String getQuestion() {
//        return question;
//    }
//
//    /**
//     * Prints the user inputted question.
//     */
//    public void printQuestion(){
//        System.out.println("Question: " + getQuestion());
//    }

    /**
     * Executes the Chatbot Event Conflict Use Case.
     * @param question the username to sign up
     */
    public void execute(String question) {
        final ChatbotInputData chatbotInputData = new ChatbotInputData(
                question);

        eventConflictUseCaseInteractor.execute(chatbotInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView(){
        System.out.println("back to main view function called");
    }
}
