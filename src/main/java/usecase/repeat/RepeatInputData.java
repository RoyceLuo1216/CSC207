package usecase.repeat;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.DayOfWeek;
import java.util.List;

/**
 * The input data for the Repeat use case.
 */
public class RepeatInputData {
    private final String eventName;
    private final DayOfWeek dayStart;
    private final DayOfWeek dayEnd;
    private final LocalTime timeStart;
    private final LocalTime timeEnd;
    private final List<DayOfWeek> daysRepeated;

    public RepeatInputData(String eventName,
                           DayOfWeek dayStart,
                           DayOfWeek dayEnd,
                           LocalTime timeStart,
                           LocalTime timeEnd,
                           List<DayOfWeek> daysRepeatedd) {
        this.eventName = eventName;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.daysRepeated = daysRepeatedd;
    }

    String getEventName() {
        return eventName;
    }


    DayOfWeek getDayStart() {
        return dayStart;
    }

    DayOfWeek getDayEnd() {
        return dayEnd;
    }

    LocalTime getTimeStart() {
        return timeStart;
    }

    LocalTime getTimeEnd() {
        return timeEnd;
    }

    List<DayOfWeek> getDaysRepeated() {return daysRepeated;}
}
