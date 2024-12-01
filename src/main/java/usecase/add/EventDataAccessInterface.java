package usecase.add;

import entities.EventEntity.Event;

/**
 * Interface for Event Data
 */
public interface EventDataAccessInterface {
    /**
     * Adds an event to our database.
     *
     * @param event event to be added.
     * @return boolean for if event is successfully added.
     */
    boolean addEvent(Event event);

    /**
     * Checks if the event already exists.
     * @param eventName name of the event.
     * @return returns if the event already exists.
     */
    boolean eventExists(String eventName);
}
