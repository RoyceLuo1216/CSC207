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
import usecase.delete.DeleteEventOutputData;
import view.DeleteEventView;

import javax.swing.*;
import java.time.DayOfWeek;
import java.time.LocalTime;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

class DeleteEventIntegrationTest {

    private InMemoryDataAccessObject inMemoryDataAccessObject;
    private ViewManagerModel viewManagerModel;
    private DeleteEventViewModel viewModel;
    private DeleteEventPresenter presenter;
    private DeleteEventController controller;
    private DeleteEventView view;

    @BeforeEach
    void setUp() {
        // Mock the InMemoryDataAccessObject
        inMemoryDataAccessObject = mock(InMemoryDataAccessObject.class);

        // Create the ViewManagerModel
        viewManagerModel = new ViewManagerModel();

        // Create the ViewModel
        viewModel = new DeleteEventViewModel();

        // Create the Presenter
        presenter = new DeleteEventPresenter(viewModel, viewManagerModel);

        // Create the Interactor using the mocks
        DeleteEventInputBoundary interactor = inputData -> {
            try {
                inMemoryDataAccessObject.deleteEvent(inputData.getEventName());
                presenter.presentSuccess(new DeleteEventOutputData(inputData.getEventName()));
            } catch (Exception e) {
                presenter.presentFailure("Failed to delete event: " + e.getMessage());
            }
        };

        // Create the Controller
        controller = new DeleteEventController(interactor);

        // Create the View and set the controller
        view = new DeleteEventView(viewModel, new JFrame());
        view.setController(controller);
    }

    @Test
    void testFullDeletionProcess_Success() {
        // Arrange
        doNothing().when(inMemoryDataAccessObject).deleteEvent("Christmas Brunch");

        // Act
        viewModel.getState().setEventName("Christmas Brunch");
        controller.execute("Christmas Brunch");

        // Assert
        verify(inMemoryDataAccessObject, times(1)).deleteEvent("Christmas Brunch");
        assertEquals("schedule", viewManagerModel.getState());
        assertEquals("Event \"Christmas Brunch\" deleted successfully.", viewModel.getState().getMessage());
    }

    @Test
    void testFullDeletionProcess_Failure() {
        // Arrange
        doThrow(new RuntimeException("Event not found"))
                .when(inMemoryDataAccessObject).deleteEvent("Nonexistent Event");

        // Act
        viewModel.getState().setEventName("Nonexistent Event");
        controller.execute("Nonexistent Event");

        // Assert
        verify(inMemoryDataAccessObject, times(1)).deleteEvent("Nonexistent Event");
        assertEquals("Failed to delete event: Event not found", viewModel.getState().getMessage());
        assertEquals("", viewManagerModel.getState());
    }
}
