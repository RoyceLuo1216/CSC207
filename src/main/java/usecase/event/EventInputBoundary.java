package usecase.event;

/**
 * The Repeat Use Case.
 */
public interface EventInputBoundary {
    /**
     * Execute the Event Use Case.
     * @param eventInputData the input data for this use case
     */
    void execute(EventInputData eventInputData);

    /**
     * Returns the back to main schedule view.
     */
    void backToMainView();
}
