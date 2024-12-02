package usecase.eventInformation;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * The Output Data for the Event Information Use Case.
 */
public class EventInformationOutputData {
    private String eventName;
    private String eventType;
    private DayOfWeek dayStart;
    private DayOfWeek dayEnd;
    private LocalTime timeStart;
    private LocalTime timeEnd;
    private List<DayOfWeek> daysRepeated;

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

    // Getter and Setter for eventName
    public String getEventName() {
        return eventName;
    }

    // Getter and Setter for eventType
    public String getEventType() {
        return eventType;
    }

    // Getter and Setter for dayStart
    public DayOfWeek getDayStart() {
        return dayStart;
    }

    // Getter and Setter for dayEnd
    public DayOfWeek getDayEnd() {
        return dayEnd;
    }

    // Getter and Setter for timeStart
    public LocalTime getTimeStart() {
        return timeStart;
    }

    // Getter and Setter for timeEnd
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    // Getter and Setter for daysRepeated
    public List<DayOfWeek> getDaysRepeated() {
        return daysRepeated;
    }
}
