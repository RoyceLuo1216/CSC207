package interface_adapter.event;

import usecase.edit.EditInputBoundary;
import usecase.edit.EditInputData;
import usecase.event.EventInputBoundary;
import usecase.event.EventInputData;
import usecase.repeat.RepeatInputBoundary;
import usecase.repeat.RepeatInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Edit Use Case.
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
    public void execute(String eventName, String eventType, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString) {

        DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);


        final EventInputData eventInputData = new EventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);

        eventInteractor.execute(eventInputData);
    }
}

