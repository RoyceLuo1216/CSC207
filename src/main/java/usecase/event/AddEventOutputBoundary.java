package usecase.event;

/**
 * The output boundary for the Edit use case.
 */
public interface AddEventOutputBoundary {

    /**
     * Prepares the success view for the Edit use case.
     * @param outputData the output data
     */
    void prepareSuccessView(AddEventOutputData outputData);

    /**
     * Prepares the failure view for the Edit use case.
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Transitions back to the main view.
     */
    void backToMainView();

}
