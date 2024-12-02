package usecase.event;

/**
 * The Repeat Use Case.
 */
public interface AddEventInputBoundary {
    /**
     * Execute the Event Use Case.
     * @param eventInputData the input data for this use case
     */
    void execute(AddEventInputData eventInputData);
}
