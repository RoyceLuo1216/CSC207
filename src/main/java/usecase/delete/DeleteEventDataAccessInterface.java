package usecase.delete;

import entities.EventEntity.Event;

import java.util.Optional;

public interface DeleteEventDataAccessInterface {

    /**
     * Deletes a specificied event from schedule.
     * @param eventName (name of the event to be deleted).
     * @return boolean representing if a feature was successfully deleted or not.
     */
    boolean deleteEvent(String eventName);

    /**
     * Method to find an event by its name.
     *
     * @param name the name of the event to find
     * @return an Optional containing the event if found, or an empty Optional
     */
    Optional<Event> getEventByName(String name);

}
