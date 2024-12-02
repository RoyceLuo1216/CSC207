package usecase.event;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * The input data for the Event use case.
 */
<<<<<<<< HEAD:src/main/java/usecase/event/AddEventInputData.java
public class AddEventInputData {
========
public class EventAddInputData {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddInputData.java
    private final String eventName;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;

<<<<<<<< HEAD:src/main/java/usecase/event/AddEventInputData.java
    public AddEventInputData(String eventName,
========
    public EventAddInputData(String eventName,
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddInputData.java
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
