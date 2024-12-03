package interface_adapter.repeat;

import interface_adapter.ViewManagerModel;
import usecase.repeat.RepeatOutputBoundary;
import usecase.repeat.RepeatOutputData;

/**
 * Presenter for the Repeat Event Use Case.
 */
public class RepeatPresenter implements RepeatOutputBoundary {
    private final RepeatViewModel repeatViewModel;
    private final ViewManagerModel viewManagerModel;

    public RepeatPresenter(RepeatViewModel editViewModel, ViewManagerModel viewManagerModel) {
        this.repeatViewModel = editViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(RepeatOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        repeatViewModel.firePropertyChanged("repeat");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final RepeatState editState = repeatViewModel.getState();
        editState.setEditError(errorMessage);
        repeatViewModel.firePropertyChanged();
    }

    @Override
    public void backToScheduleView() {
        viewManagerModel.setState(repeatViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
