package data_access;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import entities.eventEntity.Event;
import usecase.delete.DeleteEventDataAccessInterface;
import usecase.edit.EditDataAccessInterface;

/**
 * Class representing a ScheduleUseCase with a list of events. This class handles the
 * creation, deletion, and retrieval of events, and also provides a framework
 * for scheduling flexible events around fixed ones.
 */
public class InMemoryDataAccessObject implements DeleteEventDataAccessInterface,
                                                        EditDataAccessInterface {
    private final List<Event> events;

    /**
     * Constructor for the ScheduleUseCase class.
     */
    public InMemoryDataAccessObject() {
        this.events = new ArrayList<>();
    }

    /**
     * Adding an event to our schedule using Event Factory.
     *
     * @param event event object to be added.
     */
    public void addEvent(Event event) {
        events.add(event);
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

    /**
     * Method to delete an event by name.
     *
     * @param name the name of the event to be deleted
     */
    @Override
    public void deleteEvent(String name) {
        final Optional<Event> eventToRemove = getEventByName(name);
        eventToRemove.map(events::remove);
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

    @Override
    public Optional<Event> getEventByName(String name) {
        Optional<Event> result = Optional.empty();
        for (Event event : events) {
            if (event.getEventName().equalsIgnoreCase(name)) {
                result = Optional.of(event);
                break;
            }
        }
        return result;
    }

    /**
     * Method to get all events of a specified type in the schedule.
     *
     * @param eventType the class type of events to filter by (e.g., FixedEvent.class)
     * @param <T> the type of event to retrieve
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
        Optional<Event> result = Optional.empty();
        for (Event event : events) {
            final DayOfWeek startDay = event.getDayStart();
            final DayOfWeek endDay = event.getDayEnd();
            final LocalTime startTime = event.getTimeStart();
            final LocalTime endTime = event.getTimeEnd();

            // Check if the day falls within the event's days and the time within the event's time range
            if ((day.equals(startDay) || day.equals(endDay) || day.compareTo(startDay) > 0 && day.compareTo(endDay) < 0)
                    && (time.equals(startTime) || time.equals(endTime) || time.isAfter(startTime)
                    && time.isBefore(endTime))) {
                result = Optional.of(event);
                break;
            }
        }
        return result;
    }

    public List<Event> getEvents() {
        return this.events;
    }

}
