package usecase.event;

import java.util.List;
import java.util.Optional;

import entities.eventEntity.Event;

/**
 * This class helps create the abstraction to access the schedule which stores our events.
 */
public interface AddEventDataAccessInterface {

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */
    Optional<Event> getEventByName(String name);

    /**
     * Method to get all events.
     * @return list of events.
     */
    List<Event> getAllEvents();

    /**
     * Adds an event.
     * @param fixedEvent fixed event.
     */
    void addEvent(Event fixedEvent);
}
