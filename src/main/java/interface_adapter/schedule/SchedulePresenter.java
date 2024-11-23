package interface_adapter.schedule;

import entities.EventEntity.Event;

import java.util.List;

/**
 * The SchedulePresenter formats data from the ScheduleController for the ScheduleView.
 */
public class SchedulePresenter {
    public ScheduleState presentEvents(List<Event> events) {
        return new ScheduleState(events);
    }
}
