package usecase.delete;

import entities.EventEntity.Event;

public class DeleteEventInputData {
    private final String eventName;

    public DeleteEventInputData(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }
}
