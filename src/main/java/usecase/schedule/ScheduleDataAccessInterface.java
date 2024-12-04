package usecase.schedule;

import entities.eventEntity.Event;

import java.util.List;
import java.util.Map;

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

    /**
     * Retrieves all events.
     * @return a list of event objects.
     */
    List<Event> getAllEvents();

    /**
     * Returns the events in memory.
     * @return return events in memory.
     */
    Map<String, Map<String, Object>> getAllEventInfo();

    /**
     * Sets the current event to the one we want.
     * @param eventName name of the event we are using.
     */
    void setCurrentEventName(String eventName);

    /**
     * Deletes a specificied event from schedule.
     *
     * @param eventName (name of the event to be deleted).
     */
    void deleteEvent(String eventName);

}
