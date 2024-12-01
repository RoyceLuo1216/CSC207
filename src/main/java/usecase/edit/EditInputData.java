package usecase.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * The input data for the Edit use case.
 */
public class EditInputData {
    private final String eventName;
    private final String eventType;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;
    private final List<DayOfWeek> daysRepeated;

    public EditInputData(String eventName, String eventType, DayOfWeek dayStart, DayOfWeek dayEnd,
                         LocalTime timeStart, LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.daysRepeated = daysRepeated;
    }

    String getEventName() {
        return eventName;
    }

    String getEventType() {
        return eventType;
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

    List<DayOfWeek> getDaysRepeated() {
        return daysRepeated;
    }

}
