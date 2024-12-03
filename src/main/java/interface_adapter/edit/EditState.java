package interface_adapter.edit;

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
    private String outputMessage;

    public EditState(String eventName, String eventType, String dayStart, String dayEnd, String timeStart,
                     String timeEnd, List<String> daysRepeated, String outputMessage) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.daysRepeated = daysRepeated;
        this.outputMessage = outputMessage;
    }

    public EditState() {

    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
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

    public List<String> getDaysRepeated() {
        return daysRepeated;
    }

    public String getOutputMessage() {
        return outputMessage;
    }

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

    public void setOutputMessage(String outputMessage) {
        this.outputMessage = outputMessage;
    }
}
