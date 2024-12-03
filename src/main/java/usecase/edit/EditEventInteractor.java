package usecase.edit;

import java.util.List;

import entities.eventEntity.Event;

/**
 * Interactor for the Edit Use Case.
 */
public class EditEventInteractor implements EditEventInputBoundary {
    private final EditEventDataAccessInterface dataAccess;
    private final EditEventOutputBoundary presenter;

    public EditEventInteractor(EditEventDataAccessInterface dataAccess, EditEventOutputBoundary presenter) {
        this.dataAccess = dataAccess;
        this.presenter = presenter;
    }

    @Override
    public void execute(EditEventInputData inputData) {
        Event event = dataAccess.getEventByName(inputData.getEventName())
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));

        // Update Event Data
        event.setDayStart(inputData.getDayStart());
        event.setDayEnd(inputData.getDayEnd());
        event.setTimeStart(inputData.getTimeStart());
        event.setTimeEnd(inputData.getTimeEnd());

        presenter.prepareSuccessView(new EditEventOutputData(
                event.getEventName(), event.getEventType(),
                inputData.getDayStart().toString(), inputData.getDayEnd().toString(),
                inputData.getTimeStart().toString(), inputData.getTimeEnd().toString(),
                "Successfully updated event."
        ));
    }

    @Override
    public void populateEventFields() {
        List<Object> eventFields = dataAccess.getCurrentEventDetails();
        if (eventFields.isEmpty()) {
            presenter.prepareFailView("No event selected.");
        } else {
            presenter.prepareRawEventFields(eventFields, "Event details fetched.");
        }
    }

    /**
     * Switch to the event view.
     */
    @Override
    public void scheduleView() {
        presenter.scheduleView();
    }

    /**
     * Switch to the delete view.
     */
    @Override
    public void deleteView() {
        presenter.deleteView();
    }
}
