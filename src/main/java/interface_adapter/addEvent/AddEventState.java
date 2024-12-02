package interface_adapter.addEvent;

/**
 * The state for the Edit View Model.
 */
public class AddEventState {

    private String eventName = "";
    private String eventType = "fixed";
    private String dayStart = "Monday";
    private String dayEnd = "Monday";
    private String timeStart = "12:00 AM";
    private String timeEnd = "12:00 AM";

    private String eventError;

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

    public String getEventError() {
        return eventError;
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

    public void setEventError(String eventError) {
        this.eventError = eventError;
    }
}
