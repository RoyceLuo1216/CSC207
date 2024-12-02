package usecase.deleteevent;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.FixedEvent;
import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.delete.DeleteEventInputBoundary;
import usecase.delete.DeleteEventOutputData;
import view.DeleteEventView;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.mockito.Mockito.*;

class DeleteEventIntegrationTest {
    private InMemoryDataAccessObject inMemoryDataAccessObject;
    private DeleteEventViewModel viewModel;
    private DeleteEventPresenter presenter;
    private DeleteEventController controller;
    private DeleteEventView view;

    @BeforeEach
    void setUp() {
        // Mock the InMemoryDataAccessObject
        inMemoryDataAccessObject = mock(InMemoryDataAccessObject.class);

        // Mock the presenter
        presenter = mock(DeleteEventPresenter.class);

        // Create the ViewModel
        viewModel = new DeleteEventViewModel("delete");

        // Create the interactor using the mocks
        DeleteEventInputBoundary interactor = inputData -> {
            try {
                inMemoryDataAccessObject.deleteEvent(inputData.getEventName());
                presenter.presentSuccess(new DeleteEventOutputData(inputData.getEventName()));
            } catch (Exception e) {
                presenter.presentFailure("Failed to delete event: " + e.getMessage());
            }
        };

        // Create the controller
        controller = new DeleteEventController(interactor);

        // Create the view with mock callbacks and set the controller afterward
        view = new DeleteEventView(viewModel,
                () -> System.out.println("Back to schedule"), // Mock callback for returning to schedule
                () -> System.out.println("Schedule refreshed") // Mock callback for refreshing schedule
        );
        view.setController(controller);
    }

    @Test
    void testFullDeletionProcess_Success() {
        // Arrange: Add an event to the mock DAO and configure its behavior
        FixedEvent event = new FixedEvent(
                DayOfWeek.WEDNESDAY,
                DayOfWeek.WEDNESDAY,
                "Christmas Brunch",
                LocalTime.of(10, 0),
                LocalTime.of(12, 0)
        );

        // Mock deleteEvent to do nothing (void method)
        doNothing().when(inMemoryDataAccessObject).deleteEvent("Christmas Brunch");

        // Act: Simulate user deleting the event
        viewModel.getState().setEventName("Christmas Brunch");
        controller.execute("Christmas Brunch");

        // Assert: Verify interactions and validate behavior
        verify(inMemoryDataAccessObject, times(1)).deleteEvent("Christmas Brunch");
        verify(presenter, times(1)).presentSuccess(argThat(outputData ->
                outputData.getEventName().equals("Christmas Brunch")));
    }

    @Test
    void testFullDeletionProcess_Failure() {
        // Arrange: Configure the mock DAO to throw an exception
        doThrow(new RuntimeException("Event not found"))
                .when(inMemoryDataAccessObject).deleteEvent("Nonexistent Event");

        // Act: Attempt to delete a non-existent event
        viewModel.getState().setEventName("Nonexistent Event");
        controller.execute("Nonexistent Event");

        // Assert: Verify interactions and validate behavior
        verify(inMemoryDataAccessObject, times(1)).deleteEvent("Nonexistent Event");
        verify(presenter, times(1)).presentFailure(contains("Failed to delete event: Event not found"));
    }
}
