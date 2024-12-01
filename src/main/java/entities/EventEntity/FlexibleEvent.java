package entities.EventEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an event with flexible scheduling requirements.
 */
public class FlexibleEvent implements Event {

    @JsonProperty("tasks")
    private final List<Task> tasks;
    @JsonProperty("dayStart")
    private LocalDateTime dayStart;
    @JsonProperty("dayEnd")
    private LocalDateTime dayEnd;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("priorityLabel")
    private int priorityLabel;
    @JsonProperty("timeAllocation")
    private float timeAllocation;


    public FlexibleEvent(LocalDateTime dayStart, LocalDateTime dayEnd, String eventName, int priorityLabel, float timeAllocation) {
//        this.dayStart = dayStart;
//        this.dayEnd = dayEnd;
//        this.eventName = eventName;
//        this.priorityLabel = priorityLabel;
//        this.timeAllocation = (int) timeAllocation;
//        this.tasks = new ArrayList<>();
//    }
//
//
//    // Getters and Setters for the properties
//
//    @Override
//    public String setEventName() {
//        return "";
//    }
//
//    @Override
//    public String getEventName() {
//        return this.eventName;
//    }
//
//    /**
//     * Sets the name of the event.
//     *
//     * @param name the new name of the event.
//     */
//    @Override
//    public void setEventName(String name) {
//        this.eventName = name;
//    }
//
//    @Override
//    public int getPriorityLabel() {
//        return this.priorityLabel;
//    }
//
//    /**
//     * Sets the priority label of the event.
//     *
//     * @param priorityLabel the new priority label of the event.
//     */
//    @Override
//    public void setPriorityLabel(int priorityLabel) {
//        this.priorityLabel = priorityLabel;
//    }
//
//    public float getTimeAllocation() {
//        return timeAllocation;
//    }
//
//    public LocalDateTime getDayStart() {
//        return this.dayStart;
//    }
//
//    /**
//     * Sets the start date and time of the event.
//     *
//     * @param dayStart the new start date and time of the event.
//     */
//    @Override
//    public void setDayStart(LocalDateTime dayStart) {
//        this.dayStart = dayStart;
//    }
//
//    public LocalDateTime getDayEnd() {
//        return this.dayEnd;
//    }
//
//    /**
//     * Sets the end date and time of the event.
//     *
//     * @param dayEnd the new end date and time of the event.
//     */
//    @Override
//    public void setDayEnd(LocalDateTime dayEnd) {
//        this.dayEnd = dayEnd;
//    }
//
//    @Override
//    public List<Task> getTasks() {
//        return this.tasks;
//    }
//
//    public void addTask(Task task) {
//        this.tasks.add(task);
//    }
//
//    /**
//     * Removes a task from the list.
//     *
//     * @param task
//     */
//    @Override
//    public void removeTask(Task task) {
//        this.tasks.remove(task);
//        }
    }
}
