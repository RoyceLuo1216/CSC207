package interface_adapter.event;

import usecase.event.EventOutputBoundary;
import usecase.event.EventOutputData;
public class EventPresenter implements EventOutputBoundary {
    private final EventViewModel eventViewModel;

    public EventPresenter(EventViewModel eventViewModel) {
        this.eventViewModel = eventViewModel;
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
}
