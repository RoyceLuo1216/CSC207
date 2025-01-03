package usecase.eventInformation;

/**
 * The output boundary for the Event Information use case.
 */
public interface EventInformationOutputBoundary {

    /**
     * Prepares the view for the Event Information use case.
     *
     * @param outputData the output data
     */
    void prepareView(EventInformationOutputData outputData);

    /**
     * Switch to edit event view.
     */
    void switchToEditEventView();

    /**
     * prepare failView
     * @param message to be printed.
     */
    void prepareFailView(String message);
}

