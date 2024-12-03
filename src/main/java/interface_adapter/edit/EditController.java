package interface_adapter.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
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
        interactor.execute(new EditEventInputData(
                eventName,
                eventType,
                DayOfWeek.valueOf(dayStart.toUpperCase()),
                DayOfWeek.valueOf(dayEnd.toUpperCase()),
                LocalTime.parse(timeStart),
                LocalTime.parse(timeEnd),
                daysRepeated.stream().map(DayOfWeek::valueOf).collect(Collectors.toList())
        ));
    }
}
