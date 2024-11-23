package interface_adapter.schedule;

import entities.EventEntity.Event;
import entities.ScheduleEntity.Schedule;

import java.util.List;

/**
 * The ScheduleController handles requests from the ScheduleView
 * and interacts with the use case layer (Schedule).
 */
public class ScheduleController {
    private final Schedule schedule;

    public ScheduleController(Schedule schedule) {
        this.schedule = schedule;
    }

    public List<Event> getAllEvents() {
        return schedule.getAllEvents();
    }

    public boolean addEvent(Event event) {
        return schedule.addEvent(event);
    }

    public boolean removeEvent(String eventName) {
        return schedule.removeEvent(eventName);
    }
}
