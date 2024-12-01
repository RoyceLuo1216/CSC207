package interface_adapter.eventInformation;

import usecase.eventInformation.EventInformationInputBoundary;
import usecase.eventInformation.EventInformationInputData;

/**
 * The controller for the Login Use Case.
 */
public class EventInformationController {

    private final EventInformationInputBoundary eventInformationUseCaseInteractor;

    public EventInformationController(EventInformationInputBoundary eventInformationUseCaseInteractor) {
        this.eventInformationUseCaseInteractor = eventInformationUseCaseInteractor;
    }

    /**
     * Executes the Event Information Use Case.
     * @param eventName the name of the event we want information for
     */
    public void execute(String eventName) {
        final EventInformationInputData eventInformationInputData = new EventInformationInputData(eventName);

        eventInformationUseCaseInteractor.execute(eventInformationInputData);
    }
}
