package usecase.eventInformation;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Output Data for Event Informaiton
 */
public class EventInformationOutputData {
    private final String eventName;
    private final String eventType;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;
    private final List<DayOfWeek> daysRepeated;

    public EventInformationOutputData(String eventName, String eventType, DayOfWeek dayStart, DayOfWeek dayEnd,
                                      LocalTime timeStart, LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.daysRepeated = daysRepeated;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
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

    public List<DayOfWeek> getDaysRepeated() {
        return daysRepeated;
    }
}
