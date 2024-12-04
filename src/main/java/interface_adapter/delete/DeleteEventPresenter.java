package interface_adapter.delete;

import interface_adapter.ViewManagerModel;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.delete.DeleteEventOutputBoundary;
import usecase.delete.DeleteEventOutputData;

/**
 * Delete Event Presenter.
 */
public class DeleteEventPresenter implements DeleteEventOutputBoundary {
    private final DeleteEventViewModel viewModel;
    private final ViewManagerModel viewManagerModel;
    private final ScheduleViewModel scheduleViewModel;

    public DeleteEventPresenter(DeleteEventViewModel viewModel, ViewManagerModel viewManagerModel,
                                ScheduleViewModel scheduleViewModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
        this.scheduleViewModel = scheduleViewModel;
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

    /**
     * Transition to edit view.
     */
    @Override
    public void editView() {
        viewManagerModel.setState("edit");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void prepareEventDetails(DeleteEventOutputData outputData) {
        DeleteEventState state = viewModel.getState();
        state.setEventName(outputData.getEventName());
        state.setMessage(outputData.getMessage());
        viewModel.firePropertyChanged("eventDetails");
    }

}
