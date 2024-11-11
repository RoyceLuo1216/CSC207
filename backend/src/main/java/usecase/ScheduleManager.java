package usecase;

import usecase.fixed.FixedEventUseCase;
import usecase.flexible.FlexibleEventUseCase;
import usecase.repeat.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Manages interactions between the schedule and various event use cases.
 */
public class ScheduleManager {
    private final Schedule schedule;
    private final FixedEventUseCase fixedEventUseCase;
    private final FlexibleEventUseCase flexibleEventUseCase;
    private final RepeatEventUseCase repeatEventUseCase;

    public ScheduleManager() {
        this.schedule = new Schedule();
        this.fixedEventUseCase = new FixedEventUseCase(schedule);
        this.flexibleEventUseCase = new FlexibleEventUseCase(schedule);
        this.repeatEventUseCase = new RepeatEventUseCase(schedule);
    }

    public boolean addFixedEvent(String name, int priority, LocalDateTime dayStart, LocalDateTime dayEnd) {
        return fixedEventUseCase.addFixedEvent(name, priority, dayStart, dayEnd);
    }

    public boolean addFlexibleEvent(String name, int priority, LocalDateTime dayStart, LocalDateTime dayEnd, float timeAllocation) {
        return flexibleEventUseCase.addFlexibleEvent(name, priority, dayStart, dayEnd, timeAllocation);
    }

    public boolean addRepeatEvent(String name, int priority, LocalDateTime dayStart, LocalDateTime dayEnd) {
        return repeatEventUseCase.addRepeatEvent(name, priority, dayStart, dayEnd);
    }

    public boolean removeEvent(String name) {
        return schedule.removeEvent(name);
    }

    public List<Event> getAllEvents() {
        return schedule.getAllEvents();
    }

    public void optimizeFlexibleEvents() {
        flexibleEventUseCase.optimizeFlexibleEvents();
    }
}
