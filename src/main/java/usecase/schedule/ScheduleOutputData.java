package usecase.schedule;

import entities.eventEntity.Event;

import java.util.List;

/**
 * Output data containing event names for presentation.
 */
public class ScheduleOutputData {
    private final List<Event> events;

    public ScheduleOutputData(List<Event> events) {
        this.events = events;
    }

    public List<Event> getEventNames() {
        return events;
    }
}
