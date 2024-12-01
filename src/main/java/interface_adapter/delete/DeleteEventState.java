package interface_adapter.delete;

/**
 * State information representing the Delete Event process.
 */
public class DeleteEventState {
    private String eventName;
    private String message;


    public DeleteEventState() {
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
