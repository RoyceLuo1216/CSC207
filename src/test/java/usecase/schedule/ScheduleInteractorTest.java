package usecase.schedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ScheduleInteractorTest {
    private ScheduleDataAccessInterface dataAccessMock;
    private ScheduleOutputBoundary presenterMock;
    private ScheduleInteractor interactor;

    @BeforeEach
    void setUp() {
        dataAccessMock = mock(ScheduleDataAccessInterface.class);
        presenterMock = mock(ScheduleOutputBoundary.class);
        interactor = new ScheduleInteractor(dataAccessMock, presenterMock);
    }

    @Test
    void testRetrieveAllEvents() {
        // Arrange
        List<String> allEventNames = Arrays.asList("Event1", "Event2", "Event3");
        when(dataAccessMock.getAllEventNames()).thenReturn(allEventNames);

        ScheduleInputData inputData = new ScheduleInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccessMock, times(1)).getAllEventNames();
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock, times(1)).presentView(captor.capture());

        List<String> retrievedEventNames = captor.getValue().getEventNames();
        assertEquals(allEventNames, retrievedEventNames, "All event names should be retrieved and presented.");
    }

    @Test
    void testRetrieveSpecificEvents() {
        // Arrange
        List<String> specificEventNames = Arrays.asList("Event1", "Event3");
        ScheduleInputData inputData = new ScheduleInputData(Optional.of(specificEventNames));

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccessMock, never()).getAllEventNames();
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock, times(1)).presentView(captor.capture());

        List<String> retrievedEventNames = captor.getValue().getEventNames();
        assertEquals(specificEventNames, retrievedEventNames, "Specific event names should match the provided input.");
    }

    @Test
    void testNoEventsAvailable() {
        // Arrange
        when(dataAccessMock.getAllEventNames()).thenReturn(Collections.emptyList());
        ScheduleInputData inputData = new ScheduleInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccessMock, times(1)).getAllEventNames();
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock, times(1)).presentView(captor.capture());

        List<String> retrievedEventNames = captor.getValue().getEventNames();
        assertEquals(0, retrievedEventNames.size(), "No events should be returned if none are available.");
    }

    @Test
    void testNullEventListFromDataAccess() {
        // Arrange
        when(dataAccessMock.getAllEventNames()).thenReturn(null);
        ScheduleInputData inputData = new ScheduleInputData();

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccessMock, times(1)).getAllEventNames();
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock, times(1)).presentView(captor.capture());

        List<String> retrievedEventNames = captor.getValue().getEventNames();
        assertEquals(Collections.emptyList(), retrievedEventNames, "Presenter should handle null as an empty list.");
    }

    @Test
    void testEmptyInputData() {
        // Arrange
        ScheduleInputData inputData = new ScheduleInputData(Optional.empty());
        List<String> allEventNames = Arrays.asList("Event1", "Event2");
        when(dataAccessMock.getAllEventNames()).thenReturn(allEventNames);

        // Act
        interactor.execute(inputData);

        // Assert
        verify(dataAccessMock, times(1)).getAllEventNames();
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock, times(1)).presentView(captor.capture());

        List<String> retrievedEventNames = captor.getValue().getEventNames();
        assertEquals(allEventNames, retrievedEventNames, "All events should be retrieved when input is empty.");
    }
}
