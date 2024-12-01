package usecase.chatbot_event_conflict;

import adapter.CohereClient;
import data_access.InMemoryDataAccessObject;
import entities.eventEntity.Event;
import factory.EventFactory;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

/**
 * The Chatbot Event Conflict Interactor.
 */
public class EventConflictInteractor implements EventConflictInputBoundary {
    private final InMemoryDataAccessObject inMemoryDataAccessObjectDataObject;
    private final EventConflictOutputBoundary eventConflictPresenter;
    private final EventFactory eventFactory;

    public EventConflictInteractor(InMemoryDataAccessObject inMemoryDataAccessObject,
                                   EventConflictOutputBoundary eventConflictOutputBoundary,
                                   EventFactory eventFactory) {
        this.inMemoryDataAccessObjectDataObject = inMemoryDataAccessObject;
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

            Object[][] timePeriodList = toLocalDateTimeList(timePeriod.get());
            DayOfWeek startDay = (DayOfWeek) timePeriodList[0][0];
            LocalTime startTime = (LocalTime) timePeriodList[0][1];
            LocalTime endTime = (LocalTime) timePeriodList[1][1];
            ArrayList<String> tasksDuring = getTasksDuring(startDay, startTime, endTime, inMemoryDataAccessObjectDataObject);

            if (tasksDuring.isEmpty()) {
                // TODO: (Create and schedule the event)
                String[] timePeriodString = toStringTime(startDay, startTime, endTime);
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
    private Object[][] toLocalDateTimeList(String textResponse) {
        String[] stringLocalDateTimeList = textResponse.split(",");

        if (stringLocalDateTimeList.length != 2) {
            throw new IllegalArgumentException("Text response must contain exactly two values.");
        }

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        Object[][] result = new Object[2][2];
        result[0][0] = DayOfWeek.valueOf(stringLocalDateTimeList[0].split("T")[0].toUpperCase());
        result[0][1] = LocalTime.parse(stringLocalDateTimeList[0].split("T")[1], timeFormatter);
        result[1][0] = DayOfWeek.valueOf(stringLocalDateTimeList[1].split("T")[0].toUpperCase());
        result[1][1] = LocalTime.parse(stringLocalDateTimeList[1].split("T")[1], timeFormatter);

        return result;

    }


    /**
     * Finds tasks that occur during the specified day and time period.
     *
     * @param startDay  the start day of the period
     * @param startTime the start time of the period
     * @param endTime   the end time of the period
     * @param inMemoryDataAccessObject  the schedule containing the events
     * @return a list of task descriptions for tasks that occur during the specified period
     */
    public ArrayList<String> getTasksDuring(DayOfWeek startDay, LocalTime startTime,
                                            LocalTime endTime, InMemoryDataAccessObject inMemoryDataAccessObject) {
        ArrayList<String> tasks = new ArrayList<>();
        ArrayList<Event> events = new ArrayList<>();
        ArrayList<LocalTime> hourlyIntervals = getHourlyIntervals(startTime, endTime);

        // Add tasks in the time period to a list
        for (LocalTime hour : hourlyIntervals) {
            Optional<Event> possibleEvent = inMemoryDataAccessObject.getEventByDayAndTime(startDay, hour);

            // If the event exists, add it to the tasks list
            if (possibleEvent.isPresent()) {
                Event event = possibleEvent.get();
                if (!events.contains(event)) {
                    events.add(event);

                    // Add event details to the tasks list
                    String[] taskTime = toStringTime(event.getDayStart(), event.getTimeStart(), event.getTimeEnd());
                    tasks.add(event.getEventName() + ": " + taskTime[0] + " " + taskTime[1] + " - " + taskTime[2]);
                }
            }
        }
        return tasks;
    }


    /**
     * Generate a list of each hour from start to end (not including the exact end time).
     *
     * @param start the start time
     * @param end   the end time
     * @return a list of each hour from start to end in LocalTime objects
     */
    private ArrayList<LocalTime> getHourlyIntervals(LocalTime start, LocalTime end) {
        ArrayList<LocalTime> hourlyIntervals = new ArrayList<>();

        // Validate that start is before or equal to end
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before or equal to end time.");
        }

        // Add each hour to the list
        LocalTime current = start;
        while (!current.isAfter(end)) {
            hourlyIntervals.add(current);
            current = current.plusHours(1); // Move to the next hour
        }

        // Remove the end time if it's included
        if (!hourlyIntervals.isEmpty() && current.equals(end.plusHours(1))) {
            hourlyIntervals.remove(hourlyIntervals.size() - 1);
        }

        return hourlyIntervals;
    }

    /**
     * Converts a day and time range into a readable string array.
     *
     * @param day       the day of the week
     * @param startTime the start time
     * @param endTime   the end time
     * @return a string array with the formatted day, start time, and end time
     */
    private String[] toStringTime(DayOfWeek day, LocalTime startTime, LocalTime endTime) {
        String formattedDay = day.toString().charAt(0) + day.toString().substring(1).toLowerCase();

        // Format times to 12-hour clock with "a.m." and "p.m."
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a"); // 12-hour format

        String formattedStartTime = startTime.format(timeFormatter).toLowerCase();
        String formattedEndTime = endTime.format(timeFormatter).toLowerCase();

        return new String[]{formattedDay, formattedStartTime, formattedEndTime};
    }

    /**
     * Convert two LocalDateTime objects (start and end) into a list of string representations
     * <p>
     * Example output: [Nov 27, 02:30 PM, 04:45 PM]
     *
     * @param start LocalDateTime object
     * @param end   LocalDateTime object
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

        return new String[]{date, startTime, endTime};
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
