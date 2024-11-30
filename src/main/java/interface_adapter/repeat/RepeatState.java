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

    String getEventName() {
        return eventName;
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

    String getRepeatError() { return repeatError; }

    List<String> getDaysRepeated() { return daysRepeated; }

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

    public void setEditError(String repeatError) {
        this.repeatError = repeatError;
    }

    public void setDaysRepeated(List<String> daysRepeated) {this.daysRepeated = daysRepeated;}
}
