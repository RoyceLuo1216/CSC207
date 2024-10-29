import java.time.LocalDateTime;

/**
 * Interface representing an Event with a name, start and end time, and priority label.
 */
public interface Event {

    /**
     * Gets the name of the event.
     *
     * @return the name of the event as a String.
     */
    String getName();

    /**
     * Gets the start date and time of the event.
     *
     * @return the start time of the event as a LocalDateTime object.
     */
    LocalDateTime getDayStart();

    /**
     * Gets the end date and time of the event.
     *
     * @return the end time of the event as a LocalDateTime object.
     */
    LocalDateTime getDayEnd();

    /**
     * Gets the priority label of the event.
     *
     * @return the priority label of the event as an integer.
     */
    int getPriorityLabel();
}
