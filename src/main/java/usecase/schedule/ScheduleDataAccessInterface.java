package usecase.schedule;

import entities.eventEntity.Event;

import java.util.List;

/**
 * Data Access Interface for schedule-related operations.
 */
public interface ScheduleDataAccessInterface {
    /**
     * Retrieves all event names.
     *
     * @return a list of all event names.
     */
    List<String> getAllEventNames();

    List<Event> getAllEvents();

    /**
     * Sets the current event to the one we want.
     * @param eventName name of the event we are using.
     */
    void setCurrentEventName(String eventName);
}
