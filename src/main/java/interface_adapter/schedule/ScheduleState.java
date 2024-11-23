package interface_adapter.schedule;

import entities.EventEntity.Event;

import java.util.List;

/**
 * The ScheduleState contains data to be rendered by the ScheduleView.
 */
public class ScheduleState {
    private final List<Event> events;

    public ScheduleState(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEvents() {
        return events;
    }
}
