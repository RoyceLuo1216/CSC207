package factory;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.EventEntity.FlexibleEvent;
import entities.EventEntity.RepeatEvent;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

public class EventFactory {
    public static Event createFixedEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                                                                                LocalTime timeEnd) {
        return new FixedEvent(dayStart, dayEnd, eventName, timeStart, timeEnd);
    }

    public static Event createFlexibleEvent(String eventName, int priorityLabel, LocalDateTime dayStart, LocalDateTime dayEnd, float timeAllocation) {
        return new FlexibleEvent(dayStart, dayEnd, eventName, priorityLabel, timeAllocation);
    }

    public static Event createRepeatEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                                                    LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        return new RepeatEvent(dayStart, dayEnd, eventName, timeStart, timeEnd, daysRepeated);
    }
}
