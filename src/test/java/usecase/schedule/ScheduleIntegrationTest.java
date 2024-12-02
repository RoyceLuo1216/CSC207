//package usecase.schedule;
//
//import interface_adapter.schedule.SchedulePresenter;
//import interface_adapter.schedule.ScheduleState;
//import interface_adapter.schedule.ScheduleViewModel;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.Mockito;
//
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.mockito.Mockito.*;
//
//class ScheduleIntegrationTest {
//    private ScheduleDataAccessInterface dataAccessMock;
//    private ScheduleViewModel viewModel;
//    private SchedulePresenter presenter;
//    private ScheduleInteractor interactor;
//
//    @BeforeEach
//    void setUp() {
//        // Mock the data access interface
//        dataAccessMock = mock(ScheduleDataAccessInterface.class);
//
//        // Create the ViewModel
//        viewModel = new ScheduleViewModel();
//
//        // Create the presenter
//        presenter = new SchedulePresenter(viewModel);
//
//        // Create the interactor
//        interactor = new ScheduleInteractor(dataAccessMock, presenter);
//    }
//
//    @Test
//    void testRetrieveAllEventsIntegration() {
//        // Arrange
//        List<String> allEventNames = Arrays.asList("Event1", "Event2", "Event3");
//        when(dataAccessMock.getAllEventNames()).thenReturn(allEventNames);
//
//        // Act
//        ScheduleInputData inputData = new ScheduleInputData(); // Retrieve all events
//        interactor.execute(inputData);
//
//        // Assert
//        ScheduleState state = viewModel.getState();
//        assertEquals(allEventNames, state.getEventName(), "ViewModel should contain all event names.");
//        verify(dataAccessMock, times(1)).getAllEventNames();
//    }
//
//    @Test
//    void testRetrieveSpecificEventsIntegration() {
//        // Arrange
//        List<String> specificEventNames = Arrays.asList("Event1", "Event3");
//        ScheduleInputData inputData = new ScheduleInputData(java.util.Optional.of(specificEventNames));
//
//        // Act
//        interactor.execute(inputData);
//
//        // Assert
//        ScheduleState state = viewModel.getState();
//        assertEquals(specificEventNames, state.getEventNames(), "ViewModel should contain the specific event names.");
//        verify(dataAccessMock, never()).getAllEventNames(); // Should not call getAllEventNames()
//    }
//
//    @Test
//    void testNoEventsAvailableIntegration() {
//        // Arrange
//        when(dataAccessMock.getAllEventNames()).thenReturn(Collections.emptyList());
//
//        // Act
//        ScheduleInputData inputData = new ScheduleInputData(); // Retrieve all events
//        interactor.execute(inputData);
//
//        // Assert
//        ScheduleState state = viewModel.getState();
//        assertEquals(Collections.emptyList(), state.getEventNames(), "ViewModel should handle no events gracefully.");
//        verify(dataAccessMock, times(1)).getAllEventNames();
//    }
//
//    @Test
//    void testPresenterHandlesNullDataFromDataAccess() {
//        // Arrange
//        when(dataAccessMock.getAllEventNames()).thenReturn(null);
//
//        // Act
//        ScheduleInputData inputData = new ScheduleInputData(); // Retrieve all events
//        interactor.execute(inputData);
//
//        // Assert
//        ScheduleState state = viewModel.getState();
//        assertEquals(Collections.emptyList(), state.getEventNames(), "Presenter should convert null to an empty list.");
//        verify(dataAccessMock, times(1)).getAllEventNames();
//    }
//
//    @Test
//    void testEmptyInputDataIntegration() {
//        // Arrange
//        List<String> allEventNames = Arrays.asList("Event1", "Event2");
//        when(dataAccessMock.getAllEventNames()).thenReturn(allEventNames);
//
//        // Act
//        ScheduleInputData inputData = new ScheduleInputData(java.util.Optional.empty()); // Empty input
//        interactor.execute(inputData);
//
//        // Assert
//        ScheduleState state = viewModel.getState();
//        assertEquals(allEventNames, state.getEventNames(), "ViewModel should contain all events when input is empty.");
//        verify(dataAccessMock, times(1)).getAllEventNames();
//    }
//}
