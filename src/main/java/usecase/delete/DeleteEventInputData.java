package usecase.delete;

/**
 * Input Data for DeleteEvent use case.
 */
public class DeleteEventInputData {
    private final String eventName;

    public DeleteEventInputData(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
