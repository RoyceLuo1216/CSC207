package usecase.delete;

public class DeleteEventOutputData {
    private final String eventName;

    public DeleteEventOutputData(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

}
