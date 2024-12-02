package interface_adapter.addEvent;

import usecase.event.AddEventOutputBoundary;
import usecase.event.AddEventOutputData;

/**
 * Presenter for the Add Event Use Case.
 */
public class AddEventPresenter implements AddEventOutputBoundary {
    private final AddViewModel eventViewModel;

    public AddEventPresenter(AddViewModel eventViewModel) {
        this.eventViewModel = eventViewModel;
    }

    @Override
    public void prepareSuccessView(AddEventOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        eventViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final AddEventState eventState = eventViewModel.getState();
        eventState.setEditError(errorMessage);
        eventViewModel.firePropertyChanged();
    }
}
