package usecase.schedule;

import entities.eventEntity.Event;
import interface_adapter.schedule.ScheduleState;

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
}
