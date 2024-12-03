package interface_adapter.repeat;

import interface_adapter.ViewManagerModel;
import usecase.event.AddEventOutputData;
import usecase.repeat.RepeatOutputBoundary;
import usecase.repeat.RepeatOutputData;

/**
 * Presenter for the Repeat Event Use Case.
 */
public class RepeatPresenter implements RepeatOutputBoundary {
    private final RepeatViewModel repeatViewModel;
    private final ViewManagerModel viewManagerModel;

    public RepeatPresenter(RepeatViewModel repeatViewModel, ViewManagerModel viewManagerModel) {
        this.repeatViewModel = repeatViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(RepeatOutputData outputData) {

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
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final RepeatState eventState = repeatViewModel.getState();
        eventState.setRepeatError(errorMessage);
        repeatViewModel.firePropertyChanged();
    }

    @Override
    public void backToScheduleView() {
        // Transition to the main view
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }
}
