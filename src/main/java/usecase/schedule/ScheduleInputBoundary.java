package usecase.schedule;

/**
 * Input Boundary for schedule-related actions.
 */
public interface ScheduleInputBoundary {
    /**
     * Retrieves events based on the given input data.
     *
     * @param inputData the input data containing optional event names to retrieve.
     */
    void execute(ScheduleInputData inputData);
}
