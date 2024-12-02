package entities.eventEntity;

import java.time.DayOfWeek;
import java.time.LocalTime;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "eventType")
@JsonSubTypes({
    @JsonSubTypes.Type(value = FixedEvent.class, name = "fixedEvent"),
    @JsonSubTypes.Type(value = RepeatEvent.class, name = "repeatEvent")
})
/**
 * Interface representing an Event with a name, start and end time, and priority label.
 */
public interface Event {

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
     * Get the starting day of an event.
     * @return the starting day of an event.
     */

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

}
