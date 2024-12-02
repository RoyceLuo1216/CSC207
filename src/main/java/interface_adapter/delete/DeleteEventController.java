package interface_adapter.delete;

import usecase.delete.DeleteEventInputBoundary;
import usecase.delete.DeleteEventInputData;

/**
 * Controller for the Delete Event Use Case.
 */
public class DeleteEventController {
    private final DeleteEventInputBoundary deleteEventInteractor;

    public DeleteEventController(DeleteEventInputBoundary deleteEventInteractor) {
        this.deleteEventInteractor = deleteEventInteractor;
    }

    /**
     * Executes the Delete Event Use Case.
     *
     * @param eventName the name of the event to delete
     */
    public void execute(String eventName) {
        final DeleteEventInputData inputData = new DeleteEventInputData(eventName);
        deleteEventInteractor.execute(inputData);
    }

}
