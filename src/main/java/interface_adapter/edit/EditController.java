package interface_adapter.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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
     */
    public void execute(String eventName, String eventType, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString) {

        final DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        final DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        final LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

        final EditInputData editInputData = new EditInputData(eventName,
                eventType,
                dayStart,
                dayEnd,
                timeStart,
                timeEnd);

        editInteractor.execute(editInputData);
    }
}

