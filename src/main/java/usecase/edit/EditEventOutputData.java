package usecase.edit;

/**
 * Output Data for the Edit use case.
 */
public class EditEventOutputData {
    private final String eventName;
    private final boolean useCaseFailed;
    private final String outputMessage;

    public EditEventOutputData(String eventName, boolean useCaseFailed, String outputMessage) {
        this.eventName = eventName;
        this.useCaseFailed = useCaseFailed;
        this.outputMessage = outputMessage;
    }

    public String getEventName() {
        return eventName;
    }

    public boolean isUseCaseFailed() {
        return useCaseFailed;
    }

    public String getOutputMessage() {
        return outputMessage;
    }
}
