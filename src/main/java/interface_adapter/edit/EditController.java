package interface_adapter.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import usecase.edit.EditEventInputBoundary;
import usecase.edit.EditEventInputData;

/**
 * The Controller for the Edit Use Case.
 */
public class EditController {
    private final EditEventInputBoundary interactor;

    public EditController(EditEventInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Fetches the current event details to populate the edit form.
     */
    public void fetchEventDetails() {
        interactor.populateEventFields();
    }

    /**
     * Executes the edit use case by passing updated data to the interactor.
     *
     * @param eventName    The name of the event.
     * @param eventType    The type of the event (Fixed or Repeat).
     * @param dayStart     The start day of the event.
     * @param dayEnd       The end day of the event.
     * @param timeStart    The start time of the event.
     * @param timeEnd      The end time of the event.
     * @param daysRepeated The list of repeated days for the event (if applicable).
     */
    public void execute(String eventName, String eventType, String dayStart, String dayEnd,
                        String timeStart, String timeEnd, List<String> daysRepeated) {

        DayOfWeek parsedDayStart = parseDayOfWeek(dayStart, DayOfWeek.MONDAY);
        DayOfWeek parsedDayEnd = parseDayOfWeek(dayEnd, DayOfWeek.FRIDAY);
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a", Locale.ENGLISH);
        LocalTime parsedTimeStart = parseLocalTime(timeStart, "8:00 AM", timeFormatter);
        LocalTime parsedTimeEnd = parseLocalTime(timeEnd, "5:00 PM", timeFormatter);

        List<DayOfWeek> parsedDaysRepeated = Collections.emptyList();
        if (daysRepeated != null) {
            parsedDaysRepeated = daysRepeated.stream()
                    .map(day -> parseDayOfWeek(day, null))
                    .filter(java.util.Objects::nonNull)
                    .collect(Collectors.toList());
        }

        interactor.execute(new EditEventInputData(
                eventName,
                eventType,
                parsedDayStart,
                parsedDayEnd,
                parsedTimeStart,
                parsedTimeEnd,
                parsedDaysRepeated
        ));
    }

    /**
     * Parses a day of the week from a string, with a fallback to a default value if parsing fails.
     *
     * @param dayString  The string representation of the day.
     * @param defaultDay The default value to use if parsing fails.
     * @return The parsed DayOfWeek, or the default value if parsing fails.
     */
    private DayOfWeek parseDayOfWeek(String dayString, DayOfWeek defaultDay) {
        try {
            return DayOfWeek.valueOf(dayString.toUpperCase());
        }
        catch (IllegalArgumentException | NullPointerException e) {
            return defaultDay;
        }
    }

    /**
     * Parses a time from a string, with a fallback to a default value if parsing fails.
     *
     * @param timeString    The string representation of the time.
     * @param defaultTime   The default value to use if parsing fails.
     * @param formatter     The DateTimeFormatter used for parsing the time.
     * @return The parsed LocalTime, or the default value if parsing fails.
     */
    private LocalTime parseLocalTime(String timeString, String defaultTime, DateTimeFormatter formatter) {
        try {
            return LocalTime.parse(timeString.trim(), formatter);
        }
        catch (DateTimeParseException | NullPointerException e) {
            return LocalTime.parse(defaultTime, formatter);
        }
    }


    /**
     * Delete view.
     */
    public void deleteView() {
        interactor.deleteView();
    }

    /**
     * Switch to schedule view.
     */
    public void scheduleView() {
        interactor.scheduleView();
    }
}
