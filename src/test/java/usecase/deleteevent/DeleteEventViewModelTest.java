package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import interface_adapter.delete.DeleteEventState;
import interface_adapter.delete.DeleteEventViewModel;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventViewModelTest {

    @Test
    void testStateUpdate() {
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");

        DeleteEventState state = new DeleteEventState();
        state.setEventName("Christmas Brunch");
        state.setMessage("Event deleted successfully.");

        viewModel.setState(state);

        assertEquals("Christmas Brunch", viewModel.getState().getEventName());
        assertEquals("Event deleted successfully.", viewModel.getState().getMessage());
    }
}
