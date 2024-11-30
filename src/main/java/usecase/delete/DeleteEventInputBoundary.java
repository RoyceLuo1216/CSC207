package usecase.delete;

public interface DeleteEventInputBoundary {
    /**
     * Execute the DeleteEvent Use Case.
     * @param deleteEventInputData the event that we want to delete in the module.
     */
    void execute(DeleteEventInputData deleteEventInputData);
}
