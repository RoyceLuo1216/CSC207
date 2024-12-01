package data_access;

import entities.EventEntity.Event;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// TODO: ASK EVERYONE WHETHER OR NOT WE CAN DELETE SCHEDULE ENTITY

// TODO: add whatever interfaces are needed in the dao here

/**
 * Data Access Object for program use. Does not persist after program closes.
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
     * Method to find an event by a specific day and time.
     *
     * @param day  the day of the week to search for an event
     * @param time the time to search for an event
     * @return an Optional containing the event at that day and time, or an empty Optional
     */
    public Optional<Event> getEventByDayAndTime(DayOfWeek day, LocalTime time) {
        for (Event event : events) {
            final DayOfWeek startDay = event.getDayStart();
            final DayOfWeek endDay = event.getDayEnd();
            final LocalTime startTime = event.getTimeStart();
            final LocalTime endTime = event.getTimeEnd();

            // Check if the day falls within the event's days and the time within the event's time range
            if ((day.equals(startDay) || day.equals(endDay) || day.compareTo(startDay) > 0 && day.compareTo(endDay) < 0)
                    && (time.equals(startTime) || time.equals(endTime) || time.isAfter(startTime)
                    && time.isBefore(endTime))) {
                return Optional.of(event);
            }
        }
        return Optional.empty();
    }

}

