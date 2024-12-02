package interface_adapter.schedule;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * State for Schedule Use Case, storing event details and their corresponding buttons.
 */
public class ScheduleState {

    private final Map<String, String> eventButtonMap;
    private final Map<String, List<Object>> eventDetailsMap;

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
     * @param details   a list containing start day, start time, end day, and end time.
     */
    public void setEventDetails(String eventName, DayOfWeek startDay, LocalTime startTime, DayOfWeek endDay, LocalTime endTime) {
        eventDetailsMap.put(eventName, List.of(startDay, startTime, endDay, endTime));
    }

    /**
     * Retrieves event details by event name.
     *
     * @param eventName the name of the event
     * @return a list of event details if found, otherwise null
     */
    public List<Object> getEventDetails(String eventName) {
        return eventDetailsMap.get(eventName);
    }

    public Map<String, List<Object>> getEventDetailsMap() {
        return eventDetailsMap;
    }

    /**
     * Returns the entire event-details mapping.
     *
     * @return the event-details map
     */
    public Map<String, List<Object>> getAllEventDetails() {
        return eventDetailsMap;
    }

}
