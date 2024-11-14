package entities.EventEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A RepeatEvent represents a repeated event with  start/end time, name, and priority.
 */
public class RepeatEvent implements Event {

    private LocalDateTime dayStart;
    private LocalDateTime dayEnd;
    private String eventName;
    private int priorityLabel;
    private final List<Task> tasks;
    private final List<LocalDateTime> daysRepeated;

    /**
     * Constructor for the RepeatEvent class.
     *
     * @param dayStart      the start date and time of the event
     * @param dayEnd        the end date and time of the event
     * @param eventName     the name of the event
     * @param priorityLabel the priority label of the event
     * @param daysRepeated  the days that the event is repeated
     */
    public RepeatEvent(LocalDateTime dayStart, LocalDateTime dayEnd, String eventName, int priorityLabel, List<LocalDateTime> daysRepeated) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.eventName = eventName;
        this.priorityLabel = priorityLabel;
        this.tasks = new ArrayList<>();
        this.daysRepeated = daysRepeated;
    }

    /**
     * Gets the name of the event.
     *
     * @return the name of the event as a String.
     */
    @Override
    public String setEventName() {
        return this.eventName;
    }


    /**
     * Gets the name of the event.
     *
     * @return the name of the event as a String.
     */
    @Override
    public String getEventName() {
        return this.eventName;
    }

    /**
     * Sets the event name to a new value.
     *
     * @param eventName the new name for the event.
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * Gets the start date and time of the event.
     *
     * @return the start time of the event as a LocalDateTime object.
     */
    @Override
    public LocalDateTime getDayStart() {
        return this.dayStart;
    }

    /**
     * Sets the start day to another value.
     *
     * @param dayStart represents the new start day for this event.
     */
    public void setDayStart(LocalDateTime dayStart) {
        this.dayStart = dayStart;
    }

    /**
     * Gets the end date and time of the event.
     *
     * @return the end time of the event as a LocalDateTime object.
     */
    @Override
    public LocalDateTime getDayEnd() {
        return this.dayEnd;
    }

    /**
     * Sets the end day to a new value.
     *
     * @param dayEnd represents the new end day for this event.
     */
    public void setDayEnd(LocalDateTime dayEnd) {
        this.dayEnd = dayEnd;
    }

    /**
     * Gets the priority label of the event.
     *
     * @return the priority label of the event as an integer.
     */
    @Override
    public int getPriorityLabel() {
        return this.priorityLabel;
    }

    /**
     * Sets the priority label to a new value.
     *
     * @param priorityLabel the new priority label for this event.
     */
    public void setPriorityLabel(int priorityLabel) {
        this.priorityLabel = priorityLabel;
    }

    /**
     * Returns the list of tasks associated with the event.
     *
     * @return
     */
    @Override
    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Adds a Task to the list
     *
     * @param task, task object to add to the event.
     */
    @Override
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param task, task object that gets removed.
     */
    @Override
    public void removeTask(Task task) {
        this.tasks.remove(task);
    }

}
