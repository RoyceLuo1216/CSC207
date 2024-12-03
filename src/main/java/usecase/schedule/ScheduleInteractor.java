package usecase.schedule;

import entities.eventEntity.Event;

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
    }

    @Override
    public void refreshSchedule() {
        // Fetch all events from the data access object
        List<Event> events = Optional.ofNullable(dataAccess.getAllEvents())
                .orElse(Collections.emptyList());
        // Prepare and send output data to the presenter
        ScheduleOutputData outputData = new ScheduleOutputData(events);
        presenter.presentView(outputData);
        System.out.println("yo");
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

    /**
     * Executes the edit view use case.
     */
    @Override
    public void editView(String eventName) {
        presenter.editView(eventName);
    }
}
