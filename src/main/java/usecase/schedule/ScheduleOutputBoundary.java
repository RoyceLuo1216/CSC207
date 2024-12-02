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
}
