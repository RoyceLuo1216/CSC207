package usecase;

import EventEntity.Event;
import EventEntity.RepeatEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Class representing a ScheduleUseCase with a list of events. This class handles the
 * creation, deletion, and retrieval of events, and also provides a framework
 * for scheduling flexible events around fixed ones.
 */
public class Schedule {
    private final List<Event> events;

    /**
     * Constructor for the ScheduleUseCase class.
     */
    public Schedule() {
        this.events = new ArrayList<>();
    }

    public boolean addEvent(Event event) {
        return events.add(event);
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean removeEvent(String name) {
        return events.removeIf(event -> event.getEventName().equalsIgnoreCase(name));
    }


//    /**
//     * Method to create a fixed event with specified start and end times.
//     * @param name              the name of the event
//     * @param priority          the priority of the event (on a scale of 1-5)
//     * @param dayStart          the date and time the event starts
//     * @param dayEnd            the date and time the event ends
//     * @return true if the event was successfully added
//     */
//    public boolean createFixedEvent(String name, int priority, LocalDateTime dayStart,
//                                    LocalDateTime dayEnd) {
//        FixedEvent event = new FixedEvent(dayStart, dayEnd, name, priority);
//        return events.add(event);
//    }
//
//
//    /**
//     * Method to create a flexible event that specifies a time allocation.
//     * @param name              the name of the event
//     * @param priority          the priority of the event (on a scale of 1-5)
//     * @param dayStart          the date and time the event starts
//     * @param dayEnd            the date and time the event ends
//     * @param timeAllocation    the amount of time allocated for the event (in hours)
//     * @return true if the event was successfully added
//     */
//    public boolean createFlexibleEvent(String name, int priority, LocalDateTime dayStart,
//                                       LocalDateTime dayEnd, float timeAllocation) {
//        FlexibleEvent event = new FlexibleEvent(dayStart, dayEnd, name, priority, timeAllocation);
//        return events.add(event);
//    }

    /**
     * Method to create a repeat event with specified start and end times.
     *
     * @param name     the name of the event
     * @param priority the priority of the event (on a scale of 1-5)
     * @param dayStart the date and time the event starts
     * @param dayEnd   the date and time the event ends
     * @return true if the event was successfully added
     */
    public boolean createRepeatEvent(String name, int priority, LocalDateTime dayStart,
                                     LocalDateTime dayEnd, List<LocalDateTime> daysRepeated) {
        RepeatEvent event = new RepeatEvent(dayStart, dayEnd, name, priority, daysRepeated);
        return events.add(event);
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean deleteEvent(String name) {
        Optional<Event> eventToRemove = getEventByName(name);
        return eventToRemove.map(events::remove).orElse(false);
    }

    /**
     * Method to automatically schedule flexible events around fixed events.
     * This placeholder method could be expanded with API calls to Cohere or
     * OR-Tools to handle flexible scheduling.
     *
     * @return true if the scheduling process completed successfully
     */
    public boolean scheduleEvents() {
        // Placeholder for scheduling logic.
        // Example: Use Cohere API to get potential time slots for flexible events.
        // Then, apply OR-Tools for precise optimization.
        System.out.println("Scheduling events...");

        // Implement scheduling logic here
        return true;
    }

    /**
     * Method to get all events in the schedule.
     *
     * @return a list of all the events currently in the schedule
     */
    public List<Event> getAllEvents() {
        return events;
    }

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */
    public Optional<Event> getEventByName(String name) {
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(name)) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

    /**
     * Method to find an event by a specific time.
     *
     * @param time the date/time to search for an event
     * @return an Optional containing the event at that time, or an empty Optional
     */
    public Optional<Event> getEventByTime(LocalDateTime time) {
        for (Event event : events) {
            LocalDateTime startDate = event.getDayStart();
            LocalDateTime endDate = event.getDayEnd();

            if (!startDate.isAfter(time) && !endDate.isBefore(time)) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }
}
