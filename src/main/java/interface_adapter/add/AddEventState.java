package interface_adapter.add;

/**
 * State for the AddEvent use case, representing the current UI state.
 */
public class AddEventState {
    private String eventName;
    private String errorMessage;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;

    public AddEventState() {
        // Initialize fields to default values
        this.eventName = "";
        this.errorMessage = "";
        this.dayStart = "";
        this.dayEnd = "";
        this.timeStart = "";
        this.timeEnd = "";
    }

    // Getters and Setters for all fields

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
