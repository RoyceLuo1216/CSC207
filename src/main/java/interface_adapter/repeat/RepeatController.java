package interface_adapter.repeat;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import usecase.repeat.RepeatInputBoundary;
import usecase.repeat.RepeatInputData;

/**
 * Controller for the Repeat Use Case.
 */
public class RepeatController {
    private final RepeatInputBoundary repeatInteractor;

    public RepeatController(RepeatInputBoundary repeatInteractor) {
        this.repeatInteractor = repeatInteractor;
    }

    /**
     * Executes the Edit use case.
     * @param eventName the name of the event
     * @param dayStartString the start day of the event
     * @param dayEndString the end day of the event
     * @param timeStartString the start time of the event
     * @param timeEndString the end time of the event
     * @param daysRepeated the days repeated
     */
    public void execute(String eventName, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString, List<String> daysRepeated) {

        final DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        final DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        final LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

        final List<DayOfWeek> daysRepeatedInput = new ArrayList<DayOfWeek>();

        // For loop to add list of strings of day of weeks to DayOfWeek list
        for (String day : daysRepeated) {
            final DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
            daysRepeatedInput.add(dayOfWeek);
        }

        final RepeatInputData repeatInputData = new RepeatInputData(eventName, dayStart, dayEnd, timeStart, timeEnd,
                daysRepeatedInput);

        repeatInteractor.execute(repeatInputData);
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
     * Returns the back to main schedule view.
     */
    public void backToScheduleView() {
        repeatInteractor.backToScheduleView();
    }
}
