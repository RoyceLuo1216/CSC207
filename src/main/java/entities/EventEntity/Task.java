package entities.EventEntity;

import java.time.LocalDateTime;

/**
 * Class representing a Task with a name, start and end time, and priority.
 * This Task can be part of a larger Event and represents a specific time block.
 */
public class Task {

    private String name;               // Name of the task
    private LocalDateTime startTime;    // Start time of the task
    private LocalDateTime endTime;      // End time of the task
    private int priority;               // Priority of the task

    /**
     * Constructor for the Task class.
     *
     * @param name      the name of the task
     * @param startTime the start time of the task
     * @param endTime   the end time of the task
     * @param priority  the priority of the task
     */
    public Task(String name, LocalDateTime startTime, LocalDateTime endTime, int priority) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.priority = priority;
    }

    /**
     * Gets the name of the task.
     *
     * @return the name of the task as a String.
     */
    public String getTaskName() {
        return this.name;
    }

    /**
     * Sets the name of the task.
     *
     * @param name the new name of the task.
     */
    public void setTaskName(String name) {
        this.name = name;
    }

    /**
     * Gets the start time of the task.
     *
     * @return the start time of the task as a LocalDateTime object.
     */
    public LocalDateTime getStartTime() {
        return this.startTime;
    }

    /**
     * Sets the start time of the task.
     *
     * @param startTime the new start time of the task.
     */
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * Gets the end time of the task.
     *
     * @return the end time of the task as a LocalDateTime object.
     */
    public LocalDateTime getEndTime() {
        return this.endTime;
    }

    /**
     * Sets the end time of the task.
     *
     * @param endTime the new end time of the task.
     */
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /**
     * Gets the priority of the task.
     *
     * @return the priority of the task as an integer.
     */
    public int getPriority() {
        return this.priority;
    }

    /**
     * Sets the priority of the task.
     *
     * @param priority the new priority of the task.
     */
    public void setPriority(int priority) {
        this.priority = priority;
    }
}
