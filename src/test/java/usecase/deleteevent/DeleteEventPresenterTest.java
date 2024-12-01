package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import interface_adapter.delete.DeleteEventState;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import usecase.delete.DeleteEventOutputData;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventPresenterTest {

    @Test
    void testPresentSuccess() {
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel);

        presenter.presentSuccess(new DeleteEventOutputData("Christmas Brunch"));

        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage());
    }

    @Test
    void testPresentFailure() {
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel);

        presenter.presentFailure("Failed to delete event.");

        assertEquals("Failed to delete event.", viewModel.getState().getMessage());
    }
}
