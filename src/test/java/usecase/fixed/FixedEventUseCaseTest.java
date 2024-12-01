package usecase.fixed;

import data_access.InMemoryAddDataAccessObject;
import entities.eventEntity.Event;
import entities.eventEntity.FixedEvent;
import org.junit.Before;
import org.junit.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.*;

public class FixedEventUseCaseTest {

    private InMemoryAddDataAccessObject inMemoryDataAccessObject;

    @Before
    public void setUp() {
        inMemoryDataAccessObject = new InMemoryAddDataAccessObject();
    }

    @Test
    public void testAddFixedEvent() {
        DayOfWeek startDay = DayOfWeek.SATURDAY;
        DayOfWeek endDay = DayOfWeek.SATURDAY;
        LocalTime startTime = LocalTime.of(18, 0);
        LocalTime endTime = LocalTime.of(20, 0);

        // Creating and adding the FixedEvent
        FixedEvent event = new FixedEvent(startDay, endDay, "MAT237 Midterm", startTime, endTime);
        inMemoryDataAccessObject.addEvent(event);

        // Verify the event has been added
        Optional<Event> retrievedEvent = inMemoryDataAccessObject.getEventByName("MAT237 Midterm");
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
        inMemoryDataAccessObject.addEvent(event);

        // Verify the event is in the schedule
        assertTrue(inMemoryDataAccessObject.getEventByName("MAT237 Midterm").isPresent());

        // Remove the event
        inMemoryDataAccessObject.removeEvent("MAT237 Midterm");

        // Verify the event has been removed
        assertFalse(inMemoryDataAccessObject.getEventByName("MAT237 Midterm").isPresent());
    }
}
