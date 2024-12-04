package usecase.delete;

/**
 * DeleteEvent Output Data.
 */
public class DeleteEventOutputData {
    private final String eventName;

    public DeleteEventOutputData(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public String getMessage() {
        return "output";
    }
}
