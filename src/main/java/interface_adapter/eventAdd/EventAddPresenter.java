package interface_adapter.eventAdd;

import interface_adapter.ViewManagerModel;
import usecase.event.EventAddOutputBoundary;
import usecase.event.EventAddOutputData;

/**
 * Presenter for the Add Event Use Case.
 */
public class EventAddPresenter implements EventAddOutputBoundary {
    private final EventAddViewModel eventAddViewModel;
    private final ViewManagerModel viewManagerModel;

    public EventAddPresenter(EventAddViewModel eventAddViewModel, ViewManagerModel viewManagerModel) {
        this.eventAddViewModel = eventAddViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(EventAddOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        eventAddViewModel.firePropertyChanged("event");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final EventAddState eventAddState = eventAddViewModel.getState();
        eventAddState.setEventError(errorMessage);
        eventAddViewModel.firePropertyChanged();
    }

    @Override
    public void backToMainView() {
        viewManagerModel.setState(eventAddViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
