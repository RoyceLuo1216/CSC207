package usecase.schedule;

import data_access.Schedule;
import entities.EventEntity.FixedEvent;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ScheduleTest {

    @Test
    void testAddEvent() {
        Schedule schedule = new Schedule();
        FixedEvent event = new FixedEvent(
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 12, 0),
                "Christmas Brunch",
                1
        );

        schedule.addEvent(event);

        assertEquals(1, schedule.getEvents().size());
        assertEquals(event, schedule.getEvents().get(0));
    }

    @Test
    void testRemoveEvent() {
        Schedule schedule = new Schedule();
        FixedEvent event = new FixedEvent(
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 12, 0),
                "Christmas Brunch",
                1
        );

        schedule.addEvent(event);
        assertTrue(schedule.removeEvent("Christmas Brunch"));
        assertEquals(0, schedule.getEvents().size());
    }
}
