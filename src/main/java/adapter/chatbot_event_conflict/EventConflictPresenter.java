package adapter.chatbot_event_conflict;

//import adapter.ViewManagerModel;
//import adapter.login.LoginState;
//import adapter.login.LoginViewModel;
import usecase.chatbot_event_conflict.EventConflictOutputBoundary;
import usecase.chatbot_event_conflict.EventConflictOutputData;

/**
 * The Presenter for the Chatbot Event Conflict Use Case.
 */
public class EventConflictPresenter implements EventConflictOutputBoundary {

//    private final SignupViewModel signupViewModel;
//    private final LoginViewModel loginViewModel;
//    private final ViewManagerModel viewManagerModel;

    @Override
    public String setResponse(String outputData) {
        return outputData;
    }

    @Override
    public void backToMainView() {

    }
}

