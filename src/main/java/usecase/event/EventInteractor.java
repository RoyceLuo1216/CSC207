package usecase.event;

import entities.ScheduleEntity.Schedule;
import entities.EventEntity.Event;

import java.util.Optional;

public class EventInteractor implements EventInputBoundary {
    private final Schedule userSchedule;
    private final EventOutputBoundary presenter;

    public EventInteractor(Schedule userSchedule, EventOutputBoundary eventOutputBoundary) {
        this.userSchedule = userSchedule;
        this.presenter = eventOutputBoundary;
    }

    @Override
    public void execute(EventInputData eventInputData) {
        String eventName = eventInputData.getEventName();
        Optional<Event> optionalEvent = userSchedule.getEventByName(eventName);

        if (optionalEvent.isPresent()) {
            // event already exists, cannot add
            presenter.prepareFailView("Event already exists");

        } else {
            boolean addEvent = userSchedule.addEvent(new Event(eventInputData.getEventName(), eventInputData.getDayStart(),
                    eventInputData.getDayEnd(), eventInputData.getTimeStart(), eventInputData.getTimeEnd()));

            if (!addEvent) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added");
            }

            else {

                final EventOutputData eventOutputData = new EventOutputData(eventName, false);

                presenter.prepareSuccessView(eventOutputData);
            }
        }
    }
}
