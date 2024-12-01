package usecase.schedule;

import java.util.List;

/**
 * Output data containing event names for presentation.
 */
public class ScheduleOutputData {
    private final List<String> eventNames;

    public ScheduleOutputData(List<String> eventNames) {
        this.eventNames = eventNames;
    }

    public List<String> getEventNames() {
        return eventNames;
    }
}
