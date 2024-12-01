package interface_adapter.schedule;

import usecase.schedule.ScheduleInputBoundary;
import usecase.schedule.ScheduleInputData;

/**
 * Controller for handling schedule-related actions.
 */
public class ScheduleController {

    private final ScheduleInputBoundary interactor;

    /**
     * Constructor initializes the interactor for schedule actions.
     *
     * @param interactor the interactor for schedule use case.
     */
    public ScheduleController(ScheduleInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Triggers the schedule retrieval process.
     */
    public void getSchedule() {
        ScheduleInputData inputData = new ScheduleInputData();
        interactor.execute(inputData);
    }
}