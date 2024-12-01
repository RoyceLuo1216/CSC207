package usecase.eventInformation;

import java.util.ArrayList;
import java.util.Optional;

import entities.eventEntity.Event;
import entities.eventEntity.RepeatEvent;

/**
 * The Event Information Interactor.
 */
public class EventInformationInteractor implements EventInformationInputBoundary {
    private final EventInformationDataAccessInterface dataAccessObject;
    private final EventInformationOutputBoundary presenter;

    public EventInformationInteractor(EventInformationDataAccessInterface dataAccessObject,
                          EventInformationOutputBoundary eventInformationOutputBoundary) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = eventInformationOutputBoundary;
    }

    @Override
    public void execute(EventInformationInputData eventInformationInputData) {
        final String eventName = eventInformationInputData.getEventName();
        final Optional<Event> optionalEvent = dataAccessObject.getEventByName(eventName);

        // since this is called from button click on the schedule, we knkow the event exists
        final Event event = optionalEvent.get();
        final String eventType = event.getClass().getSimpleName();

        if ("RepeatEvent".equals(eventType)) {
            // event is a repeat event, and thus we have an array to input for daysRepeated
            final RepeatEvent repeatEvent = (RepeatEvent) event;
            final EventInformationOutputData eventInformationOutputData = new EventInformationOutputData(
                    repeatEvent.getEventName(), "Repeat", repeatEvent.getDayStart(), repeatEvent.getDayEnd(),
                    repeatEvent.getTimeStart(), repeatEvent.getTimeEnd(), repeatEvent.getDaysRepeated());

            presenter.prepareView(eventInformationOutputData);

        }
        else {
            final EventInformationOutputData eventInformationOutputData = new EventInformationOutputData(
                    event.getEventName(), "Fixed", event.getDayStart(), event.getDayEnd(), event.getTimeStart(),
                    event.getTimeEnd(), new ArrayList<>());
            presenter.prepareView(eventInformationOutputData);
        }
    }
}
