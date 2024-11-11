package usecase.fixed;

import EventEntity.FixedEvent;
import usecase.Schedule;

import java.time.LocalDateTime;

/**
 * Use case for managing Fixed Events.
 */
public class FixedEventUseCase {
    private final Schedule schedule;

    public FixedEventUseCase(Schedule schedule) {
        this.schedule = schedule;
    }

    /**
     * Method to create a fixed event with specified start and end times.
     *
     * @param name     the name of the event
     * @param priority the priority of the event (on a scale of 1-5)
     * @param dayStart the date and time the event starts
     * @param dayEnd   the date and time the event ends
     * @return true if the event was successfully added
     */
    public boolean addFixedEvent(String name, int priority, LocalDateTime dayStart, LocalDateTime dayEnd) {
        FixedEvent event = new FixedEvent(dayStart, dayEnd, name, priority);
        return schedule.addEvent(event);
    }


    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean removeFixedEvent(String name) {
        return schedule.removeEvent(name);
    }
}
