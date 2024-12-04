package usecase.deleteevent;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.FixedEvent;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import usecase.delete.DeleteEventInputBoundary;
import usecase.delete.DeleteEventInteractor;
import usecase.delete.DeleteEventOutputBoundary;
import usecase.delete.DeleteEventOutputData;

import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteEventIntegrationTest {

    private InMemoryDataAccessObject inMemoryDataAccessObject;
    private ViewManagerModel viewManagerModel;
    private DeleteEventViewModel viewModel;
    private DeleteEventPresenter presenter;
    private DeleteEventController controller;

    @BeforeEach
    void setUp() {
        // Initialize mocks
        inMemoryDataAccessObject = mock(InMemoryDataAccessObject.class);

        // Initialize real objects
        viewManagerModel = new ViewManagerModel();
        viewModel = new DeleteEventViewModel();

        // Initialize presenter
        presenter = new DeleteEventPresenter(viewModel, viewManagerModel);

        // Create interactor with real presenter and mock DAO
        DeleteEventInputBoundary interactor = new DeleteEventInteractor(presenter, inMemoryDataAccessObject);

        // Initialize controller
        controller = new DeleteEventController(interactor);
    }

    @Test
    void testFullDeletionProcess_Success() {
        // Arrange
        String eventName = "Christmas Brunch";

        inMemoryDataAccessObject.setCurrentEventName(eventName);

        // Mock deletion behavior
        doNothing().when(inMemoryDataAccessObject).deleteEvent(eventName);

        // Act
        controller.deleteEvent();

        // Assert
        verify(inMemoryDataAccessObject, times(1)).deleteEvent(eventName);
        assertEquals("schedule", viewManagerModel.getState());
        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage());
    }

    @Test
    void testFullDeletionProcess_Failure() {
        // Arrange
        String eventName = "Nonexistent Event";

        inMemoryDataAccessObject.setCurrentEventName(eventName);

        // Mock failure behavior
        doThrow(new RuntimeException("Event not found"))
                .when(inMemoryDataAccessObject).deleteEvent(eventName);

        // Act
        controller.deleteEvent();

        // Assert
        verify(inMemoryDataAccessObject, times(1)).deleteEvent(eventName);
        assertEquals("Failed to delete event: Event not found", viewModel.getState().getMessage());
        assertEquals("", viewManagerModel.getState());
    }
}
