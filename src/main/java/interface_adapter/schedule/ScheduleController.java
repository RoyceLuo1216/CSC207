package interface_adapter.schedule;

import usecase.schedule.ScheduleInputBoundary;
import usecase.schedule.ScheduleInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;

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
        ScheduleInputData inputData = new ScheduleInputData(null);
        interactor.execute(inputData);
    }

    /**
     * Refreshes the schedule.
     */
    public void refreshSchedule() {
        interactor.refreshSchedule();
    }

    /**
     * Executes the pop-up event view use case.
     */
    public void popUpAddEventView() {
        interactor.popUpAddEventView();
    }

    /**
     * Executes the pop-up time estimation view use case.
     */
    public void popUpTimeEstimationChatbotView() {
        interactor.popUpTimeEstimationChatbotView();
    }

    /**
     * Executes the pop-up event conflict view use case.
     */
    public void popUpEventConflictChatbotView() {
        interactor.popUpEventConflictChatbotView();
    }

    /**
     * Executes editview use case.
     * @param eventName name of the event.
     */
    public void editView(String eventName) {
        interactor.setCurrentEvent(eventName);
        interactor.editView(eventName);
    }

    public void resetScheduleState() {
        interactor.refreshScheduleState();
    }


}
