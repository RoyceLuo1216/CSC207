package interface_adapter.addEvent;

import usecase.event.AddEventInputBoundary;
import usecase.event.AddEventInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

        final DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        final DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        final LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

        final AddEventInputData addEventInputData = new AddEventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);

        eventInteractor.execute(addEventInputData);
    }

    /**
     * Switch back to schedule view.
     */
    public void backToScheduleView() {
        eventInteractor.backToScheduleView();
    }
}
