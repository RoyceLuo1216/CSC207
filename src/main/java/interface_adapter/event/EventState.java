package interface_adapter.event;

/**
 * The state for the Edit View Model.
 */
public class EventState {
    private String eventName;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;

    private String editError;

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

    public String getEventError() {
        return editError;
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

    public void setEditError(String editError) {
        this.editError = editError;
    }
}
