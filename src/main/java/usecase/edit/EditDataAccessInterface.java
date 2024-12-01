package usecase.edit;

import java.util.Optional;

import entities.EventEntity.Event;

/**
 * This class helps create the abstraction to access the schedule which stores our events.
 */
public interface EditDataAccessInterface {

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */
    Optional<Event> getEventByName(String name);

}
