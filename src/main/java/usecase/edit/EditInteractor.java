package usecase.edit;

import entities.EventEntity.Event;
import entities.EventEntity.RepeatEvent;

import java.util.Optional;

/**
 *  Interactor for Edit Use Case. Implements abstraction defined in EditInputBoundary.
 */
public class EditInteractor implements EditInputBoundary {
    private final EditDataAccessInterface dataAccessObject;
    private final EditOutputBoundary presenter;

    public EditInteractor(EditDataAccessInterface dataAccessObject, EditOutputBoundary editOutputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = editOutputBoundary;
    }

    /**
     * Updates fixed event data (dayStart, dayEnd, timeStart, timeEnd).
     *
     * @param editInputData the input data containing the updated information
     * @param event         the event to edit
     */
    private static void updateFixedEventData(EditInputData editInputData, Event event) {
        event.setDayStart(editInputData.getDayStart());
        event.setDayEnd(editInputData.getDayEnd());
        event.setTimeStart(editInputData.getTimeStart());
        event.setTimeEnd(editInputData.getTimeEnd());
    }

    @Override
    public void execute(EditInputData editInputData) {
        final String eventName = editInputData.getEventName();
        final Optional<Event> optionalEvent = dataAccessObject.getEventByName(editInputData.getEventName());

        if (!optionalEvent.isPresent()) {
            // event is not present, tell user that the event does not exist
            presenter.prepareFailView("The event, " + eventName + " does not exist.");

        }
        else {
            if (editInputData.getDayStart().compareTo(editInputData.getDayEnd()) > 0) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else if (editInputData.getDayEnd().compareTo(editInputData.getDayStart()) == 0
                    && editInputData.getTimeStart().isAfter(editInputData.getTimeEnd())) {
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }
            else {
                final Event event = optionalEvent.get();
                final String eventType = editInputData.getEventType() + "Event";

                if (!eventType.equals(event.getClass().getSimpleName())) {
                    // event type is being changed, tell user that the event type cannot be changed
                    presenter.prepareFailView("The event type cannot be changed.");

                }
                else if ("RepeatEvent".equals(eventType)) {
                    // event is a repeat event and thus has one extra parameter then fixed event
                    updateFixedEventData(editInputData, event);
                    final RepeatEvent repeatEvent = (RepeatEvent) event;
                    repeatEvent.setDaysRepeated(editInputData.getDaysRepeated());

                    final EditOutputData editOutputData = new EditOutputData(eventName, false,
                            "Successfully updated repeat event!");
                    presenter.prepareSuccessView(editOutputData);

                }
                else {
                    // event is present and type has not been changed, so we can update
                    updateFixedEventData(editInputData, event);

                    final EditOutputData editOutputData = new EditOutputData(eventName, false,
                            "successfully update event!");
                    presenter.prepareSuccessView(editOutputData);
                }
            }
        }
    }
}
