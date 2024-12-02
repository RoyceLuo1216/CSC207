package usecase.event;

import java.util.Optional;

import entities.eventEntity.Event;
import entities.eventEntity.EventFactory;

/**
 *  Interactor for Event Add Use Case. Implements abstraction defined in EventInputBoundary.
 */
public class AddEventInteractor implements AddEventInputBoundary {
    private final AddEventDataAccessInterface dataAccessObject;
    private final AddEventOutputBoundary presenter;

    public AddEventInteractor(AddEventDataAccessInterface userSchedule, AddEventOutputBoundary eventOutputBoundary) {
        this.dataAccessObject = userSchedule;
        this.presenter = eventOutputBoundary;
    }

    /**
     * Execute class for the interactor.
     * @param eventInputData      the inputdata for the event
     */

    @Override
    public void execute(AddEventInputData eventInputData) {
        final String eventName = eventInputData.getEventName();
        final Optional<Event> optionalEvent = dataAccessObject.getEventByName(eventName);

        if (!dataAccessObject.getAllEvents().isEmpty() & optionalEvent.isPresent()) {
            // event already exists, cannot add
            presenter.prepareFailView("Event already exists");

        }
        else {

            if (eventInputData.getDayStart().compareTo(eventInputData.getDayEnd()) > 0) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else if (eventInputData.getDayEnd().compareTo(eventInputData.getDayStart()) == 0
                    && eventInputData.getTimeStart().isAfter(eventInputData.getTimeEnd())) {
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else {
                final EventFactory factory = new EventFactory();
                dataAccessObject.addEvent(factory.createFixedEvent(eventInputData.getEventName(),
                        eventInputData.getDayStart(),
                        eventInputData.getDayEnd(),
                        eventInputData.getTimeStart(),
                        eventInputData.getTimeEnd()));

                final AddEventOutputData eventOutputData = new AddEventOutputData(eventName, false);

                presenter.prepareSuccessView(eventOutputData);
            }
        }
    }
}
