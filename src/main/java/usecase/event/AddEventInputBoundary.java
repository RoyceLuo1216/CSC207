package usecase.event;

/**
 * The Repeat Use Case.
 */
public interface AddEventInputBoundary {
    /**
     * Execute the Event Use Case.
     * @param eventAddInputData the input data for this use case
     */
    void execute(AddEventInputData eventAddInputData);
}
