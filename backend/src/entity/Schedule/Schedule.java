package schedule;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import EventEntity.Event;

/**
 * Class representing a Schedule with a name, start and end time, and priority.
 * This Task can be part of a larger Event and represents a specific time block.
 */

public class Schedule {
    private List<Event> events;

    /**
     * Constructor for the Schedule class.
     */
    public Schedule() {
        this.events = new ArrayList<>();
    }

    /**
     * Method to create a fixed event with specified start and end times.
     * @param name              the name of the event
     * @param priority     the priority of the event (on a scale of 1-5)
     * @param dayStart          the date and time the event starts
     * @param dayEnd            the date and time the event ends
     * @return True             if the event was successfully added
     */
    public boolean createFixedEvent(String name, int priority, LocalDateTime dayStart,
                                    LocalDateTime dayEnd) {

        final FixedEvent event = new FixedEvent(dayStart, dayEnd, name, priority);
        return events.add(event);
    }

    /**
     * Method to create a flexible event that only specifies a time allocation.
     * @param name              the name of the event
     * @param priority     the priority of the event (on a scale of 1-5)
     * @param dayStart          the date and time the event starts (i.e. monday, tuesday, etc.)
     * @param dayEnd            the date and time the event ends
     * @param timeAllocation    how many hours the event will take (over the course of dayStart and dayEnd)
     * @return True             if the event was successfully added
     */
    public boolean createFlexibleEvent(String name, int priority, LocalDateTime dayStart,
                                       LocalDateTime dayEnd, int timeAllocation) {
        final FlexibleEvent event = new FlexibleEvent(name, priority, dayStart, dayEnd, timeAllocation);
        return events.add(event);
    }

    /**
     * Method to create a repeat event with specified start and end times.
     * @param name              the name of the event
     * @param priority     the priority of the event (on a scale of 1-5)
     * @param dayStart          the date and time the event starts (i.e. monday, tuesday, etc.)
     * @param dayEnd            the date and time the event ends
     * @return True             if the event was successfully added
     */
    public boolean createRepeatEvent(String name, int priority, LocalDateTime dayStart,
                                      LocalDateTime dayEnd) {
        final RepeatEvent event = new RepeatEvent(name, priority, dayStart, dayEnd);
        return events.add(event);
    }

    /**
     * Method to delete an event by name.
     * @param name      name of the event
     * @return True     if the event was successfully deleted
     */
    public boolean deleteEvent(String name) {

        return events.remove(name);
    }

    /**
     * Method to automatically schedule events.
     * @return True     if the event was successfully deleted
     */
    public boolean scheduleEvents() {
        // Logic to schedule events
        // API call
        return true;
    }

    /**
     * Method to get all events in the schedule.
     * @return a list of all the events currently in the schedule
     */
    public List<Event> getAllEvents() {
        return events;
    }

    /**
     * Method to find an event by its name.
     * @param name the name of the event we want to find
     * @return the event with the name or an empty Optional object if the event cannot be found
     */
    public Optional<Event> getEventByName(String name) {
        Optional<Event> result = Optional.empty();
        for (Event event : events) {
            if (event.getEventName().equals(name)) {
                result = Optional.of(event);
            }
        }
        return result;
    }

    /**
     * Method to find an event by a specific time.
     * @param time      the date/time of the event we want to find
     * @return the event scheduled at that time or an empty Optional object if the event cannot be found
     */
    public Optional<Event> getEventByTime(LocalDateTime time) {
        Optional<Event> result = Optional.empty();
        for (Event event : events) {
            final LocalDateTime startDate = event.getDayStart();
            final LocalDateTime endDate = event.getDayEnd();

            if (startDate.isEqual(time) || endDate.isEqual(time)
                    || startDate.isBefore(time) && (endDate.isAfter(time))) {
                result = Optional.of(result);
            }
        }
        return result;
    }
}
