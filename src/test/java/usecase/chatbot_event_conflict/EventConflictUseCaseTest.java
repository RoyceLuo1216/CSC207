package usecase.chatbot_event_conflict;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.ScheduleEntity.Schedule;
import factory.EventFactory;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Test for the Chatbot Event Conflict Use Case
 */
public class EventConflictUseCaseTest {
    private Schedule schedule;
    private EventConflictInteractor interactor;

    @Before
    public void setUp() {
        schedule = new Schedule();
        // interactor = new EventConflictInteractor(schedule, eventConflictPresenter, eventFactory);
    }

    // TODO: make Event Conflict Use Case test into CA

    /**
     * Test for getTasksDuring method in EventConflictInteractor class (tasks exist)
     */
    @Test
    public void eventConflictOccurs() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 9, 20, 0);

        // Creating and adding the FixedEvent from 6 to 8pm
        FixedEvent event = new FixedEvent(start, end, "Naptime", 1);
        schedule.addEvent(event);

        // Verify event has been added
        Optional<Event> retrievedEvent = schedule.getEventByTime(start);

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Naptime", retrievedEvent.get().getEventName());

        // Verify there is an event conflict at specified time
        ArrayList<String> actual =  interactor.getTasksDuring(start, end, schedule);
        ArrayList<String> result = new ArrayList<String>();
        result.add("Naptime: Nov 9  6:00 p.m. - 8:00 p.m.");

        assertEquals(result, actual);
    }

    /**
     * Test for getTasksDuring method in EventConflictInteractor class (no tasks exist)
     */
    @Test
    public void noEventConflict() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 9, 20, 0);
        LocalDateTime starti = LocalDateTime.of(2024, 11, 9, 16, 0);
        LocalDateTime endi = LocalDateTime.of(2024, 11, 9, 18, 0);

        // Creating and adding the FixedEvent from 6 to 8pm
        FixedEvent event = new FixedEvent(start, end, "Naptime", 1);
        schedule.addEvent(event);

        // Verify event has been added
        Optional<Event> retrievedEvent = schedule.getEventByTime(start);

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Naptime", retrievedEvent.get().getEventName());

        // Verify there is no event conflict at specified time
        ArrayList<String> actual =  interactor.getTasksDuring(starti, endi, schedule);
        ArrayList<String> result = new ArrayList<String>();

        assertEquals(result, actual);
    }
}
