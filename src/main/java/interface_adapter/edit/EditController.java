package interface_adapter.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import usecase.edit.EditInputBoundary;
import usecase.edit.EditInputData;

/**
 * Controller for the Edit Use Case.
 */
public class EditController {
    private final EditInputBoundary editInteractor;

    public EditController(EditInputBoundary editInteractor) {
        this.editInteractor = editInteractor;
    }

    /**
     * Executes the Edit use case.
     * @param eventName the name of the event
     * @param eventType the type of the event
     * @param dayStartString the start day of the event
     * @param dayEndString the end day of the event
     * @param timeStartString the start time of the event
     * @param timeEndString the end time of the event
     * @param daysRepeatedString an array of days that are repeated (for repeat events only)
     */
    public void execute(String eventName, String eventType, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString, List<String> daysRepeatedString) {

        final DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        final DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        final LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

        final List<DayOfWeek> daysRepeated = new ArrayList<>();

        // if the event is a repeat event, change each day into a DayOfWeek and add it to daysRepeated
        // otherwise, leave daysRepeated an empty array
        if ("REPEAT".equalsIgnoreCase(eventType)) {
            for (String day: daysRepeatedString) {
                daysRepeated.add(DayOfWeek.valueOf(day.toUpperCase()));
            }
        }

        final EditInputData editInputData = new EditInputData(eventName, eventType, dayStart, dayEnd, timeStart,
                timeEnd, daysRepeated);

        editInteractor.execute(editInputData);
    }
}

