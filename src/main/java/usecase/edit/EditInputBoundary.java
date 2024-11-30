package usecase.edit;

/**
 * The Edit Use Case.
 */
public interface EditInputBoundary {
    /**
     * Execute the Edit Use Case.
     * @param editInputData the input data for this use case
     */
    void execute(EditInputData editInputData);
}
