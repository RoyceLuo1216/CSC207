package usecase.delete;

/**
 *  Interactor for DeleteEvent Use Case. Implements abstraction defined in DeleteEventInputBoundary.
 */
public class DeleteEventInteractor implements DeleteEventInputBoundary {
    private final DeleteEventOutputBoundary presenter;
    private final DeleteEventDataAccessInterface dataAccessObject;

    public DeleteEventInteractor(DeleteEventOutputBoundary presenter, DeleteEventDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(DeleteEventInputData inputData) {
        System.out.println("Deleting event: " + inputData.getEventName());
        dataAccessObject.deleteEvent(inputData.getEventName());

        presenter.presentSuccess(new DeleteEventOutputData(inputData.getEventName()));

    }

    /**
     * Fetch event details.
     */
    @Override
    public void fetchEventDetails() {
        String eventName = dataAccessObject.getCurrentEventName();
        if (eventName == null || eventName.isEmpty()) {
            presenter.presentFailure("No current event found.");
        } else {
            presenter.prepareEventDetails(new DeleteEventOutputData(eventName));
        }
    }


    @Override
    public void deleteEvent() {
        System.out.println("Deleting event: " + dataAccessObject.getCurrentEventName());
        String eventName = dataAccessObject.getCurrentEventName();
        if (eventName == null || eventName.isEmpty()) {
            presenter.presentFailure("No current event to delete.");
        }
        else {
            System.out.println("Deleting event (interactor): " + eventName);
            dataAccessObject.deleteEvent(eventName);
            presenter.presentSuccess(new DeleteEventOutputData(eventName));
        }
    }

    /**
     * swap to schedule.
     */
    @Override
    public void scheduleView() {
        presenter.backToScheduleView();
    }

    /**
     * Swap back to edit.
     */
    @Override
    public void editView() {
        presenter.editView();
    }

}
