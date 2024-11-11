package EventEntity;

import java.time.LocalDateTime;
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

    /**
     * Gets the start date and time of the event.
     *
     * @return the start time of the event as a LocalDateTime object.
     */
    LocalDateTime getDayStart();

    /**
     * Sets the start date and time of the event.
     *
     * @param dayStart the new start date and time of the event.
     */
    void setDayStart(LocalDateTime dayStart);

    /**
     * Gets the end date and time of the event.
     *
     * @return the end time of the event as a LocalDateTime object.
     */
    LocalDateTime getDayEnd();

    /**
     * Sets the end date and time of the event.
     *
     * @param dayEnd the new end date and time of the event.
     */
    void setDayEnd(LocalDateTime dayEnd);

    /**
     * Gets the priority label of the event.
     *
     * @return the priority label of the event as an integer.
     */
    int getPriorityLabel();

    /**
     * Sets the priority label of the event.
     *
     * @param priorityLabel the new priority label of the event.
     */
    void setPriorityLabel(int priorityLabel);

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
