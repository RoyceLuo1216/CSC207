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
     */
    public void execute() {
        deleteEventInteractor.deleteEvent();
    }

    /**
     * Swap back to schedule view.
     */
    public void scheduleView() {
        deleteEventInteractor.scheduleView();
    }

    /**
     * Back to edit view.
     */
    public void editView() {
        deleteEventInteractor.editView();
    }

    /**
     * Fetch event details.
     */
    public void fetchEventDetails() {
        deleteEventInteractor.fetchEventDetails();
    }

    /**
     * deletes an event.
     */
    public void deleteEvent() {
        deleteEventInteractor.deleteEvent();
    }

}
