package interface_adapter.edit;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * The state for the Edit View Model.
 */
public class EditState {
    private String eventName;
    private String eventType;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private List<String> daysRepeated;

    private String editError;

    String getEventName() {
        return eventName;
    }

    String getEventType() {
        return eventType;
    }

    String getDayStart() {
        return dayStart;
    }

    String getDayEnd() {
        return dayEnd;
    }

    String getTimeStart() {
        return timeStart;
    }

    String getTimeEnd() {
        return timeEnd;
    }

    List<String> getDaysRepeated() { return daysRepeated;}

    String getEditError() { return editError; }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setDaysRepeated(List<String> daysRepeated) {
        this.daysRepeated = daysRepeated;
    }

    public void setEditError(String editError) {
        this.editError = editError;
    }
}
