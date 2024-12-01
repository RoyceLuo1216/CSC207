package interface_adapter.eventAdd;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import usecase.event.EventAddInputBoundary;
import usecase.event.EventAddInputData;

/**
 * Controller for the Add Event Use Case.
 */
public class EventAddController {
    private final EventAddInputBoundary eventInteractor;

    public EventAddController(EventAddInputBoundary eventInteractor) {
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

        final EventAddInputData eventAddInputData = new EventAddInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);

        eventInteractor.execute(eventAddInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView() {
        eventInteractor.backToMainView();
    }
}
