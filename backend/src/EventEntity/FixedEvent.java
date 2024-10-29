package EventEntity;

import java.time.LocalDateTime;

/**
 * A FixedEvent represents an event with a fixed start and end time, name, and priority.
 */
public class FixedEvent implements Event {

    private LocalDateTime dayStart;
    private LocalDateTime dayEnd;
    private String eventName;
    private int priorityLabel;

    /**
     * Constructor for the FixedEvent class.
     *
     * @param dayStart      the start date and time of the event
     * @param dayEnd        the end date and time of the event
     * @param eventName     the name of the event
     * @param priorityLabel the priority label of the event
     */
    public FixedEvent(LocalDateTime dayStart, LocalDateTime dayEnd, String eventName, int priorityLabel) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.eventName = eventName;
        this.priorityLabel = priorityLabel;
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
}
