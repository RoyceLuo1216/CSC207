package usecase.add;

/**
 * Output Boundary Interface.
 */
public interface AddEventOutputBoundary {
    /**
     * Returns data for success view.
     * @param outputData output data.
     * @return data for success view.
     */
    AddEventOutputData prepareSuccessView(AddEventOutputData outputData);

    /**
     * Returns data for fail view.
     * @param errorMessage error message to input.
     * @return message for error.
     */
    AddEventOutputData prepareFailView(String errorMessage);
}

