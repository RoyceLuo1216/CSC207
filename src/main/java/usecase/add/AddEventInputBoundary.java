package usecase.add;

/**
 * Input Boundary for add event use case.
 */
public interface AddEventInputBoundary {
    /**
     * Executes functions.
     * @param inputData input data for add event.
     * @return the output data.
     */
    AddEventOutputData execute(AddEventInputData inputData);
}
