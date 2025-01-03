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
     * refresh schedule.
     */
    void refreshSchedule();

    /**
     * Executes the pop-up time estimation view use case.
     */
    void popUpTimeEstimationChatbotView();

    /**
     * Executes the pop-up event conflict view use case.
     */
    void popUpEventConflictChatbotView();

    /**
     * Executes the edit view use case.
     * @param eventName name of event to edit.
     */
    void editView(String eventName);

    /**
     * Sets current event to desired event by name.
     * @param eventName name of event.
     */
    void setCurrentEvent(String eventName);

    /**
     * Resets the schedule state.
     */
    void refreshScheduleState();
}
