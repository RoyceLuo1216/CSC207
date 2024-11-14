package EventEntity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event with flexible scheduling requirements.
 */
public class FlexibleEvent implements Event {

    private LocalDateTime dayStart;
    private LocalDateTime dayEnd;
    private String eventName;
    private int priorityLabel;
    private final float timeAllocation;
    private final List<Task> tasks;


    public FlexibleEvent(LocalDateTime dayStart, LocalDateTime dayEnd, String eventName, int priorityLabel, float timeAllocation) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.eventName = eventName;
        this.priorityLabel = priorityLabel;
        this.timeAllocation = timeAllocation;
        this.tasks = new ArrayList<>();
    }


    // Getters and Setters for the properties

    @Override
    public String setEventName() {
        return "";
    }

    @Override
    public String getEventName() {
        return this.eventName;
    }

    /**
     * Sets the name of the event.
     *
     * @param name the new name of the event.
     */
    @Override
    public void setEventName(String name) {
        this.eventName = name;
    }

    @Override
    public int getPriorityLabel() {
        return this.priorityLabel;
    }

    /**
     * Sets the priority label of the event.
     *
     * @param priorityLabel the new priority label of the event.
     */
    @Override
    public void setPriorityLabel(int priorityLabel) {
        this.priorityLabel = priorityLabel;
    }

    public float getTimeAllocation() {
        return timeAllocation;
    }

    public LocalDateTime getDayStart() {
        return this.dayStart;
    }

    /**
     * Sets the start date and time of the event.
     *
     * @param dayStart the new start date and time of the event.
     */
    @Override
    public void setDayStart(LocalDateTime dayStart) {
        this.dayStart = dayStart;
    }

    public LocalDateTime getDayEnd() {
        return this.dayEnd;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param dayEnd the new end date and time of the event.
     */
    @Override
    public void setDayEnd(LocalDateTime dayEnd) {
        this.dayEnd = dayEnd;
    }

    @Override
    public List<Task> getTasks() {
        return this.tasks;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the list.
     *
     * @param task
     */
    @Override
    public void removeTask(Task task) {
        this.tasks.remove(task);
    }
}
