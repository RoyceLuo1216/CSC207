package usecase.schedule;

import data_access.Schedule;
import entities.EventEntity.FixedEvent;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    void testAddEvent() {
        // Arrange
        Schedule schedule = new Schedule();
        FixedEvent event = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Christmas Brunch",                 // Event name
                LocalTime.of(10, 0),                // Start time
                LocalTime.of(12, 0)                 // End time
        );

        // Act
        schedule.addEvent(event);

        // Assert
        assertEquals(1, schedule.getEvents().size());
        assertEquals(event, schedule.getEvents().get(0));
    }

    @Test
    void testRemoveEvent() {
        // Arrange
        Schedule schedule = new Schedule();
        FixedEvent event = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Christmas Brunch",                 // Event name
                LocalTime.of(10, 0),                // Start time
                LocalTime.of(12, 0)                 // End time
        );

        // Act
        schedule.addEvent(event);
        boolean removed = schedule.removeEvent("Christmas Brunch");

        // Assert
        assertTrue(removed);
        assertEquals(0, schedule.getEvents().size());
    }

    @Test
    void testGetEventByDayAndTime() {
        // Arrange
        Schedule schedule = new Schedule();
        FixedEvent event1 = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Morning Meeting",                  // Event name
                LocalTime.of(9, 0),                 // Start time
                LocalTime.of(10, 0)                 // End time
        );

        FixedEvent event2 = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Lunch Break",                      // Event name
                LocalTime.of(12, 0),                // Start time
                LocalTime.of(13, 0)                 // End time
        );

        schedule.addEvent(event1);
        schedule.addEvent(event2);

        // Act
        Optional<entities.EventEntity.Event> foundEvent = schedule.getEventByDayAndTime(DayOfWeek.MONDAY, LocalTime.of(9, 30));
        Optional<entities.EventEntity.Event> noEvent = schedule.getEventByDayAndTime(DayOfWeek.TUESDAY, LocalTime.of(9, 30));

        // Assert
        assertTrue(foundEvent.isPresent());
        assertEquals("Morning Meeting", foundEvent.get().getEventName());
        assertFalse(noEvent.isPresent());
    }
}
