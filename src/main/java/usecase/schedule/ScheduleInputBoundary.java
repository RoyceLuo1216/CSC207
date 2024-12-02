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

    /**
     * Executes the pop-up event view use case.
     */
    void popUpAddEventView();

    /**
     * Executes the pop-up time estimation view use case.
     */
    void popUpTimeEstimationChatbotView();

    /**
     * Executes the pop-up event conflict view use case.
     */
    void popUpEventConflictChatbotView();
}
