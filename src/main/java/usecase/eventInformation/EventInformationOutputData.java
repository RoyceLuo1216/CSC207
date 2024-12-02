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

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    // Getter and Setter for eventType
    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    // Getter and Setter for dayStart
    public DayOfWeek getDayStart() {
        return dayStart;
    }

    public void setDayStart(DayOfWeek dayStart) {
        this.dayStart = dayStart;
    }

    // Getter and Setter for dayEnd
    public DayOfWeek getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(DayOfWeek dayEnd) {
        this.dayEnd = dayEnd;
    }

    // Getter and Setter for timeStart
    public LocalTime getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    // Getter and Setter for timeEnd
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
    }

    // Getter and Setter for daysRepeated
    public List<DayOfWeek> getDaysRepeated() {
        return daysRepeated;
    }

    public void setDaysRepeated(List<DayOfWeek> daysRepeated) {
        this.daysRepeated = daysRepeated;
    }
}
