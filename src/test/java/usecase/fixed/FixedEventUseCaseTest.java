package usecase.fixed;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import data_access.Schedule;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
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
        DayOfWeek startDay = DayOfWeek.SATURDAY;
        DayOfWeek endDay = DayOfWeek.SATURDAY;
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        // Creating and adding the FixedEvent
        FixedEvent event = new FixedEvent(startDay, endDay, "MAT237 Midterm", startTime, endTime);
        schedule.addEvent(event);

        // Verify the event has been added
        Optional<Event> retrievedEvent = schedule.getEventByName("MAT237 Midterm");
        assertTrue(retrievedEvent.isPresent());
        assertEquals("MAT237 Midterm", retrievedEvent.get().getEventName());
        assertEquals(startDay, retrievedEvent.get().getDayStart());
        assertEquals(endDay, retrievedEvent.get().getDayEnd());
        assertEquals(startTime, retrievedEvent.get().getTimeStart());
        assertEquals(endTime, retrievedEvent.get().getTimeEnd());
    }

    @Test
    public void testRemoveFixedEvent() {
        DayOfWeek startDay = DayOfWeek.SATURDAY;
        DayOfWeek endDay = DayOfWeek.SATURDAY;
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        // Adding the FixedEvent
        FixedEvent event = new FixedEvent(startDay, endDay, "MAT237 Midterm", startTime, endTime);
        schedule.addEvent(event);

        // Verify the event is in the schedule
        assertTrue(schedule.getEventByName("MAT237 Midterm").isPresent());

        // Remove the event
        schedule.removeEvent("MAT237 Midterm");

        // Verify the event has been removed
        assertFalse(schedule.getEventByName("MAT237 Midterm").isPresent());
    }
}
