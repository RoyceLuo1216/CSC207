package usecase.delete;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.FixedEvent;
import interface_adapter.ViewManagerModel;
import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import usecase.delete.DeleteEventInputBoundary;
import usecase.delete.DeleteEventInputData;
import usecase.delete.DeleteEventOutputData;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class DeleteEventInteractorTest {

    private InMemoryDataAccessObject dataAccessObjectMock;
    private ViewManagerModel viewManagerModel;
    private DeleteEventViewModel viewModel;
    private DeleteEventPresenter presenterMock;
    private DeleteEventInteractor interactor;

    @BeforeEach
    void setUp() {
        // Mock dependencies
        dataAccessObjectMock = mock(InMemoryDataAccessObject.class);
        viewManagerModel = new ViewManagerModel();
        viewModel = new DeleteEventViewModel();
        presenterMock = mock(DeleteEventPresenter.class);

        // Create the interactor with the mocked presenter and data access object
        interactor = new DeleteEventInteractor(presenterMock, dataAccessObjectMock);
    }

    @Test
    void testExecute_Success() {
        // Arrange
        String eventName = "Birthday Party";
        DeleteEventInputData inputData = new DeleteEventInputData(eventName);
        doNothing().when(dataAccessObjectMock).deleteEvent(eventName);

        // Act
        interactor.execute(inputData);

        // Assert
        ArgumentCaptor<DeleteEventOutputData> captor = ArgumentCaptor.forClass(DeleteEventOutputData.class);
        verify(presenterMock).presentSuccess(captor.capture());
        assertEquals(eventName, captor.getValue().getEventName());
        verify(dataAccessObjectMock).deleteEvent(eventName);
    }

    @Test
    void testFetchEventDetails_Success() {
        // Arrange
        String eventName = "Wedding";
        when(dataAccessObjectMock.getCurrentEventName()).thenReturn(eventName);

        // Act
        interactor.fetchEventDetails();

        // Assert
        ArgumentCaptor<DeleteEventOutputData> captor = ArgumentCaptor.forClass(DeleteEventOutputData.class);
        verify(presenterMock).prepareEventDetails(captor.capture());
        assertEquals(eventName, captor.getValue().getEventName());
    }

    @Test
    void testFetchEventDetails_Failure() {
        // Arrange
        when(dataAccessObjectMock.getCurrentEventName()).thenReturn(null);

        // Act
        interactor.fetchEventDetails();

        // Assert
        verify(presenterMock).presentFailure("No current event found.");
    }

    @Test
    void testDeleteEvent_Success() {
        // Arrange
        String eventName = "Conference";
        when(dataAccessObjectMock.getCurrentEventName()).thenReturn(eventName);
        doNothing().when(dataAccessObjectMock).deleteEvent(eventName);

        // Act
        interactor.deleteEvent();

        // Assert
        ArgumentCaptor<DeleteEventOutputData> captor = ArgumentCaptor.forClass(DeleteEventOutputData.class);
        verify(presenterMock).presentSuccess(captor.capture());
        assertEquals(eventName, captor.getValue().getEventName());
        verify(dataAccessObjectMock).deleteEvent(eventName);
    }

    @Test
    void testDeleteEvent_Failure() {
        // Arrange
        when(dataAccessObjectMock.getCurrentEventName()).thenReturn(null);

        // Act
        interactor.deleteEvent();

        // Assert
        verify(presenterMock).presentFailure("No current event to delete.");
    }

    @Test
    void testScheduleView() {
        // Act
        interactor.scheduleView();

        // Assert
        verify(presenterMock).backToScheduleView();
    }

    @Test
    void testEditView() {
        // Act
        interactor.editView();

        // Assert
        verify(presenterMock).editView();
    }
}
