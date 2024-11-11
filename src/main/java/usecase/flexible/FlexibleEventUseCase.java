package usecase.flexible;

import EventEntity.FlexibleEvent;
import usecase.Schedule;

import java.time.LocalDateTime;

/**
 * Use case for managing Flexible Events.
 */
public class FlexibleEventUseCase {
    private final Schedule schedule;
    private final FlexibleEventOptimizer optimizer;

    public FlexibleEventUseCase(Schedule schedule) {
        this.schedule = schedule;
        this.optimizer = new FlexibleEventOptimizer();
    }

    /**
     * Method to create a flexible event that specifies a time allocation.
     *
     * @param name           the name of the event
     * @param priority       the priority of the event (on a scale of 1-5)
     * @param dayStart       the date and time the event starts
     * @param dayEnd         the date and time the event ends
     * @param timeAllocation the amount of time allocated for the event (in hours)
     * @return true if the event was successfully added
     */
    public boolean addFlexibleEvent(String name, int priority, LocalDateTime dayStart, LocalDateTime dayEnd, float timeAllocation) {
        FlexibleEvent event = new FlexibleEvent(dayStart, dayEnd, name, priority, timeAllocation);
        return schedule.addEvent(event);
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean removeFlexibleEvent(String name) {
        return schedule.removeEvent(name);
    }

    public void optimizeFlexibleEvents() {
        optimizer.optimizeFlexibleEvents(schedule.getEventsByType(FlexibleEvent.class));
    }
}
