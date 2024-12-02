package interface_adapter.delete;

import interface_adapter.ViewManagerModel;
import usecase.delete.DeleteEventOutputBoundary;
import usecase.delete.DeleteEventOutputData;

/**
 * Delete Event Presenter.
 */
public class DeleteEventPresenter implements DeleteEventOutputBoundary {
    private final DeleteEventViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public DeleteEventPresenter(DeleteEventViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void presentSuccess(DeleteEventOutputData outputData) {
        viewModel.getState().setMessage("Event \"" + outputData.getEventName() + "\" deleted successfully.");
        backToScheduleView();
    }

    @Override
    public void presentFailure(String errorMessage) {
        viewModel.getState().setMessage(errorMessage);
    }

    @Override
    public void backToScheduleView() {
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }
}
