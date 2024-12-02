package interface_adapter.schedule;

import interface_adapter.ViewManagerModel;
import usecase.schedule.ScheduleOutputBoundary;
import usecase.schedule.ScheduleOutputData;

/**
 * Presenter for the Schedule Use Case.
 */
public class SchedulePresenter implements ScheduleOutputBoundary {

    private final ScheduleViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Initializes the SchedulePresenter with the associated ViewModel.
     *
     * @param viewModel the ViewModel for the schedule
     * @param viewManagerModel the viewManagerModel
     */
    public SchedulePresenter(ScheduleViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    /**
     * Prepares the data for the ViewModel and updates the state.
     *
     * @param outputData the output data from the use case
     */
    @Override
    public void presentView(ScheduleOutputData outputData) {
        final ScheduleState state = viewModel.getState();

        // Clear previous state
        state.getEventButtonMap().clear();

        // Populate the state with new event data
        outputData.getEventNames().forEach(eventName -> {
            final String buttonId = "btn_" + eventName;
            state.addEventButton(buttonId, eventName);
        });

        // Notify the ViewModel of the updated state
        viewModel.setState(state);
    }

}
