package usecase.edit;

import java.util.List;
import java.util.Optional;

import entities.eventEntity.Event;

/**
 * This class helps create the abstraction to access the schedule which stores our events.
 */
public interface EditEventDataAccessInterface {

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */
    Optional<Event> getEventByName(String name);

    /**
     * Method to find the name of the current event stored in memory.
     * @return the name of the current event as a string.
     */
    String getCurrentEventName();

    /**
     * Returns the details (time, day, etc.) associated with the current event stored in memory.
     * @return the list of all event details.
     */
    List<Object> getCurrentEventDetails();
}
