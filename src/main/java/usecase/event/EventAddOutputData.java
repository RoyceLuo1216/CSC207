package usecase.event;

/**
 * Output Data for the Edit use case.
 */
<<<<<<<< HEAD:src/main/java/usecase/event/AddEventOutputData.java
public class AddEventOutputData {
    private final String eventName;
    private final boolean useCaseFailed;

    public AddEventOutputData(String eventName, boolean useCaseFailed) {
========
public class EventAddOutputData {
    private final String eventName;
    private final boolean useCaseFailed;

    public EventAddOutputData(String eventName, boolean useCaseFailed) {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddOutputData.java
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
