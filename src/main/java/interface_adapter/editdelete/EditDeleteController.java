package interface_adapter.editdelete;

import entities.EventEntity.Event;
import entities.ScheduleEntity.Schedule;

import java.time.LocalDateTime;

/**
 * The EditDeleteController handles logic for editing or deleting events.
 */
public class EditDeleteController {
    private final Schedule schedule;
    private Event currentEvent;

    public EditDeleteController(Schedule schedule, Event currentEvent) {
        this.schedule = schedule;
        this.currentEvent = currentEvent;
    }

    public Event getCurrentEvent() {
        return currentEvent;
    }

    public boolean deleteEvent() {
        return schedule.removeEvent(currentEvent.getEventName());
    }
}
