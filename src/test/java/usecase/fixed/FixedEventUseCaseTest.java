package usecase.fixed;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.ScheduleEntity.Schedule;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class FixedEventUseCaseTest {

    private Schedule schedule;

    @Before
    public void setUp() {
        schedule = new Schedule();
    }

    @Test
    public void testAddFixedEvent() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 9, 20, 0);

        // Creating and adding the FixedEvent
        FixedEvent event = new FixedEvent(start, end, "MAT237 Midterm", 1);
        schedule.addEvent(event);

        // Verify the event has been added
        Optional<Event> retrievedEvent = schedule.getEventByName("MAT237 Midterm");
        assertTrue(retrievedEvent.isPresent());
        assertEquals("MAT237 Midterm", retrievedEvent.get().getEventName());
        assertEquals(start, retrievedEvent.get().getDayStart());
        assertEquals(end, retrievedEvent.get().getDayEnd());
    }

    @Test
    public void testRemoveFixedEvent() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 18, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 9, 20, 0);

        // Adding the FixedEvent
        FixedEvent event = new FixedEvent(start, end, "MAT237 Midterm", 1);
        schedule.addEvent(event);

        // Verify the event is in the schedule
        assertTrue(schedule.getEventByName("MAT237 Midterm").isPresent());

        // Remove the event
        schedule.removeEvent("MAT237 Midterm");

        // Verify the event has been removed
        assertFalse(schedule.getEventByName("MAT237 Midterm").isPresent());
    }
}
