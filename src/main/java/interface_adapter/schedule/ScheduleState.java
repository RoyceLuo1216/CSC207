package interface_adapter.schedule;

import java.util.HashMap;
import java.util.Map;

/**
 * State for Schedule Use Case, storing event names and their corresponding buttons.
 */
public class ScheduleState {

    private final Map<String, String> eventButtonMap;

    public ScheduleState() {
        this.eventButtonMap = new HashMap<>();
    }

    /**
     * Adds a mapping between a button ID and an event name.
     *
     * @param buttonId   the ID of the button
     * @param eventName  the name of the event
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
}
