package usecase.schedule;

import entities.eventEntity.Event;
import interface_adapter.schedule.*;
import usecase.schedule.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.*;

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
    void testRefreshSchedule_Success() {
        // Arrange
        List<Event> events = Arrays.asList(
                mockEvent("Meeting", DayOfWeek.MONDAY, LocalTime.of(9, 0), DayOfWeek.MONDAY, LocalTime.of(10, 0)),
                mockEvent("Lunch", DayOfWeek.TUESDAY, LocalTime.of(12, 0), DayOfWeek.TUESDAY, LocalTime.of(13, 0))
        );
        when(dataAccessMock.getAllEvents()).thenReturn(events);

        // Act
        interactor.refreshSchedule();

        // Assert
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock).presentView(captor.capture());
        assertEquals(events, captor.getValue().getEventNames());
    }

    @Test
    void testRefreshSchedule_NoEvents() {
        // Arrange
        when(dataAccessMock.getAllEvents()).thenReturn(Collections.emptyList());

        // Act
        interactor.refreshSchedule();

        // Assert
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock).presentView(captor.capture());
        assertEquals(0, captor.getValue().getEventNames().size());
    }

    @Test
    void testRefreshSchedule_NullEvents() {
        // Arrange
        when(dataAccessMock.getAllEvents()).thenReturn(null);

        // Act
        interactor.refreshSchedule();

        // Assert
        ArgumentCaptor<ScheduleOutputData> captor = ArgumentCaptor.forClass(ScheduleOutputData.class);
        verify(presenterMock).presentView(captor.capture());
        assertEquals(0, captor.getValue().getEventNames().size());
    }

    @Test
    void testPopUpAddEventView() {
        // Act
        interactor.popUpAddEventView();

        // Assert
        verify(presenterMock).popUpAddEventView();
    }

    @Test
    void testPopUpTimeEstimationChatbotView() {
        // Act
        interactor.popUpTimeEstimationChatbotView();

        // Assert
        verify(presenterMock).popUpTimeEstimationChatbotView();
    }

    @Test
    void testPopUpEventConflictChatbotView() {
        // Act
        interactor.popUpEventConflictChatbotView();

        // Assert
        verify(presenterMock).popUpEventConflictChatbotView();
    }

    @Test
    void testEditView() {
        // Arrange
        String eventName = "Meeting";

        // Act
        interactor.editView(eventName);

        // Assert
        verify(presenterMock).editView(eventName);
    }

    @Test
    void testSetCurrentEvent() {
        // Arrange
        String eventName = "Lunch";

        // Act
        interactor.setCurrentEvent(eventName);

        // Assert
        verify(dataAccessMock).setCurrentEventName(eventName);
    }

    @Test
    void testRefreshScheduleState() {
        // Arrange
        Map<String, Map<String, Object>> eventDetails = new HashMap<>();
        eventDetails.put("Meeting", createEventDetails(DayOfWeek.MONDAY, LocalTime.of(9, 0), DayOfWeek.MONDAY, LocalTime.of(10, 0)));
        eventDetails.put("Lunch", createEventDetails(DayOfWeek.TUESDAY, LocalTime.of(12, 0), DayOfWeek.TUESDAY, LocalTime.of(13, 0)));
        when(dataAccessMock.getAllEventInfo()).thenReturn(eventDetails);

        // Act
        interactor.refreshScheduleState();

        // Assert
        verify(presenterMock).updateScheduleWithEvents(eventDetails);
    }

    // Helper method to mock an Event
    private Event mockEvent(String name, DayOfWeek dayStart, LocalTime timeStart, DayOfWeek dayEnd, LocalTime timeEnd) {
        Event event = mock(Event.class);
        when(event.getEventName()).thenReturn(name);
        when(event.getDayStart()).thenReturn(dayStart);
        when(event.getTimeStart()).thenReturn(timeStart);
        when(event.getDayEnd()).thenReturn(dayEnd);
        when(event.getTimeEnd()).thenReturn(timeEnd);
        return event;
    }

    // Helper method to create event details for Map
    private Map<String, Object> createEventDetails(DayOfWeek dayStart, LocalTime timeStart, DayOfWeek dayEnd, LocalTime timeEnd) {
        Map<String, Object> details = new HashMap<>();
        details.put("dayStart", dayStart);
        details.put("timeStart", timeStart);
        details.put("dayEnd", dayEnd);
        details.put("timeEnd", timeEnd);
        return details;
    }
}
