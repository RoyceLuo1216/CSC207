package usecase.edit;


import data_access.Schedule;
import entities.EventEntity.FixedEvent;
import entities.EventEntity.RepeatEvent;
import entities.EventEntity.Event;

import java.time.DayOfWeek;
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
            String eventType = editInputData.getEventType() + "Event";

            if (!eventType.equals(event.getClass().getSimpleName())) {
                // event type is being changed, tell user that the event type cannot be changed
                presenter.prepareFailView("The event type cannot be changed.");

            } else if (eventType.equals("RepeatEvent")) {
                 // event is a repeat event and thus has one extra parameter then fixed event
                updateFixedEventData(editInputData, event);
                RepeatEvent repeatEvent = (RepeatEvent) event;
                repeatEvent.setDaysRepeated(editInputData.getDaysRepeated());

                final EditOutputData editOutputData = new EditOutputData(eventName, false,
                                                    "Successfully updated repeat event!");
                presenter.prepareSuccessView(editOutputData);

            } else {
                // event is present and type has not been changed, so we can update
                updateFixedEventData(editInputData, event);

                final EditOutputData editOutputData = new EditOutputData(eventName, false,
                                                            "successfully update event!");
                presenter.prepareSuccessView(editOutputData);
            }
        }
    }

    /**
     * Updates fixed event data (dayStart, dayEnd, timeStart, timeEnd)
     * @param editInputData     the input data containing the updated information
     * @param event             the event to edit
     */
    private static void updateFixedEventData(EditInputData editInputData, Event event) {
        event.setDayStart(editInputData.getDayStart());
        event.setDayEnd(editInputData.getDayEnd());
        event.setTimeStart(editInputData.getTimeStart());
        event.setTimeEnd(editInputData.getTimeEnd());
    }
}
