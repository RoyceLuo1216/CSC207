package usecase.schedule;

import java.util.List;

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
        List<String> retrievedEventNames;

        if (inputData.getEventNames().isPresent() && !inputData.getEventNames().get().isEmpty()) {
            // Retrieve specific event names
            retrievedEventNames = inputData.getEventNames().get();
        } else {
            // Retrieve all event names
            retrievedEventNames = dataAccess.getAllEventNames();
        }

        ScheduleOutputData outputData = new ScheduleOutputData(retrievedEventNames);
        presenter.presentEvents(outputData);
    }
}
