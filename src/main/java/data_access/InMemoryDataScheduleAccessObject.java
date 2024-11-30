package data_access;

import entities.EventEntity.Event;
import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: ASK EVERYONE WHETHER OR NOT WE CAN DELETE SCHEDULE ENTITY

/**
 * Class representing a ScheduleUseCase with a list of events. This class handles the
 * creation, deletion, and retrieval of events, and also provides a framework
 * for scheduling flexible events around fixed ones.
 */
public class InMemoryDataScheduleAccessObject {
    private final List<Event> events;

    /**
     * Constructor for the InMemoryDataScheduleAccessObject
     */
    public InMemoryDataScheduleAccessObject() {
        this.events = new ArrayList<>();
    }

    /**
     * Add an event.
     *
     * @param event the name of the event to be deleted
     * @return true if the event was successfully added
     */
    public boolean addEvent(Event event) {
        return events.add(event);
    }

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     * @return true if the event was successfully deleted
     */
    public boolean deleteEvent(String name) {
        return events.removeIf(event -> event.getEventName().equalsIgnoreCase(name));
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
     * Method to get all events of a specified type in the schedule.
     *
     * @param eventType the class type of events to filter by (e.g., FixedEvent.class)
     * @return a list of events of the specified type
     */
    public <T extends Event> List<T> getEventsByType(Class<T> eventType) {
        return events.stream()
                .filter(eventType::isInstance)
                .map(eventType::cast)
                .collect(Collectors.toList());
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

