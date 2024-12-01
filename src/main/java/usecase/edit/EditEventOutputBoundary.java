package usecase.edit;

/**
 * The output boundary for the Edit use case.
 */
public interface EditOutputBoundary {
    /**
     * Prepares the success view for the Edit use case.
     *
     * @param outputData the output data
     */
    void prepareSuccessView(EditOutputData outputData);

    /**
     * Prepares the failure view for the Edit use case.
     *
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);
}
