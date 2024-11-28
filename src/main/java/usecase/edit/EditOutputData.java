package usecase.edit;

/**
 * Output Data for the Edit use case.
 */
public class EditOutputData {
    private final String eventName;
    private final boolean useCaseFailed;
    
    public EditOutputData(String eventName, boolean useCaseFailed) {
        this.eventName = eventName;
        this.useCaseFailed = useCaseFailed;
    }

    public String getEventName(){ return eventName; }

    public boolean isUseCaseFailed() {return useCaseFailed; }
}
