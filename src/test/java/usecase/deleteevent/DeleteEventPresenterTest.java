package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import usecase.delete.DeleteEventOutputData;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventPresenterTest {

    @Test
    void testPresentSuccess() {
        DeleteEventViewModel viewModel = new DeleteEventViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel, viewManagerModel);

        presenter.presentSuccess(new DeleteEventOutputData("Christmas Brunch"));

        // Verify the success message in the ViewModel
        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage());

        // Verify the transition to the schedule view in the ViewManagerModel
        assertEquals("schedule", viewManagerModel.getState());
    }

    @Test
    void testPresentFailure() {
        DeleteEventViewModel viewModel = new DeleteEventViewModel();
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel, viewManagerModel);

        presenter.presentFailure("Failed to delete event.");

        // Verify the failure message in the ViewModel
        assertEquals("Failed to delete event.", viewModel.getState().getMessage());

        // Ensure no view transition occurred for a failure
        assertEquals("", viewManagerModel.getState());
    }

}
