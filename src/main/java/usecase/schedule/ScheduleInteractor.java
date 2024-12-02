package usecase.schedule;

import interface_adapter.schedule.ScheduleState;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Interactor for handling schedule-related use cases.
 */
public class ScheduleInteractor implements ScheduleInputBoundary {

    private final ScheduleOutputBoundary presenter;
    private final ScheduleDataAccessInterface dataAccess;

    /**
     * Constructor initializes the interactor with data access and presenter.
     *
     * @param dataAccess the data access interface for retrieving schedule data.
     * @param presenter the output boundary for presenting data to the view.
     */
    public ScheduleInteractor(ScheduleDataAccessInterface dataAccess, ScheduleOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(ScheduleInputData inputData) {
        List<String> eventNames;
        if (inputData.getEventNames().isPresent()) {
            eventNames = inputData.getEventNames().get();
        } else {
            // Handle potential null return value from dataAccess
            eventNames = Optional.ofNullable(dataAccess.getAllEventNames()).orElse(Collections.emptyList());
        }

        // Prepare and send output data
        ScheduleOutputData outputData = new ScheduleOutputData(eventNames);
        presenter.presentView(outputData);
    }

    /**
     * Executes the pop-up event view use case.
     */
    @Override
    public void popUpAddEventView() {
        presenter.popUpAddEventView();
    }

    /**
     * Executes the pop-up time estimation view use case.
     */
    @Override
    public void popUpTimeEstimationChatbotView() {
        presenter.popUpTimeEstimationChatbotView();
    }

    /**
     * Executes the pop-up event conflict view use case.
     */
    @Override
    public void popUpEventConflictChatbotView() {
        presenter.popUpEventConflictChatbotView();
    }
}
