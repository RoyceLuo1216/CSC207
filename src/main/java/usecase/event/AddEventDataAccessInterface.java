package usecase.event;

import java.util.List;
import java.util.Optional;

import entities.eventEntity.Event;

/**
 * This class helps create the abstraction to access the schedule which stores our events.
 */
<<<<<<<< HEAD:src/main/java/usecase/event/AddEventDataAccessInterface.java
public interface AddEventDataAccessInterface {
========
public interface EventAddDataAccessInterface {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/usecase/event/EventAddDataAccessInterface.java

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
     * @param fixedEvent
     */
    void addEvent(Event fixedEvent);
}
