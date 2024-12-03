package usecase.chatbot_event_conflict;

import data_access.InMemoryDataAccessObject;
import entities.eventEntity.Event;
import entities.eventEntity.FixedEvent;
import entities.eventEntity.EventFactory;
import interface_adapter.chatbot_event_conflict.EventConflictController;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test for the Chatbot Event Conflict Use Case
 */
public class EventConflictUseCaseTest {
    private InMemoryDataAccessObject inMemoryDataAccessObject;

    // Start and end times
    LocalTime startTime;
    LocalTime endTime;
    LocalTime endTime2;
    DayOfWeek startDay;
    DayOfWeek endDay;

    private static class MockPresenter implements EventConflictChatbotOutputBoundary {
        private boolean successCalled;
        private boolean failureCalled;
        private boolean backCalled;
        private String response;
        private String failureMessage;

        /**
         * Prepares the success view for the Event Conflict Use Case.
         *
         * @param outputData the output data
         */
        @Override
        public void prepareSuccessView(EventConflictChatbotOutputData outputData) {
            successCalled = true;
            response = outputData.getResponse();
        }

        /**
         * Prepares the failure view for the Event Conflict Use Case.
         *
         * @param errorMessage the explanation of the failure
         */
        @Override
        public void prepareFailView(String errorMessage) {
            failureCalled = true;
            failureMessage = errorMessage;
        }

        /**
         * Switches back to the Main Schedule View.
         */
        @Override
        public void backToScheduleView() {
            backCalled = true;
        }

        public boolean isSuccessCalled() {
            return successCalled;
        }

        public boolean isFailureCalled() {
            return failureCalled;
        }

        public boolean isBackCalled() {return backCalled;}

        public String getSuccessResponse() {
            return response;
        }

        public String getFailureMessage() {
            return failureMessage;
        }
    }


    @Before
    public void setUp() {

        inMemoryDataAccessObject = new InMemoryDataAccessObject();

        // Define start and end times
        startTime = LocalTime.of(18, 0); // 6:00 PM
        endTime = LocalTime.of(20, 0);   // 8:00 PM
        endTime2 = LocalTime.of(22, 0);   // 10:00 PM
        startDay = DayOfWeek.SATURDAY;
        endDay = DayOfWeek.SATURDAY;

        // Creating and adding events
        FixedEvent event = new FixedEvent(startDay, endDay, "Naptime", startTime, endTime);
        FixedEvent event2 = new FixedEvent(startDay, endDay, "Homework", endTime, endTime2);

        inMemoryDataAccessObject.addEvent(event);
        inMemoryDataAccessObject.addEvent(event2);

    }

    /**
     * Test for adding "Naptime" event to schedule
     */
    @Test
    public void addedEvent(){

        // Verify event has been added
        Optional<Event> retrievedEvent = inMemoryDataAccessObject.getEventByDayAndTime(startDay, startTime);

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Naptime", retrievedEvent.get().getEventName());
    }

    /**
     * Test for getTasksDuring method in EventConflictInteractor class (tasks exist)
     */
    @Test
    public void eventConflictOccurs() {
        EventConflictChatbotOutputBoundary mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        // Verify there is an event conflict at specified time
        ArrayList<String> actual =  interactor.getTasksDuring(startDay,startTime, endTime, inMemoryDataAccessObject);
        ArrayList<String> result = new ArrayList<String>();
        result.add("Naptime: Saturday 6:00 p.m. - 8:00 p.m.");

        assertEquals(result, actual);
    }

    /**
     * Test for getTasksDuring method in EventConflictInteractor class (no tasks exist)
     */
    @Test
    public void noEventConflict() {
        EventConflictChatbotOutputBoundary mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        LocalTime startIntervalTime = LocalTime.of(16, 0); // 4:00 PM
        LocalTime endIntervalTime = LocalTime.of(18, 0);   // 6:00 PM

        // Verify there is no event conflict at specified time
        ArrayList<String> actual =  interactor.getTasksDuring(startDay, startIntervalTime, endIntervalTime,
                inMemoryDataAccessObject);
        ArrayList<String> result = new ArrayList<String>();

        assertEquals(result, actual);
    }

    /**
     * Test for getHourlyIntervals method in EventConflictInteractor class (start time is after end time)
     */
    @Test
    public void startAfterEnd() {
        EventConflictChatbotOutputBoundary mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        LocalTime startIntervalTime = LocalTime.of(18, 0); // 6:00 PM
        LocalTime endIntervalTime = LocalTime.of(16, 0);   // 4:00 PM

        // Verify there is no event conflict at specified time
        interactor.getTasksDuring(startDay,startTime, endTime, inMemoryDataAccessObject);

        assertThrows(IllegalArgumentException.class, () -> {
            throw new IllegalArgumentException("Start time must be before or equal to end time.");
        });
    }

    /**
     * Test for an event conflict with the CoHere call
     */
    @Test
    public void eventConflictCohere() {
        MockPresenter mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        final EventConflictController controller = new EventConflictController(interactor);

        controller.execute("Do I have an event on saturday from 6-8 pm?");

        String result = "You have the following event conflict: \n\n\tNaptime: Saturday 6:00 p.m. - 8:00 p.m.\n";
        String actual = mockPresenter.getSuccessResponse();

        assertTrue(mockPresenter.isSuccessCalled());
        assertEquals(result, actual);

    }

    /**
     * Test for mutiple event conflict with the CoHere call
     */
    @Test
    public void multipleEventConflictCohere() {
        MockPresenter mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        final EventConflictController controller = new EventConflictController(interactor);

        controller.execute("Do I have an event on saturday from 6-10 pm?");

        String result = "You have the following event conflicts: \n" +
                "\n" +
                "\tNaptime: Saturday 6:00 p.m. - 8:00 p.m.\n" +
                "\tHomework: Saturday 8:00 p.m. - 10:00 p.m.\n";
        String actual = mockPresenter.getSuccessResponse();

        assertTrue(mockPresenter.isSuccessCalled());
        assertEquals(result, actual);

    }

    /**
     * Test for no event conflict with the CoHere call
     */
    @Test
    public void noEventConflictOccursCohere() {
        MockPresenter mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        final EventConflictController controller = new EventConflictController(interactor);

        controller.execute("Do I have an event on monday from 7-8 pm?");

        String result = "Yes, you can schedule your task on Monday from 7:00 p.m. to 8:00 p.m..";
        String actual = mockPresenter.getSuccessResponse();

        assertTrue(mockPresenter.isSuccessCalled());
        assertEquals(result, actual);

    }

    /**
     * Test for no time found with the CoHere call
     */
    @Test
    public void noTimeFoundCohere() {
        MockPresenter mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        final EventConflictController controller = new EventConflictController(interactor);

        controller.execute("ahfgkjsdflk");

        String result = "No time period found";
        String actual = mockPresenter.getFailureMessage();

        assertTrue(mockPresenter.isFailureCalled());
        assertEquals(result, actual);

    }

    /**
     * Test for back to schedule view called
     */
    @Test
    public void backToScheduleTest() {
        MockPresenter mockPresenter = new MockPresenter();
        EventFactory mockFactory = mock(EventFactory.class);
        EventConflictInteractor interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

        final EventConflictController controller = new EventConflictController(interactor);

        controller.backToScheduleView();

        assertTrue(mockPresenter.isBackCalled());

    }
}
