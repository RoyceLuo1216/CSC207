package usecase.chatbot_event_conflict;

import data_access.InMemoryAddDataAccessObject;
import entities.eventEntity.Event;
import entities.eventEntity.FixedEvent;
import factory.EventFactory;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

/**
 * Test for the Chatbot Event Conflict Use Case
 */
public class EventConflictUseCaseTest {
    private InMemoryAddDataAccessObject inMemoryDataAccessObject;
    private EventConflictInteractor interactor;

    @Before
    public void setUp() {

        inMemoryDataAccessObject = new InMemoryAddDataAccessObject();


        // Mock dependencies
        EventConflictPresenter mockPresenter = mock(EventConflictPresenter.class);
        EventFactory mockFactory = mock(EventFactory.class);

        // Initialize the interactor
        interactor = new EventConflictInteractor(inMemoryDataAccessObject, mockPresenter, mockFactory);

    }

    // TODO: make Event Conflict Use Case test into CA

    /**
     * Test for getTasksDuring method in EventConflictInteractor class (tasks exist)
     */
    @Test
    public void eventConflictOccurs() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 9, 20, 0);

// Define start and end times
        LocalTime startTime = LocalTime.of(18, 0); // 6:00 PM
        LocalTime endTime = LocalTime.of(20, 0);   // 8:00 PM

// Define days for the event
        DayOfWeek startDay = DayOfWeek.SATURDAY;
        DayOfWeek endDay = DayOfWeek.SATURDAY;

// Creating and adding the FixedEvent from 6 to 8 PM
        FixedEvent event = new FixedEvent(startDay, endDay, "Naptime", startTime, endTime);
        inMemoryDataAccessObject.addEvent(event);



        // Verify event has been added
        Optional<Event> retrievedEvent = inMemoryDataAccessObject.getEventByDayAndTime(startDay, startTime);

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Naptime", retrievedEvent.get().getEventName());

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
        LocalTime startTime = LocalTime.of(18, 0); // 6:00 PM
        LocalTime endTime = LocalTime.of(20, 0);   // 8:00 PM
        LocalTime startIntervalTime = LocalTime.of(16, 0); // 4:00 PM
        LocalTime endIntervalTime = LocalTime.of(18, 0);   // 6:00 PM
        DayOfWeek startDay = DayOfWeek.SATURDAY;
        DayOfWeek endDay = DayOfWeek.SATURDAY;



        // Creating and adding the FixedEvent from 6 to 8pm
        FixedEvent event = new FixedEvent(startDay, endDay, "Naptime", startTime, endTime);
        inMemoryDataAccessObject.addEvent(event);


        // Verify event has been added
        Optional<Event> retrievedEvent = inMemoryDataAccessObject.getEventByDayAndTime(startDay, startTime);

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Naptime", retrievedEvent.get().getEventName());

        // Verify there is no event conflict at specified time
        ArrayList<String> actual =  interactor.getTasksDuring(startDay, startIntervalTime, endIntervalTime, inMemoryDataAccessObject);
        ArrayList<String> result = new ArrayList<String>();


        assertEquals(result, actual);
    }
}
