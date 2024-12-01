package factory;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.EventEntity.RepeatEvent;

/**
 * EventFactory to create events.
 */
public class EventFactory {
    /**
     * Creating new fixed events.
     * @param eventName name of event.
     * @param dayStart start day.
     * @param dayEnd end day.
     * @param timeStart start time.
     * @param timeEnd end time.
     * @return Event that gets created.
     */
    public static Event createFixedEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                         LocalTime timeEnd) {
        return new FixedEvent(dayStart, dayEnd, eventName, timeStart, timeEnd);
    }

    /**
     * Create a Repeat Event.
     * @param eventName event name.
     * @param dayStart day start.
     * @param dayEnd day end.
     * @param timeStart start time.
     * @param timeEnd end time.
     * @param daysRepeated days that the event repeats on.
     * @return the repeat event that we create.
     */
    public static Event createRepeatEvent(String eventName, DayOfWeek dayStart, DayOfWeek dayEnd, LocalTime timeStart,
                                                                    LocalTime timeEnd, List<DayOfWeek> daysRepeated) {
        return new RepeatEvent(dayStart, dayEnd, eventName, timeStart, timeEnd, daysRepeated);
    }
}
