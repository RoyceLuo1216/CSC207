package entities.eventEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A FixedEvent represents an event with a fixed start and end time, name.
 */
public class FixedEvent implements Event {
    @JsonProperty("dayStart")
    private DayOfWeek dayStart;
    @JsonProperty("dayEnd")
    private DayOfWeek dayEnd;
    @JsonProperty("eventName")
    private String eventName;
    @JsonProperty("timeStart")
    private LocalTime timeStart;
    @JsonProperty("timeEnd")
    private LocalTime timeEnd;

    /**
     * Constructor for fixed event class.
     * @param dayStart      the start date of the event
     * @param dayEnd        the end date of the event
     * @param eventName     the name of the event
     * @param timeStart     the start time of the event
     * @param timeEnd       the end time of the event
     */
    public FixedEvent(DayOfWeek dayStart, DayOfWeek dayEnd, String eventName, LocalTime timeStart, LocalTime timeEnd) {
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.eventName = eventName;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
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
     * Sets the start day to another value.
     *
     * @param dayStart represents the new start day for this event.
     */
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

}
