package usecase.edit;

import java.util.Optional;

import entities.eventEntity.Event;
import entities.eventEntity.RepeatEvent;

/**
 *  Interactor for Edit Use Case. Implements abstraction defined in EditEventInputBoundary.
 */
public class EditEventInteractor implements EditEventInputBoundary {
    private final EditEventDataAccessInterface dataAccessObject;
    private final EditEventOutputBoundary presenter;

    public EditEventInteractor(EditEventDataAccessInterface dataAccessObject, EditEventOutputBoundary editEventOutputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = editEventOutputBoundary;
    }

    /**
     * Updates fixed event data (dayStart, dayEnd, timeStart, timeEnd).
     *
     * @param editEventInputData the input data containing the updated information
     * @param event         the event to edit
     */
    private static void updateFixedEventData(EditEventInputData editEventInputData, Event event) {
        event.setDayStart(editEventInputData.getDayStart());
        event.setDayEnd(editEventInputData.getDayEnd());
        event.setTimeStart(editEventInputData.getTimeStart());
        event.setTimeEnd(editEventInputData.getTimeEnd());
    }

    @Override
    public void execute(EditEventInputData editEventInputData) {
        final String eventName = editEventInputData.getEventName();
        final Optional<Event> optionalEvent = dataAccessObject.getEventByName(editEventInputData.getEventName());

        if (!optionalEvent.isPresent()) {
            // event is not present, tell user that the event does not exist
            presenter.prepareFailView("The event, " + eventName + " does not exist.");

        }
        else {
            if (editEventInputData.getDayStart().compareTo(editEventInputData.getDayEnd()) > 0) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else if (editEventInputData.getDayEnd().compareTo(editEventInputData.getDayStart()) == 0
                    && editEventInputData.getTimeStart().isAfter(editEventInputData.getTimeEnd())) {
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }
            else {
                final Event event = optionalEvent.get();
                final String eventType = editEventInputData.getEventType() + "Event";

                if (!eventType.equals(event.getClass().getSimpleName())) {
                    // event type is being changed, tell user that the event type cannot be changed
                    presenter.prepareFailView("The event type cannot be changed.");

                }
                else if ("RepeatEvent".equals(eventType)) {
                    // event is a repeat event and thus has one extra parameter then fixed event
                    updateFixedEventData(editEventInputData, event);
                    final RepeatEvent repeatEvent = (RepeatEvent) event;
                    repeatEvent.setDaysRepeated(editEventInputData.getDaysRepeated());

                    final EditEventOutputData editEventOutputData = new EditEventOutputData(eventName, false,
                            "Successfully updated repeat event!");
                    presenter.prepareSuccessView(editEventOutputData);

                }
                else {
                    // event is present and type has not been changed, so we can update
                    updateFixedEventData(editEventInputData, event);

                    final EditEventOutputData editEventOutputData = new EditEventOutputData(eventName, false,
                            "successfully update event!");
                    presenter.prepareSuccessView(editEventOutputData);
                }
            }
        }
    }
}
