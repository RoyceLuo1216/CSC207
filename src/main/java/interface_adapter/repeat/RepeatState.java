package interface_adapter.repeat;

import java.util.List;

/**
 * The state for the Edit View Model.
 */
public class RepeatState {
    private String eventName;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private List<String> daysRepeated;

    private String repeatError;

    public String getEventName() {
        return eventName;
    }

    public String getDayStart() {
        return dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getRepeatError() {
        return repeatError;
    }

    public List<String> getDaysRepeated() {
        return daysRepeated;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
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

    public void setRepeatError(String repeatErrors) {
        this.repeatError = repeatErrors;
    }

    public void setDaysRepeated(List<String> daysRepeated) {
        this.daysRepeated = daysRepeated;
    }
}
