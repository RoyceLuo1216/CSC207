package interface_adapter.add;

import usecase.add.AddEventInputBoundary;
import usecase.add.AddEventInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Controller for the AddEvent use case. Invokes the interactor.
 */
public class AddEventController {
    private final AddEventInputBoundary interactor;

    public AddEventController(AddEventInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Exectutes using dependencies from usecase.
     * @param eventName eventname to assign.
     * @param dayStart start day.
     * @param dayEnd end day.
     * @param timeStart start time.
     * @param timeEnd end time.
     */
    public void execute(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart, LocalTime timeEnd) {
        final AddEventInputData inputData = new AddEventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);
        interactor.execute(inputData);
    }
}
