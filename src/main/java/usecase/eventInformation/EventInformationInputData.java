package usecase.eventInformation;

/**
 * The Input Data for the Event Information Use Case.
 */
public class EventInformationInputData {
    private final String eventName;

    public EventInformationInputData(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
