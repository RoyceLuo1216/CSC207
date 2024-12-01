package usecase.event;

import java.time.LocalTime;
import java.time.DayOfWeek;

/**
 * The input data for the Event use case.
 */
public class EventInputData {
    private final String eventName;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;

    public EventInputData(String eventName,
                           DayOfWeek dayStart,
                           DayOfWeek dayEnd,
                           LocalTime timeStart,
                           LocalTime timeEnd) {
        this.eventName = eventName;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    String getEventName() {
        return eventName;
    }


    DayOfWeek getDayStart() {
        return dayStart;
    }

    DayOfWeek getDayEnd() {
        return dayEnd;
    }

    LocalTime getTimeStart() {
        return timeStart;
    }

    LocalTime getTimeEnd() {
        return timeEnd;
    }
}
