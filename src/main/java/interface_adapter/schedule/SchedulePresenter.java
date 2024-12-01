package interface_adapter.schedule;

import usecase.schedule.ScheduleOutputBoundary;
import usecase.schedule.ScheduleOutputData;

/**
 * Presenter for the Schedule Use Case.
 */
public class SchedulePresenter implements ScheduleOutputBoundary {

    private final ScheduleViewModel viewModel;

    /**
     * Initializes the SchedulePresenter with the associated ViewModel.
     *
     * @param viewModel the ViewModel for the schedule
     */
    public SchedulePresenter(ScheduleViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the data for the ViewModel and updates the state.
     *
     * @param outputData the output data from the use case
     */
    @Override
    public void presentEvents(ScheduleOutputData outputData) {
        ScheduleState state = viewModel.getState();

        // Clear previous state
        state.getEventButtonMap().clear();

        // Populate the state with new event data
        outputData.getEventNames().forEach(eventName -> {
            String buttonId = "btn_" + eventName;
            state.addEventButton(buttonId, eventName);
        });

        // Notify the ViewModel of the updated state
        viewModel.setState(state);
    }


}
