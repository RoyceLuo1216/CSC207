package usecase.event;

/**
 * The output boundary for the Edit use case.
 */
public interface EventOutputBoundary {
    /**
     * Prepares the success view for the Edit use case.
     * @param outputData the output data
     */
    void prepareSuccessView(EventOutputData outputData);

    /**
     * Prepares the failure view for the Edit use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Returns the back to main schedule view.
     */
    void backToMainView();
}
