package usecase.delete;


public class DeleteEventInteractor implements DeleteEventInputBoundary {
    private final DeleteEventOutputBoundary presenter;
    private final DeleteEventDataAccessInterface dataAccessObject;

    public DeleteEventInteractor(DeleteEventOutputBoundary presenter, DeleteEventDataAccessInterface dataAccessObject) {
        this.presenter = presenter;
        this.dataAccessObject = dataAccessObject;
    }

    @Override
    public void execute(DeleteEventInputData inputData) {
        final String eventName = inputData.getEventName();

        if (!dataAccessObject.getEventByName(eventName).isPresent()) {
            presenter.presentFailure("The event, " + eventName + " does not exist.");
        }
        else {
            DeleteEventOutputData outputData = new DeleteEventOutputData(inputData.getEventName());

            dataAccessObject.deleteEvent(eventName);
            presenter.presentSuccess(outputData);
        }
    }
}
