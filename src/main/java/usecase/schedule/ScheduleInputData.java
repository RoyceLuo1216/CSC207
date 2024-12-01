package usecase.schedule;

import java.util.List;
import java.util.Optional;

/**
 * Input Data for schedule use case.
 */
public class ScheduleInputData {
    private final Optional<List<String>> eventNames;

    /**
     * Constructor for ScheduleInputData.
     *
     * @param eventNames an optional list of event names to retrieve.
     */
    public ScheduleInputData(Optional<List<String>> eventNames) {
        this.eventNames = eventNames;
    }

    /**
     * Gets the optional list of event names.
     *
     * @return the optional list of event names.
     */
    public Optional<List<String>> getEventNames() {
        return eventNames;
    }
}
