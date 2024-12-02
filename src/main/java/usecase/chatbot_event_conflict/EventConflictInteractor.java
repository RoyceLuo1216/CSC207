package usecase.chatbot_event_conflict;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

import adapter.CohereClient;
import entities.eventEntity.Event;
import entities.eventEntity.EventFactory;

/**
 * The Chatbot Event Conflict Interactor.
 */
public class EventConflictInteractor implements EventConflictInputBoundary {
    private final EventConflictChatbotDataAccessInterface inMemoryDataAccessObjectDataObject;
    private final EventConflictChatbotOutputBoundary eventConflictPresenter;
    private final EventFactory eventFactory;

    public EventConflictInteractor(EventConflictChatbotDataAccessInterface inMemoryDataAccessObject,
                                   EventConflictChatbotOutputBoundary eventConflictChatbotOutputBoundary,
                                   EventFactory eventFactory) {
        this.inMemoryDataAccessObjectDataObject = inMemoryDataAccessObject;
        this.eventConflictPresenter = eventConflictChatbotOutputBoundary;
        this.eventFactory = eventFactory;
    }

    @Override
    public void execute(ChatbotInputData chatbotInputData) {
        System.out.println("EventConflictInteractor.execute(chatbotInputData);");
        final CohereClient client = new CohereClient();
        final Optional<String> timePeriod = client.getTimePeriodForEventConflict(chatbotInputData.getQuestion());

        if (timePeriod.isPresent()) {
            if (timePeriod.get().charAt(0) != 'e') {
                final Object[][] timePeriodList = toLocalDateTimeList(timePeriod.get());
                final DayOfWeek startDay = (DayOfWeek) timePeriodList[0][0];
                final LocalTime startTime = (LocalTime) timePeriodList[0][1];
                final LocalTime endTime = (LocalTime) timePeriodList[1][1];
                final ArrayList<String> tasksDuring = getTasksDuring(startDay, startTime, endTime,
                        inMemoryDataAccessObjectDataObject);
                if (tasksDuring.isEmpty()) {
                    // TODO: (Create and schedule the event)
                    final String[] timePeriodString = toStringTime(startDay, startTime, endTime);
                    final String response = "Yes, you can schedule your task on " + timePeriodString[0]
                            + " from " + timePeriodString[1] + " to " + timePeriodString[2] + ".";

                    final EventConflictChatbotOutputData eventConflictChatbotOutputData = new EventConflictChatbotOutputData(response, false);
                    eventConflictPresenter.prepareSuccessView(eventConflictChatbotOutputData);
                }
                else {
                    final String[] article = new String[2];
                    if (tasksDuring.size() == 1) {
                        article[0] = "event conflict";
                        article[1] = "is";
                    }
                    else {
                        article[0] = "event conflicts";
                        article[1] = "are";
                    }

                    String tasksDuringString = "\n";
                    for (String task : tasksDuring) {
                        tasksDuringString += "\t" + task + "\n";
                    }
                    final String response = "You have the following " + article[0] + ": \n" + tasksDuringString;

                    final EventConflictChatbotOutputData eventConflictChatbotOutputData = new EventConflictChatbotOutputData(response, false);
                    eventConflictPresenter.prepareSuccessView(eventConflictChatbotOutputData);
                }
            }
            else {
                // Error from COHERE:
                eventConflictPresenter.prepareFailView("No time period found");
                System.out.println("Error: No time period found");
            }
        }
        else {
            eventConflictPresenter.prepareFailView("Error occured during API call");
            System.out.println("Error occured during API call");
        }
    }

    @Override
    public void backToMainView() {
        eventConflictPresenter.backToMainView();
    }

    /**
     * Convert a string of two values in ISO-8601 format into a list of 2 LocalDateTime objects.
     *
     * @param textResponse of the Cohere client, should be a string version of a list of 2 LocalDateTime objects
     * @return an array of 2 LocalDateTime objects
     * @throws IllegalArgumentException if the eventType is null
     */
    private Object[][] toLocalDateTimeList(String textResponse) {
        final String[] stringLocalDateTimeList = textResponse.split(",");

        if (stringLocalDateTimeList.length != 2) {
            throw new IllegalArgumentException("Text response must contain exactly two values.");
        }

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        final Object[][] result = new Object[2][2];

        final String splitter = "T";

        result[0][0] = DayOfWeek.valueOf(stringLocalDateTimeList[0].split(splitter)[0].toUpperCase());
        result[0][1] = LocalTime.parse(stringLocalDateTimeList[0].split(splitter)[1], timeFormatter);
        result[1][0] = DayOfWeek.valueOf(stringLocalDateTimeList[1].split(splitter)[0].toUpperCase());
        result[1][1] = LocalTime.parse(stringLocalDateTimeList[1].split(splitter)[1], timeFormatter);

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
                                            LocalTime endTime, EventConflictChatbotDataAccessInterface inMemoryDataAccessObject) {
        final ArrayList<String> tasks = new ArrayList<>();
        final ArrayList<Event> events = new ArrayList<>();
        final ArrayList<LocalTime> hourlyIntervals = getHourlyIntervals(startTime, endTime);

        // Add tasks in the time period to a list
        for (LocalTime hour : hourlyIntervals) {
            final Optional<Event> possibleEvent = inMemoryDataAccessObject.getEventByDayAndTime(startDay, hour);

            // If the event exists, add it to the tasks list
            if (possibleEvent.isPresent()) {
                final Event event = possibleEvent.get();
                if (!events.contains(event)) {
                    events.add(event);

                    // Add event details to the tasks list
                    final String[] taskTime = toStringTime(event.getDayStart(), event.getTimeStart(),
                            event.getTimeEnd());
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
     * @throws IllegalArgumentException if the eventType is null
     */
    private ArrayList<LocalTime> getHourlyIntervals(LocalTime start, LocalTime end) {
        final ArrayList<LocalTime> hourlyIntervals = new ArrayList<>();

        // Validate that start is before or equal to end
        if (start.isAfter(end)) {
            throw new IllegalArgumentException("Start time must be before or equal to end time.");
        }

        // Add each hour to the list
        LocalTime current = start;
        while (!current.isAfter(end)) {
            hourlyIntervals.add(current);
            // Move to the next hour
            current = current.plusHours(1);
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
        final String formattedDay = day.toString().charAt(0) + day.toString().substring(1).toLowerCase();

        // Format times to 12-hour clock with "a.m." and "p.m.". 12 hour format
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        final String formattedStartTime = startTime.format(timeFormatter).toLowerCase();
        final String formattedEndTime = endTime.format(timeFormatter).toLowerCase();

        return new String[]{formattedDay, formattedStartTime, formattedEndTime};
    }

    /**
     * Convert two LocalDateTime objects (start and end) into a list of string representations.
     * Example output: [Nov 27, 02:30 PM, 04:45 PM]
     *
     * @param start LocalDateTime object
     * @param end   LocalDateTime object
     * @return an array of string representation (Day, Start, End)
     * @throws IllegalArgumentException if the eventType is null
     */
    public String[] toStringTime(LocalDateTime start, LocalDateTime end) {
        // Validate input
        if (start == null || end == null) {
            throw new IllegalArgumentException("Input must be two non-null LocalDateTime objects.");
        }

        // Formatters
        final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MMM d");
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");

        // Extract and format Date, Start, and End
        final String date = start.format(dateFormatter);
        final String startTime = start.format(timeFormatter);
        final String endTime = end.format(timeFormatter);

        return new String[]{date, startTime, endTime};
    }
    
}
