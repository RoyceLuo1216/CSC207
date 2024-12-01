package usecase.deleteevent;

import data_access.InMemoryAddDataAccessObject;
import org.junit.jupiter.api.Test;
import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import entities.eventEntity.FixedEvent;
import usecase.delete.DeleteEventOutputData;
import view.DeleteEventView;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventIntegrationTest {

    @Test
    void testFullDeletionProcess() {
        // Arrange: Create entities
        InMemoryAddDataAccessObject inMemoryDataAccessObject = new InMemoryAddDataAccessObject();
        FixedEvent event = new FixedEvent(
                DayOfWeek.WEDNESDAY,               // Start day
                DayOfWeek.WEDNESDAY,               // End day
                "Christmas Brunch",                // Event name
                LocalTime.of(10, 0),               // Start time
                LocalTime.of(12, 0)                // End time
        );
        inMemoryDataAccessObject.addEvent(event);

        // Arrange: Create adapter layers
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel);
        DeleteEventController controller = new DeleteEventController(inputData -> {
            boolean success = inMemoryDataAccessObject.removeEvent(inputData.getEventName());
            if (success) {
                presenter.presentSuccess(new DeleteEventOutputData(inputData.getEventName()));
            } else {
                presenter.presentFailure("Failed to delete event.");
            }
        });

        // Arrange: Create view
        DeleteEventView view = new DeleteEventView(controller, viewModel,
                () -> System.out.println("Back to schedule"), // Mock callback
                () -> System.out.println("Schedule refreshed")); // Mock callback

        // Act: Simulate user deleting the event
        viewModel.getState().setEventName("Christmas Brunch");
        controller.execute("Christmas Brunch");

        // Assert: Validate results
        assertTrue(inMemoryDataAccessObject.getEvents().isEmpty(), "Event should be removed from the schedule.");
        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage(),
                "Success message should match the expected output.");
    }
}
