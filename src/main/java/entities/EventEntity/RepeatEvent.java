package entities.EventEntity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * A RepeatEvent represents a repeated event with  start/end time, name, and priority.
 */
public class RepeatEvent implements Event {

    @JsonProperty("tasks")
    private final List<Task> tasks;
    @JsonProperty("dayStart")
    private DayOfWeek dayStart;
    @JsonProperty("dayEnd")
    private DayOfWeek dayEnd;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("daysRepeated")
    private List<DayOfWeek> daysRepeated;
    @JsonProperty("timeStart")
    private LocalTime timeStart;
    @JsonProperty("timeEnd")
    private LocalTime timeEnd;

    /**
     * Constructor for the RepeatEvent class.
     *
     * @param dayStart      the start date and time of the event
     * @param dayEnd        the end date and time of the event
     * @param eventName     the name of the event
     * @param daysRepeated  the days that the event is repeated
     */
    public RepeatEvent(DayOfWeek dayStart, DayOfWeek dayEnd, String eventName, LocalTime timeStart, LocalTime timeEnd,
                                                                                    List<DayOfWeek> daysRepeated) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.eventName = eventName;
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
    public DayOfWeek getDayStart() {
        return this.dayStart;
    }

    /**
     * Sets the starting day of the event.
     *
     * @param dayStart the new start date of the event.
     */
    @Override
    public void setDayStart(DayOfWeek dayStart) {
        this.dayStart = dayStart;
    }

    /**
     * Gets the end date and time of the event.
     *
     * @return the end time of the event as a LocalDateTime object.
     */
    @Override
    public DayOfWeek getDayEnd() {
        return this.dayEnd;
    }

    /**
     * Sets the ending day of the event.
     *
     * @param dayEnd the new end date of the event.
     */
    @Override
    public void setDayEnd(DayOfWeek dayEnd) {
        this.dayEnd = dayEnd;
    }

    /**
     * Gets the start time of the event.
     *
     * @return the start time of the event as a DayOfWeek.
     */
    @Override
    public LocalTime getTimeStart() {
        return timeStart;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param timeStart the new start time of the event.
     */
    @Override
    public void setTimeStart(LocalTime timeStart) {
        this.timeStart = timeStart;
    }

    /**
     * Gets the end time of the event.
     *
     * @return the end time of the event as a DayOfWeek.
     */
    @Override
    public LocalTime getTimeEnd() {
        return timeEnd;
    }

    /**
     * Sets the end date and time of the event.
     *
     * @param timeEnd the new end time of the event.
     */
    @Override
    public void setTimeEnd(LocalTime timeEnd) {
        this.timeEnd = timeEnd;
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


    public List<DayOfWeek> getDaysRepeated() {
        return this.daysRepeated;
    }

    public void setDaysRepeated(List<DayOfWeek> daysRepeated) {
        this.daysRepeated = daysRepeated;
    }

}
