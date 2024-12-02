package interface_adapter.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

/**
 * State for Schedule Use Case, storing event details and their corresponding buttons.
 */
public class ScheduleState {

    private final Map<String, String> eventButtonMap;
    private final Map<String, EventDetails> eventDetailsMap;

    public ScheduleState() {
        this.eventButtonMap = new HashMap<>();
        this.eventDetailsMap = new HashMap<>();
    }

    /**
     * Adds a mapping between a button ID and an event name.
     *
     * @param buttonId  the ID of the button
     * @param eventName the name of the event
     */
    public void addEventButton(String buttonId, String eventName) {
        eventButtonMap.put(buttonId, eventName);
    }

    /**
     * Gets the event name associated with a button ID.
     *
     * @param buttonId the ID of the button
     * @return the event name
     */
    public String getEventName(String buttonId) {
        return eventButtonMap.get(buttonId);
    }

    /**
     * Returns the entire event-button mapping.
     *
     * @return the event-button map
     */
    public Map<String, String> getEventButtonMap() {
        return eventButtonMap;
    }

    /**
     * Adds or updates event details for a specific event name.
     *
     * @param eventName the name of the event
     * @param startDay  the start day of the event
     * @param startTime the start time of the event
     * @param endDay    the end day of the event
     * @param endTime   the end time of the event
     */
    public void addOrUpdateEventDetails(String eventName, DayOfWeek startDay, LocalTime startTime,
                                        DayOfWeek endDay, LocalTime endTime) {
        final EventDetails details = new EventDetails(startDay, startTime, endDay, endTime);
        eventDetailsMap.put(eventName, details);
    }

    /**
     * Retrieves event details by event name.
     *
     * @param eventName the name of the event
     * @return the EventDetails object if found, otherwise null
     */
    public EventDetails getEventDetails(String eventName) {
        return eventDetailsMap.get(eventName);
    }

    /**
     * Returns the entire event-details mapping.
     *
     * @return the event-details map
     */
    public Map<String, EventDetails> getAllEventDetails() {
        return new HashMap<>(eventDetailsMap);
    }

    /**
     * Class to encapsulate event details.
     */
    public static class EventDetails {
        private final DayOfWeek startDay;
        private final LocalTime startTime;
        private final DayOfWeek endDay;
        private final LocalTime endTime;

        public EventDetails(DayOfWeek startDay, LocalTime startTime, DayOfWeek endDay, LocalTime endTime) {
            this.startDay = startDay;
            this.startTime = startTime;
            this.endDay = endDay;
            this.endTime = endTime;
        }

        public DayOfWeek getStartDay() {
            return startDay;
        }

        public LocalTime getStartTime() {
            return startTime;
        }

        public DayOfWeek getEndDay() {
            return endDay;
        }

        public LocalTime getEndTime() {
            return endTime;
        }

        @Override
        public String toString() {
            return "EventDetails{" + "startDay=" + startDay
                    + ", startTime=" + startTime
                    + ", endDay=" + endDay
                    + ", endTime="
                    + endTime + '}';
        }
    }
}
