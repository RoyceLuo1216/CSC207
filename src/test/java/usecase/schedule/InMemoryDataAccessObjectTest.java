package usecase.schedule;

import data_access.InMemoryDataAccessObject;
import entities.EventEntity.FixedEvent;

import org.junit.jupiter.api.Test;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class InMemoryDataAccessObjectTest {

    @Test
    void testAddEvent() {
        // Arrange
        InMemoryDataAccessObject inMemoryDataAccessObject = new InMemoryDataAccessObject();
        FixedEvent event = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Christmas Brunch",                 // Event name
                LocalTime.of(10, 0),                // Start time
                LocalTime.of(12, 0)                 // End time
        );

        // Act
        inMemoryDataAccessObject.addEvent(event);

        // Assert
        assertEquals(1, inMemoryDataAccessObject.getEvents().size());
        assertEquals(event, inMemoryDataAccessObject.getEvents().get(0));
    }

    @Test
    void testRemoveEvent() {
        // Arrange
        InMemoryDataAccessObject inMemoryDataAccessObject = new InMemoryDataAccessObject();
        FixedEvent event = new FixedEvent(
                DayOfWeek.MONDAY,                   // Start day
                DayOfWeek.MONDAY,                   // End day
                "Christmas Brunch",                 // Event name
                LocalTime.of(10, 0),                // Start time
                LocalTime.of(12, 0)                 // End time
        );

        // Act
        inMemoryDataAccessObject.addEvent(event);
        boolean removed = inMemoryDataAccessObject.removeEvent("Christmas Brunch");

        // Assert
        assertTrue(removed);
        assertEquals(0, inMemoryDataAccessObject.getEvents().size());
    }

    @Test
    void testGetEventByDayAndTime() {
        // Arrange
        InMemoryDataAccessObject inMemoryDataAccessObject = new InMemoryDataAccessObject();
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

        inMemoryDataAccessObject.addEvent(event1);
        inMemoryDataAccessObject.addEvent(event2);

        // Act
        Optional<entities.EventEntity.Event> foundEvent = inMemoryDataAccessObject.getEventByDayAndTime(DayOfWeek.MONDAY, LocalTime.of(9, 30));
        Optional<entities.EventEntity.Event> noEvent = inMemoryDataAccessObject.getEventByDayAndTime(DayOfWeek.TUESDAY, LocalTime.of(9, 30));

        // Assert
        assertTrue(foundEvent.isPresent());
        assertEquals("Morning Meeting", foundEvent.get().getEventName());
        assertFalse(noEvent.isPresent());
    }
}
