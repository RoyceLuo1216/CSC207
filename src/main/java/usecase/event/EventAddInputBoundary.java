package usecase.event;

/**
 * The Repeat Use Case.
 */
public interface EventAddInputBoundary {
    /**
     * Execute the Event Use Case.
     * @param eventAddInputData the input data for this use case
     */
    void execute(EventAddInputData eventAddInputData);

    /**
     * Returns the back to main schedule view.
     */
    void backToMainView();
}
