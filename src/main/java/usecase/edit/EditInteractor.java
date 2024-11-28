package usecase.edit;

import entities.ScheduleEntity.Schedule;
import entities.EventEntity.Event;

import java.util.Optional;

public class EditInteractor implements EditInputBoundary {
    private final Schedule userSchedule;
    private final EditOutputBoundary presenter;

    public EditInteractor(Schedule userSchedule, EditOutputBoundary editOutputBoundary) {
        this.userSchedule = userSchedule;
        this.presenter = editOutputBoundary;
    }

    @Override
    public void execute(EditInputData editInputData) {
        final String eventName = editInputData.getEventName();
        Optional<Event> optionalEvent = userSchedule.getEventByName(editInputData.getEventName());

        if (!optionalEvent.isPresent()) {
            // event is not present, tell user that the event does not exist
            presenter.prepareFailView("The event, " + eventName + " does not exist.");

        } else {
            Event event = optionalEvent.get();

            if (!editInputData.getEventType().equals(event.getClass().getName())) {
                // event type is being changed, tell user that the event type cannot be changed
                presenter.prepareFailView("The event type cannot be changed.");

            } else {
                // event is present and type has not been changed, so we can update
                event.setDayStart(editInputData.getDayStart());
                event.setDayEnd(editInputData.getDayEnd());
                event.setTimeStart(editInputData.getTimeStart());
                event.setTimeEnd(editInputData.getTimeEnd());

                final EditOutputData editOutputData = new EditOutputData(eventName, false);

                presenter.prepareSuccessView(editOutputData);
            }
        }
    }
}
