package interface_adapter.addEvent;

import interface_adapter.ViewManagerModel;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.event.AddEventOutputBoundary;
import usecase.event.AddEventOutputData;

/**
 * Presenter for the Add Event Use Case.
 */
public class AddEventPresenter implements AddEventOutputBoundary {
    private final AddEventViewModel eventViewModel;
    private final ViewManagerModel viewManagerModel;
    private final ScheduleViewModel scheduleViewModel;

    public AddEventPresenter(AddEventViewModel eventViewModel, ScheduleViewModel scheduleViewModel, ViewManagerModel viewManagerModel) {
        this.eventViewModel = eventViewModel;
        this.viewManagerModel = viewManagerModel;
        this.scheduleViewModel = new ScheduleViewModel();
    }
    /**
     * Prepares the success view for the Edit use case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(AddEventOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their new event was saved.
        // On success, switch to main view.
        // Transition to the main view
        scheduleViewModel.firePropertyChanged();
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final AddEventState eventState = eventViewModel.getState();
        eventState.setEventError(errorMessage);
        eventViewModel.firePropertyChanged();
    }

    @Override
    public void backToScheduleView() {
        // Transition to the main view
        scheduleViewModel.firePropertyChanged();
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }

}
