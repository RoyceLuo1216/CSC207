package interface_adapter.chatbotTimeEstimation;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Chatbot View.
 */
public class TimeEstimationChatbotViewModel extends ViewModel<TimeEstimationChatbotState> {
    // TODO: NEED TO CLARIFY HOW TO SEPARATE THE CHATBOTVIEW MODEL WITH ELAINE
    public static final String TITLE_LABEL = "Schedule Chatbot View";
    public static final String[] CHAT_INTRO = {
        "Welcome to the time estimation chatbot! Ask me how long something might take.",
        "I can help you estimate time for a task, and inform you about scheduling conflicts!",
        "Press ENTER or the 'Ask!' button to ask a question. :)",
    };
    public static final String ASK_LABEL = "Ask me about your schedule:";

    public static final String ASK_BUTTON_LABEL = "Ask!";
    public static final String BACK_BUTTON_LABEL = "Back";

    public static final String EMPTY_QUESTION_ERROR = "[Please type a question.]";

    public static final String CHATBOT_NAME_LABEL = "Chatbot: ";
    public static final String USER_NAME_LABEL = "You: ";

    public TimeEstimationChatbotViewModel() {
        super("chatbot");
        setState(new TimeEstimationChatbotState());
    }

}

