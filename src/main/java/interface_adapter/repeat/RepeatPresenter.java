package interface_adapter.repeat;

import usecase.repeat.RepeatOutputBoundary;
import usecase.repeat.RepeatOutputData;

/**
 * Presenter for the Repeat Event Use Case.
 */
public class RepeatPresenter implements RepeatOutputBoundary {
    private final RepeatViewModel repeatViewModel;

    public RepeatPresenter(RepeatViewModel editViewModel) {
        this.repeatViewModel = editViewModel;
    }

    @Override
    public void prepareSuccessView(RepeatOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        repeatViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final RepeatState editState = repeatViewModel.getState();
        editState.setEditError(errorMessage);
        repeatViewModel.firePropertyChanged();
    }
}