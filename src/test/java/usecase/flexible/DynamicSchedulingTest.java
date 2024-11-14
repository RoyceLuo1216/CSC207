package usecase.flexible;

import entities.EventEntity.Event;
import entities.EventEntity.FlexibleEvent;
import entities.EventEntity.FixedEvent;
import org.junit.Before;
import org.junit.Test;
import entities.ScheduleEntity.Schedule;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.Assert.*;

public class DynamicSchedulingTest {

    private Schedule schedule;

    @Before
    public void setUp() {
        schedule = new Schedule();
    }

    @Test
    public void testBasicFlexibleEventScheduling() {
        // Add a fixed event to block out time
        Event fixedEvent = new FixedEvent(
                LocalDateTime.of(2024, 11, 9, 18, 0),
                LocalDateTime.of(2024, 11, 9, 20, 0),
                "MAT237 Midterm", 1);
        schedule.addEvent(fixedEvent);

        // Add a flexible event that should be scheduled around it
        FlexibleEvent flexibleEvent = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 9, 0, 0),
                LocalDateTime.of(2024, 11, 10, 23, 59),
                "Stats Studying", 2, 4.0f); // Needs 4 hours
        schedule.addEvent(flexibleEvent);

        // Run scheduling algorithm (method to be implemented in Schedule class)
        schedule.scheduleEvents();

        // Verify that the flexible event does not overlap with the fixed event
        List<FlexibleEvent> flexibleEvents = schedule.getEventsByType(FlexibleEvent.class);
        assertFalse(isOverlapping(flexibleEvents.get(0), fixedEvent));
    }

    @Test
    public void testFlexibleEventSchedulingWithMultipleFixedEvents() {
        // Add multiple fixed events
        FixedEvent fixedEvent1 = new FixedEvent(
                LocalDateTime.of(2024, 11, 9, 8, 0),
                LocalDateTime.of(2024, 11, 9, 10, 0),
                "Morning Meeting", 1);
        FixedEvent fixedEvent2 = new FixedEvent(
                LocalDateTime.of(2024, 11, 9, 15, 0),
                LocalDateTime.of(2024, 11, 9, 17, 0),
                "Afternoon Workshop", 1);

        schedule.addEvent(fixedEvent1);
        schedule.addEvent(fixedEvent2);

        // Add a flexible event that should avoid these fixed times
        FlexibleEvent flexibleEvent = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 9, 0, 0),
                LocalDateTime.of(2024, 11, 9, 23, 59),
                "Flexible Study Time", 2, 5.0f);
        schedule.addEvent(flexibleEvent);

        schedule.scheduleEvents();

        // Verify that flexible event times do not overlap with any fixed events
        List<FlexibleEvent> flexibleEvents = schedule.getEventsByType(FlexibleEvent.class);
        assertTrue(flexibleEvents.stream().noneMatch(flexEvent ->
                isOverlapping(flexEvent, fixedEvent1) || isOverlapping(flexEvent, fixedEvent2)));
    }

    @Test
    public void testPriorityBasedScheduling() {
        // Add a high-priority flexible event
        FlexibleEvent highPriorityEvent = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 10, 0, 0),
                LocalDateTime.of(2024, 11, 10, 23, 59),
                "High Priority Study", 1, 6.0f); // Needs 6 hours

        // Add a low-priority flexible event
        FlexibleEvent lowPriorityEvent = new FlexibleEvent(
                LocalDateTime.of(2024, 11, 10, 0, 0),
                LocalDateTime.of(2024, 11, 10, 23, 59),
                "Low Priority Reading", 3, 6.0f); // Needs 6 hours

        schedule.addEvent(highPriorityEvent);
        schedule.addEvent(lowPriorityEvent);

        schedule.scheduleEvents();

        // Verify that high-priority event is scheduled first
        List<FlexibleEvent> flexibleEvents = schedule.getEventsByType(FlexibleEvent.class);
        assertEquals("High Priority Study", flexibleEvents.get(0).getEventName());
        assertEquals("Low Priority Reading", flexibleEvents.get(1).getEventName());
    }

    // Helper method to check if two events overlap
    private boolean isOverlapping(Event event1, Event event2) {
        return !(event1.getDayEnd().isBefore(event2.getDayStart()) ||
                event2.getDayEnd().isBefore(event1.getDayStart()));
    }
}
