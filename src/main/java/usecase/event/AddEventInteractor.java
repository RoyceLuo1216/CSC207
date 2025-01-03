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
    private final EventFactory eventFactory;

    public AddEventInteractor(AddEventDataAccessInterface userSchedule, AddEventOutputBoundary eventAddOutputBoundary,
                              EventFactory eventFactory) {
        this.dataAccessObject = userSchedule;
        this.presenter = eventAddOutputBoundary;
        this.eventFactory = eventFactory;
    }

    /**
     * Execute class for the interactor.
     * @param eventAddInputData      the inputdata for the event
     */
    @Override
    public void execute(AddEventInputData eventAddInputData) {
        System.out.println(eventAddInputData.getEventName());
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

                final AddEventOutputData eventAddOutputData = new AddEventOutputData(eventName, false);
                System.out.println("event added to database");
                presenter.prepareSuccessView(eventAddOutputData);
            }
        }
    }

    /**
     * Switch back to schedule view.
     */
    public void backToScheduleView() {

        presenter.backToScheduleView();
    }

}
