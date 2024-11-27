package usecase.chatbot_event_conflict;

import adapter.CohereClient;
import adapter.chatbot_event_conflict.EventConflictPresenter;
import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.ScheduleEntity.Schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * The Chatbot Event Conflict Interactor.
 */
public class EventConflictInteractor implements EventConflictInputBoundary {
//    private final EventConflictOutputBoundary schedulePresenter;
//
//    public EventConflictInteractor(EventConflictOutputBoundary eventConflictOutputBoundary) {
//        this.schedulePresenter = eventConflictOutputBoundary;
//    }

    private final EventConflictOutputBoundary schedulePresenter = new EventConflictPresenter();

    @Override
    public String execute(ChatbotInputData chatbotInputData) {
        // TODO: If success: return response "Yes, your event at [time] can be scheduled without any conflicts"
        //          EXTRA (Create and schedule)

        CohereClient client = new CohereClient();
        LocalDateTime[] timePeriod = client.getTimePeriodForEventConflict(chatbotInputData.getQuestion());

        //// TEST
        Schedule schedule = new Schedule();
        LocalDateTime start = LocalDateTime.of(2024, 11, 27, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 27, 20, 0);

        LocalDateTime start2 = LocalDateTime.of(2024, 11, 27, 15, 0);
        LocalDateTime end2 = LocalDateTime.of(2024, 11, 27, 18, 0);
        //// TEST

        // Creating and adding the FixedEvent from 6 to 8pm
        FixedEvent event = new FixedEvent(start, end, "Naptime", 1);
        schedule.addEvent(event);
        FixedEvent event2 = new FixedEvent(start2, end2, "258 Lab", 1);
        schedule.addEvent(event2);

        ArrayList<String> tasksDuring = getTasksDuring(timePeriod[0], timePeriod[1], schedule);

        if (tasksDuring.isEmpty()) {
            // TODO: change presenter such that output data is changed
            // TODO: EXTRA (Create and schedule the event)
            return schedulePresenter.setResponse("Yes, you can schedule your task from " + Arrays.toString(timePeriod));
        }
        else {
            // TODO: change presenter such that output data is changed
            String[] article = new String[2];
            if (tasksDuring.size() == 1) {
                article[0] = "an event conflict";
                article[1] = "is";
            } else {
                article[0] = "event conflicts";
                article[1] = "are";
            }

            return schedulePresenter.setResponse("You have " + article[0] + ", where " + tasksDuring +
                    " " + article[1] + " already scheduled.");
            // TODO: add error message
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
    public ArrayList<String> getTasksDuring(LocalDateTime start, LocalDateTime end, Schedule schedule) {
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<LocalDateTime> hours = getHourlyIntervals(start, end);

        // Add tasks in time period to a string
        for (LocalDateTime hour : hours) {
            Optional<Event> possibleEvent = schedule.getEventByTime(hour);

            // If the event exists, add it to the tasks list
            if (possibleEvent.isPresent()) {
                Event event = possibleEvent.get();
                if (!events.contains(event)) {
                    events.add(event);
                    // TODO: add times details to the event
                    tasks.add(event.getEventName());  // Add event details to the tasks list
                }
            }
        }
        return tasks;
    }

    /**
     * Generate a list of each hour from start to end (not including end)
     *
     * @param start of interval hour
     * @param end of interval hour
     * @return a list of each hour from start to end in LocalDateTime objects
     */
    public static ArrayList<LocalDateTime> getHourlyIntervals(LocalDateTime start, LocalDateTime end) {
        ArrayList<LocalDateTime> hourlyIntervals = new ArrayList<>();

        // Validate that start is before or equal to end
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before or equal to end time.");
        }

        // Add each hour to the list
        LocalDateTime current = start;
        while (!current.isAfter(end)) {
            hourlyIntervals.add(current);
            current = current.plusHours(1); // Move to the next hour
        }

        hourlyIntervals.remove(hourlyIntervals.size() - 1);  // Remove the end time

        return hourlyIntervals;
    }
}
