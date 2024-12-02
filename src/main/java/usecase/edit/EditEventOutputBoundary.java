package usecase.edit;

/**
 * The output boundary for the Edit use case.
 */
public interface EditEventOutputBoundary {
    /**
     * Prepares the success view for the Edit use case.
     *
     * @param outputData the output data
     */
    void prepareSuccessView(EditEventOutputData outputData);

    /**
     * Prepares the failure view for the Edit use case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
