package interface_adapter.edit;

import usecase.edit.EditOutputBoundary;
import usecase.edit.EditOutputData;

public class EditPresenter implements EditOutputBoundary {
    private final EditViewModel editViewModel;

    public EditPresenter(EditViewModel editViewModel) {
        this.editViewModel = editViewModel;
    }

    @Override
    public void prepareSuccessView(EditOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        editViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final EditState editState = editViewModel.getState();
        editState.setEditError(errorMessage);
        editViewModel.firePropertyChanged();
    }
}
