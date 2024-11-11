package usecase.flexible;

import EventEntity.FlexibleEvent;
import org.junit.Before;
import org.junit.Test;
import usecase.Schedule;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

public class FlexibleEventUseCaseTest {

    private Schedule schedule;

    @Before
    public void setUp() {
        schedule = new Schedule();
    }

    @Test
    public void testAddFlexibleEvent() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 12, 23, 59);
        float timeAllocation = 10.0f;

        // Create and add the FlexibleEvent
        FlexibleEvent event = new FlexibleEvent(start, end, "Stats Studying", 2, timeAllocation);
        boolean isAdded = schedule.addEvent(event);

        // Verify that the event was successfully added
        assertTrue(isAdded);
        Optional<FlexibleEvent> retrievedEvent = schedule.getEventsByType(FlexibleEvent.class).stream()
                .filter(e -> e.getEventName().equals("Stats Studying"))
                .findFirst();

        assertTrue(retrievedEvent.isPresent());
        assertEquals("Stats Studying", retrievedEvent.get().getEventName());
        assertEquals(start, retrievedEvent.get().getDayStart());
        assertEquals(end, retrievedEvent.get().getDayEnd());
        assertEquals(timeAllocation, retrievedEvent.get().getTimeAllocation(), 0.0f);
    }

    @Test
    public void testRemoveFlexibleEvent() {
        LocalDateTime start = LocalDateTime.of(2024, 11, 9, 0, 0);
        LocalDateTime end = LocalDateTime.of(2024, 11, 12, 23, 59);
        float timeAllocation = 10.0f;

        // Create and add the FlexibleEvent
        FlexibleEvent event = new FlexibleEvent(start, end, "Stats Studying", 2, timeAllocation);
        schedule.addEvent(event);

        // Verify that the event was added
        Optional<FlexibleEvent> addedEvent = schedule.getEventsByType(FlexibleEvent.class).stream()
                .filter(e -> e.getEventName().equals("Stats Studying"))
                .findFirst();
        assertTrue(addedEvent.isPresent());

        // Remove the event and verify it no longer exists
        boolean isRemoved = schedule.removeEvent("Stats Studying");
        assertTrue(isRemoved);
        Optional<FlexibleEvent> removedEvent = schedule.getEventsByType(FlexibleEvent.class).stream()
                .filter(e -> e.getEventName().equals("Stats Studying"))
                .findFirst();
        assertFalse(removedEvent.isPresent());
    }

    @Test
    public void testRetrieveFlexibleEvents() {
        // Adding multiple FlexibleEvents
        FlexibleEvent event1 = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 10, 9, 0),
                LocalDateTime.of(2024, 11, 12, 23, 59),
                "Stats Studying", 2, 10.0f);

        FlexibleEvent event2 = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 10, 14, 0),
                LocalDateTime.of(2024, 11, 12, 16, 0),
                "Project Work", 3, 5.0f);

        schedule.addEvent(event1);
        schedule.addEvent(event2);

        // Retrieve all FlexibleEvents and verify the correct number is returned
        List<FlexibleEvent> flexibleEvents = schedule.getEventsByType(FlexibleEvent.class);
        assertEquals(2, flexibleEvents.size());

        // Verify specific events in the list
        assertTrue(flexibleEvents.stream().anyMatch(e -> e.getEventName().equals("Stats Studying")));
        assertTrue(flexibleEvents.stream().anyMatch(e -> e.getEventName().equals("Project Work")));
    }
}
