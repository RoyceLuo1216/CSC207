package usecase.add;

import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Add event Input Data
 */
public class AddEventInputData {
    private final String eventName;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;

    public AddEventInputData(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart, LocalTime timeEnd) {
        this.eventName = eventName;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getEventName() {
        return eventName;
    }

    public DayOfWeek getDayStart() {
        return dayStart;
    }

    public DayOfWeek getDayEnd() {
        return dayEnd;
    }

    public LocalTime getTimeStart() {
        return timeStart;
    }

    public LocalTime getTimeEnd() {
        return timeEnd;
    }
}
