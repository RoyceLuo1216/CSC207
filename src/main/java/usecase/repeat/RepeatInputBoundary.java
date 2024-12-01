package usecase.repeat;

/**
 * The Repeat Use Case.
 */
public interface RepeatInputBoundary {
    /**
     * Execute the Edit Use Case.
     * @param repeatInputData the input data for this use case
     */
    void execute(RepeatInputData repeatInputData);
}
