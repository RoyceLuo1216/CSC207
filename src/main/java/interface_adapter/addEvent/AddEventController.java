package interface_adapter.addEvent;

import usecase.event.AddEventInputBoundary;
import usecase.event.AddEventInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Controller for the Add Event Use Case.
 */

public class AddEventController {
    private final AddEventInputBoundary eventInteractor;

    public AddEventController(AddEventInputBoundary eventInteractor) {
        this.eventInteractor = eventInteractor;
    }

    /**
     * Executes the Edit use case.
     * @param eventName the name of the event
     * @param dayStartString the start day of the event
     * @param dayEndString the end day of the event
     * @param timeStartString the start time of the event
     * @param timeEndString the end time of the event
     */
    public void execute(String eventName, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString) {

        // Set default values for dayStart and dayEnd if null or invalid
        final DayOfWeek dayStart = parseDayOfWeek(dayStartString, DayOfWeek.MONDAY);
        final DayOfWeek dayEnd = parseDayOfWeek(dayEndString, DayOfWeek.FRIDAY);

        // Set default values for timeStart and timeEnd if null or invalid
        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = parseLocalTime(timeStartString, "8:00 AM", timeFormatter);
        final LocalTime timeEnd = parseLocalTime(timeEndString, "5:00 PM", timeFormatter);

        // Create input data object
        final AddEventInputData addEventInputData = new AddEventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);

        // Execute eventInteractor with input data
        eventInteractor.execute(addEventInputData);
    }

    private DayOfWeek parseDayOfWeek(String dayString, DayOfWeek defaultDay) {
        try {
            if (dayString == null) return defaultDay;
            return DayOfWeek.valueOf(dayString.toUpperCase());
        } catch (IllegalArgumentException e) {
            return defaultDay;
        }
    }

    private LocalTime parseLocalTime(String timeString, String defaultTime, DateTimeFormatter formatter) {
        try {
            if (timeString == null) return LocalTime.parse(defaultTime, formatter);
            return LocalTime.parse(timeString, formatter);
        } catch (DateTimeParseException e) {
            return LocalTime.parse(defaultTime, formatter);
        }
    }

    /**
     * Switch back to schedule view.
     */
    public void backToScheduleView() {
        eventInteractor.backToScheduleView();
    }
}
