package usecase.event;

/**
 * Output Data for the Edit use case.
 */
public class EventOutputData {
    private final String eventName;
    private final boolean useCaseFailed;

    public EventOutputData(String eventName, boolean useCaseFailed) {
        this.eventName = eventName;
        this.useCaseFailed = useCaseFailed;
    }

    public String getEventName() {
        return eventName;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }
}