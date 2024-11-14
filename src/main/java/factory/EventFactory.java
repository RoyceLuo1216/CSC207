package factory;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.EventEntity.FlexibleEvent;
import entities.EventEntity.RepeatEvent;

import java.time.LocalDateTime;
import java.util.List;

public class EventFactory {
    public static Event createFixedEvent(String eventName, int priorityLabel, LocalDateTime dayStart, LocalDateTime dayEnd) {
        return new FixedEvent(dayStart, dayEnd, eventName, priorityLabel);
    }

    public static Event createFlexibleEvent(String eventName, int priorityLabel, LocalDateTime dayStart, LocalDateTime dayEnd, float timeAllocation) {
        return new FlexibleEvent(dayStart, dayEnd, eventName, priorityLabel, timeAllocation);
    }

    public static Event createRepeatEvent(LocalDateTime dayStart, LocalDateTime dayEnd, String eventName,
                                          int priorityLabel, List<LocalDateTime> daysRepeated) {
        return new RepeatEvent(dayStart, dayEnd, eventName, priorityLabel, daysRepeated);
    }
}
