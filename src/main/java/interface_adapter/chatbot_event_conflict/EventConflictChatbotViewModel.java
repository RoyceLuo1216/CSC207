package interface_adapter.chatbot_event_conflict;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Chatbot View.
 */
public class EventConflictChatbotViewModel extends ViewModel<EventConflictChatbotState> {

    public static final String TITLE_LABEL = "Event Conflict Chatbot View";
    public static final String[] CHAT_INTRO = {
        "Welcome to the schedule chatbot! Ask me about your schedule.",
        "I can help you schedule events, estimate time for a task, and inform you about scheduling conflicts!",
        "Press ENTER or the 'Ask!' button to ask a question. :)",
    };
    public static final String ASK_LABEL = "Ask me about your schedule:";

    public static final String ASK_BUTTON_LABEL = "Ask!";
    public static final String BACK_BUTTON_LABEL = "Back";

    public static final String EMPTY_QUESTION_ERROR = "[Please type a question.]";

    public static final String CHATBOT_NAME_LABEL = "Chatbot: ";
    public static final String USER_NAME_LABEL = "You: ";

    public EventConflictChatbotViewModel() {
        super("chatbot");
        setState(new EventConflictChatbotState());
    }

}

