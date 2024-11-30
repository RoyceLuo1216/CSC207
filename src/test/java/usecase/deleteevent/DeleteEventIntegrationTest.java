package usecase.deleteevent;

import org.junit.jupiter.api.Test;
import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import entities.EventEntity.FixedEvent;
import data_access.Schedule;
import usecase.delete.DeleteEventOutputData;
import view.DeleteEventView;

import javax.swing.*;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class DeleteEventIntegrationTest {

    @Test
    void testFullDeletionProcess() {
        // Arrange: Create entities
        Schedule schedule = new Schedule();
        FixedEvent event = new FixedEvent(
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 12, 0),
                "Christmas Brunch",
                1
        );
        schedule.addEvent(event);

        // Arrange: Create adapter layers
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");
        DeleteEventPresenter presenter = new DeleteEventPresenter(viewModel);
        DeleteEventController controller = new DeleteEventController(inputData -> {
            boolean success = schedule.removeEvent(inputData.getEventName());
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
        assertTrue(schedule.getEvents().isEmpty());
        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage());
    }
}

