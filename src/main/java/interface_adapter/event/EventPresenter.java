package interface_adapter.event;

import interface_adapter.ViewManagerModel;
import usecase.event.EventOutputBoundary;
import usecase.event.EventOutputData;

/**
 * Presenter for the Add Event Use Case.
 */
public class EventPresenter implements EventOutputBoundary {
    private final EventViewModel eventViewModel;
    private final ViewManagerModel viewManagerModel;

    public EventPresenter(EventViewModel eventViewModel, ViewManagerModel viewManagerModel) {
        this.eventViewModel = eventViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(EventOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        eventViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final EventState eventState = eventViewModel.getState();
        eventState.setEditError(errorMessage);
        eventViewModel.firePropertyChanged();
    }

    @Override
    public void backToMainView() {
        viewManagerModel.setState(eventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
