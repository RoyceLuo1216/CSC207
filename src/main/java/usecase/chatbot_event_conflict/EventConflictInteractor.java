package usecase.chatbot_event_conflict;

import adapter.CohereClient;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The Chatbot Event Conflict Interactor.
 */
public class EventConflictInteractor implements EventConflictInputBoundary {
    private final EventConflictOutputBoundary schedulePresenter;

    public EventConflictInteractor(EventConflictOutputBoundary eventConflictOutputBoundary) {
        this.schedulePresenter = eventConflictOutputBoundary;
    }

    @Override
    public void execute(ChatbotInputData chatbotInputData) {
        // TODO: If success: return True, return Yes, your event at [time] can be scheduled without any conflicts
        //          EXTRA (Create and schedule)

        CohereClient client = new CohereClient();
        ArrayList<LocalDateTime> timePeriod = client.getTimePeriodForEventConflict(chatbotInputData.getQuestion());

        ArrayList<String> tasksDuring = getTasksDuring(timePeriod.get(0), timePeriod.get(1));

        if (tasksDuring.isEmpty()) { // No tasks in array returned means empty
            // TODO: change presenter such that output data is changed
            // TODO: EXTRA (Create and schedule the event)
            // "yes you can schedule your task at [time]";
        }
        else {
            // TODO: change presenter such that output data is changed
            // "you have an event conflict at [time] where [event name(s)] is already scheduled";
        }
    }

    @Override
    public void backToMainView() {
        schedulePresenter.backToMainView();
    }

    /**
     * @param start of time to be checked for event conflicts
     * @param end of time to be checked for event conflicts
     *
     * @return a list of tasks during the requested time period, if there are none, return an empty list
     */
    private ArrayList<String> getTasksDuring(LocalDateTime start, LocalDateTime end) {
        ArrayList<String> tasks = new ArrayList<>();

        // TODO: Loop through every hour from start to end
        for (int i = 0; i < 5; i++) {
            // TODO: If there tasks, add to them to the list of tasks as a string
            tasks.add("dummy task");
        }
        return tasks;
    }
}
