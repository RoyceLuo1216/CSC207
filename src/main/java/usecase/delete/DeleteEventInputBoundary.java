package usecase.delete;

/**
 * This acts as the input point for any delete event use cases to access data and execute.
 */
public interface DeleteEventInputBoundary {
    /**
     * Execute the DeleteEvent Use Case.
     *
     * @param deleteEventInputData the event that we want to delete in the module.
     */
    void execute(DeleteEventInputData deleteEventInputData);
}
