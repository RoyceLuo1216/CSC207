package usecase.repeat;

/**
 * Output Data for the Edit use case.
 */
public class RepeatOutputData {
    private final String eventName;
    private final boolean useCaseFailed;

    public RepeatOutputData(String eventName, boolean useCaseFailed) {
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
