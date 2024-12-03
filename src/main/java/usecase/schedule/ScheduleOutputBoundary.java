package usecase.schedule;

/**
 * Output Boundary for presenting schedule data.
 */
public interface ScheduleOutputBoundary {
    /**
     * Presents the retrieved events for the view.
     *
     * @param outputData data containing the list of event names.
     */
    void presentView(ScheduleOutputData outputData);

    /**
     * Executes the pop-up event view use case.
     */
    void popUpAddEventView();

    void popUpRepeatView();

    /**
     * Executes the pop-up time estimation view use case.
     */
    void popUpTimeEstimationChatbotView();

    /**
     * Executes the pop-up event conflict view use case.
     */
    void popUpEventConflictChatbotView();
}
