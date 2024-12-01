package interface_adapter.event;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import usecase.event.EventInputBoundary;
import usecase.event.EventInputData;

/**
 * Controller for the Add Event Use Case.
 */
public class EventController {
    private final EventInputBoundary eventInteractor;

    public EventController(EventInputBoundary eventInteractor) {
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

        final EventInputData eventInputData = new EventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);

        eventInteractor.execute(eventInputData);
    }
}
