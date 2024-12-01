package factory;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.EventEntity.RepeatEvent;

import java.time.LocalDateTime;
import java.util.List;

public class EventFactory {
    public static Event createFixedEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                                                                                LocalTime timeEnd) {
        return new FixedEvent(dayStart, dayEnd, eventName, timeStart, timeEnd);
    }


    public static Event createRepeatEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                                                    LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        return new RepeatEvent(dayStart, dayEnd, eventName, timeStart, timeEnd, daysRepeated);
    }
}
