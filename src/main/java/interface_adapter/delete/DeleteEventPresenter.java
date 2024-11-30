package interface_adapter.delete;

import usecase.delete.DeleteEventOutputBoundary;
import usecase.delete.DeleteEventOutputData;

/**
 * Presenter for the Delete Event Use Case.
 */
public class DeleteEventPresenter implements DeleteEventOutputBoundary {
    private final DeleteEventViewModel deleteviewModel;

    public DeleteEventPresenter(DeleteEventViewModel deleteviewModel) {
        this.deleteviewModel = deleteviewModel;
    }

    @Override
    public void presentSuccess(DeleteEventOutputData outputData) {
        // Updates the ViewModel to reflect success
        final interface_adapter.editdelete.DeleteEventState deleteState = deleteviewModel.getState();

        deleteState.setMessage("Event \"" + outputData.getEventName() + "\" deleted successfully.");

    }

    @Override
    public void presentFailure(String errorMessage) {
        // Updates the ViewModel to reflect failure
        final interface_adapter.editdelete.DeleteEventState deleteState = deleteviewModel.getState();

        deleteState.setMessage(errorMessage);
        deleteviewModel.firePropertyChanged();
    }
}
