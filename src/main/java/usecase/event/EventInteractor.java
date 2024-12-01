package usecase.event;

import java.util.Optional;

import entities.EventEntity.Event;
import entities.ScheduleEntity.Schedule;

public class EventInteractor implements EventInputBoundary {
    private final Schedule userSchedule;
    private final EventOutputBoundary presenter;

    public EventInteractor(Schedule userSchedule, EventOutputBoundary eventOutputBoundary) {
        this.userSchedule = userSchedule;
        this.presenter = eventOutputBoundary;
    }

    /**
     * Execute class for the interactor.
     * @param eventInputData      the inputdata for the event
     */

    @Override
    public void execute(EventInputData eventInputData) {
        final String eventName = eventInputData.getEventName();
        final Optional<Event> optionalEvent = userSchedule.getEventByName(eventName);

        if (optionalEvent.isPresent()) {
            // event already exists, cannot add
            presenter.prepareFailView("Event already exists");

        }
        else {
            final boolean addEvent = userSchedule.addEvent(new Event(eventInputData.getEventName(),
                    eventInputData.getDayStart(),
                    eventInputData.getDayEnd(),
                    eventInputData.getTimeStart(),
                    eventInputData.getTimeEnd()));

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
