package interface_adapter.schedule;

import entities.EventEntity.Event;

import java.util.List;

/**
 * The ScheduleViewModel communicates with the ScheduleController and SchedulePresenter.
 */
public class ScheduleViewModel {
    private final ScheduleController controller;
    private final SchedulePresenter presenter;

    public ScheduleViewModel(ScheduleController controller, SchedulePresenter presenter) {
        this.controller = controller;
        this.presenter = presenter;
    }

    public ScheduleState getScheduleState() {
        List<Event> events = controller.getAllEvents();
        return presenter.presentEvents(events);
    }
}
