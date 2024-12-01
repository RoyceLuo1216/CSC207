package entities.EventEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

/**
 * Interface representing an Event with a name, start and end time, and priority label.
 */
public interface Event {

    String setEventName();

    /**
     * Gets the name of the event.
     *
     * @return the name of the event as a String.
     */
    String getEventName();

    /**
     * Sets the name of the event.
     *
     * @param name the new name of the event.
     */
    void setEventName(String name);


    DayOfWeek getDayStart();

    /**
     * Sets the starting day of the event.
     *
     * @param dayStart the new start date of the event.
     */
    void setDayStart(DayOfWeek dayStart);

    /**
     * Gets the ending day of the event.
     *
     * @return the end time of the event as a DayOfWeek object.
     */
    DayOfWeek getDayEnd();

    /**
     * Sets the ending day of the event.
     *
     * @param dayEnd the new end date of the event.
     */
    void setDayEnd(DayOfWeek dayEnd);

    /**
     * Gets the start time of the event.
     *
     * @return the start time of the event as a DayOfWeek.
     */
    LocalTime getTimeStart();

    /**
     * Sets the end date and time of the event.
     *
     * @param timeStart the new start time of the event.
     */
    void setTimeStart(LocalTime timeStart);

    /**
     * Gets the end time of the event.
     *
     * @return the end time of the event as a DayOfWeek.
     */
    LocalTime getTimeEnd();

    /**
     * Sets the end date and time of the event.
     *
     * @param timeEnd the new end time of the event.
     */
    void setTimeEnd(LocalTime timeEnd);

    /**
     * Returns the list of tasks associated with the event.
     *
     * @return
     */
    List<Task> getTasks();

    /**
     * Adds a Task to the list
     *
     * @param task, task that you want to add.
     */
    void addTask(Task task);

    /**
     * Removes a task from the list.
     *
     * @param task, task to be removed.
     */
    void removeTask(Task task);

}
