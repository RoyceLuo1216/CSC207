package usecase.chatbot_event_conflict;

import adapter.CohereClient;
import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.ScheduleEntity.Schedule;
import factory.EventFactory;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

/**
 * The Chatbot Event Conflict Interactor.
 */
public class EventConflictInteractor implements EventConflictInputBoundary {
    private final Schedule scheduleDataObject;
    private final EventConflictOutputBoundary eventConflictPresenter;
    private final EventFactory eventFactory;

    public EventConflictInteractor(Schedule schedule,
                            EventConflictOutputBoundary eventConflictOutputBoundary,
                            EventFactory eventFactory) {
        this.scheduleDataObject = schedule;
        this.eventConflictPresenter = eventConflictOutputBoundary;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(ChatbotInputData chatbotInputData) {
        // TODO: Create and schedule event?
        System.out.println("EventConflictInteractor.execute(chatbotInputData);");
        CohereClient client = new CohereClient();
        Optional<String> timePeriod = client.getTimePeriodForEventConflict(chatbotInputData.getQuestion());


        if (timePeriod.isPresent()) {

            if (timePeriod.get().charAt(0) == 'e') {
                // Error from COHERE:
                eventConflictPresenter.prepareFailView("No time period found");
                System.out.println("Error: No time period found");
                return;
            }

            LocalDateTime[] timePeriodList = toLocalDateTimeList(timePeriod.get());
            ArrayList<String> tasksDuring = getTasksDuring(timePeriodList[0], timePeriodList[1], scheduleDataObject);

            if (tasksDuring.isEmpty()) {
                // TODO: (Create and schedule the event)
                String[] timePeriodString = toStringTime(timePeriodList[0], timePeriodList[1]);
                String response = "Yes, you can schedule your task on " + timePeriodString[0] +
                        " from " + timePeriodString[1] + " to " + timePeriodString[2] + ".";

                final ChatbotOutputData chatbotOutputData = new ChatbotOutputData(response, false);
                eventConflictPresenter.prepareSuccessView(chatbotOutputData);
            } else {
                String[] article = new String[2];
                if (tasksDuring.size() == 1) {
                    article[0] = "event conflict";
                    article[1] = "is";
                } else {
                    article[0] = "event conflicts";
                    article[1] = "are";
                }

                String tasksDuringString = "\n";
                for (String task : tasksDuring) {
                    tasksDuringString += "\t" + task + "\n";
                }
                String response = "You have the following " + article[0] + ": \n" + tasksDuringString;

                final ChatbotOutputData chatbotOutputData = new ChatbotOutputData(response, false);
                eventConflictPresenter.prepareSuccessView(chatbotOutputData);
            }
        } else {
            eventConflictPresenter.prepareFailView("Error occured during API call");
            System.out.println("Error occured during API call");
        }
    }

    @Override
    public void backToMainView() {
        eventConflictPresenter.backToMainView();
    }

    /**
     * Convert a string of two values in ISO-8601 format into a list of 2 LocalDateTime objects
     *
     * @param textResponse of the Cohere client, should be a string version of a list of 2 LocalDateTime objects
     * @return an array of 2 LocalDateTime objects
     */
    private LocalDateTime[] toLocalDateTimeList(String textResponse) {
        String[] stringLocalDateTimeList = textResponse.split(",");

        LocalDateTime[] dateTimes = new LocalDateTime[2];

        dateTimes[0] = LocalDateTime.parse(stringLocalDateTimeList[0]);
        dateTimes[1] = LocalDateTime.parse(stringLocalDateTimeList[1]);

        return dateTimes;
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

                    // Add event details to the tasks list
                    String[] taskTime = toStringTime(event.getDayStart(), event.getDayEnd());
                    tasks.add(event.getEventName() + ": " + taskTime[0] + "  " + taskTime[1] + " - " + taskTime[2]);
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

    /**
     * Convert two LocalDateTime objects (start and end) into a list of string representations
     *
     * Example output: [Nov 27, 02:30 PM, 04:45 PM]
     *
     * @param start LocalDateTime object
     * @param end LocalDateTime object
     * @return an array of string representation (Day, Start, End)
     */
    public String[] toStringTime(LocalDateTime start, LocalDateTime end) {
        // Validate input
        if (start == null || end == null) {
            throw new IllegalArgumentException("Input must be two non-null LocalDateTime objects.");
        }

        // Formatters
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        // Extract and format Date, Start, and End
        String date = start.format(dateFormatter);
        String startTime = start.format(timeFormatter);
        String endTime = end.format(timeFormatter);

        return new String[] { date, startTime, endTime };
    }
    //
    //
    //Methods from non CA code
//    @Override
//    public String execute(ChatbotInputData chatbotInputData) {
//        //          EXTRA (Create and schedule)
//
//        CohereClient client = new CohereClient();
//        LocalDateTime[] timePeriod = client.getTimePeriodForEventConflict(chatbotInputData.getQuestion());
//
//        //// TEST
//        Schedule schedule = new Schedule();
//        LocalDateTime start = LocalDateTime.of(2024, 11, 27, 18, 0);
//        LocalDateTime end = LocalDateTime.of(2024, 11, 27, 20, 0);
//
//        LocalDateTime start2 = LocalDateTime.of(2024, 11, 27, 15, 0);
//        LocalDateTime end2 = LocalDateTime.of(2024, 11, 27, 18, 0);
//
//        // Creating and adding the FixedEvent from 6 to 8pm
//        FixedEvent event = new FixedEvent(start, end, "Naptime", 1);
//        schedule.addEvent(event);
//        FixedEvent event2 = new FixedEvent(start2, end2, "258 Lab", 1);
//        schedule.addEvent(event2);
//        //// TEST
//
//        ArrayList<String> tasksDuring = getTasksDuring(timePeriod[0], timePeriod[1], scheduleDataObject);
//
//        if (tasksDuring.isEmpty()) {
//            String[] timePeriodString = toStringTime(timePeriod[0], timePeriod[1]);
//            return eventConflictPresenter.setResponse("Yes, you can schedule your task on " + timePeriodString[0] +
//                    " from " + timePeriodString[1] + " to " + timePeriodString[2] + ".");
//        }
//        else {
//            String[] article = new String[2];
//            if (tasksDuring.size() == 1) {
//                article[0] = "event conflict";
//                article[1] = "is";
//            } else {
//                article[0] = "event conflicts";
//                article[1] = "are";
//            }
//
//            String tasksDuringString = "\n";
//
//            for (String task : tasksDuring) {
//                tasksDuringString += "\t" + task + "\n";
//            }
//
//            return eventConflictPresenter.setResponse("You have the following " + article[0] + ": \n" + tasksDuringString);
//        }
//    }




}
