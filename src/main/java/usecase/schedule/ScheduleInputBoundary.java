package usecase.schedule;

import entities.EventEntity.Event;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Input Boundary for Schedule.
 */
public interface ScheduleInputBoundary {
    /**
     * Adds an event to the schedule
     * @param event event to be added.
     */
    void addEvent(Event event);

    /**
     * removes an event by its name.
     * @param eventName name of the event.
     */
    void removeEvent(String eventName);

    /**
     * Returns a list of events by day and time.
     * @param day
     * @param time
     * @return
     */
    List<Event> getEventsByDayAndTime(DayOfWeek day, LocalTime time);
}
