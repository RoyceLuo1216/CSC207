package usecase.event;

import java.util.Optional;

import entities.eventEntity.Event;
import factory.EventFactory;

/**
 *  Interactor for Event Add Use Case. Implements abstraction defined in EventInputBoundary.
 */
public class EventAddInteractor implements EventAddInputBoundary {
    private final EventAddDataAccessInterface dataAccessObject;
    private final EventAddOutputBoundary presenter;

    public EventAddInteractor(EventAddDataAccessInterface userSchedule, EventAddOutputBoundary eventAddOutputBoundary) {
        this.dataAccessObject = userSchedule;
        this.presenter = eventAddOutputBoundary;
    }

    /**
     * Execute class for the interactor.
     * @param eventAddInputData      the inputdata for the event
     */

    @Override
    public void execute(EventAddInputData eventAddInputData) {
        final String eventName = eventAddInputData.getEventName();
        final Optional<Event> optionalEvent = dataAccessObject.getEventByName(eventName);

        if (!dataAccessObject.getAllEvents().isEmpty() & optionalEvent.isPresent()) {
            // event already exists, cannot add
            presenter.prepareFailView("Event already exists");

        }
        else {

            if (eventAddInputData.getDayStart().compareTo(eventAddInputData.getDayEnd()) > 0) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else if (eventAddInputData.getDayEnd().compareTo(eventAddInputData.getDayStart()) == 0
                    && eventAddInputData.getTimeStart().isAfter(eventAddInputData.getTimeEnd())) {
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else {
                final EventFactory factory = new EventFactory();
                dataAccessObject.addEvent(factory.createFixedEvent(eventAddInputData.getEventName(),
                        eventAddInputData.getDayStart(),
                        eventAddInputData.getDayEnd(),
                        eventAddInputData.getTimeStart(),
                        eventAddInputData.getTimeEnd()));

                final EventAddOutputData eventAddOutputData = new EventAddOutputData(eventName, false);

                presenter.prepareSuccessView(eventAddOutputData);
            }
        }
    }

    @Override
    public void backToMainView() {
        presenter.backToMainView();
    }
}
