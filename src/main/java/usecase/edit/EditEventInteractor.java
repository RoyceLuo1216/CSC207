package usecase.edit;

import java.util.List;

import entities.eventEntity.Event;
import usecase.delete.DeleteEventOutputData;

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
        // Get current event details
        List<Object> eventDetails = dataAccess.getCurrentEventDetails();

        if (eventDetails.isEmpty()) {
            presenter.prepareFailView("Event not found or no current event selected.");
            return;
        }

        try {
            // Extract event details in the expected order
            String eventName = (String) eventDetails.get(0);
            String eventType = (String) eventDetails.get(1);
            String dayStart = eventDetails.get(2).toString();
            String dayEnd = eventDetails.get(3).toString();
            String timeStart = eventDetails.get(4).toString();
            String timeEnd = eventDetails.get(5).toString();

            // Update fields based on inputData
            eventName = inputData.getEventName() != null ? inputData.getEventName() : eventName;
            eventType = inputData.getEventType() != null ? inputData.getEventType() : eventType;
            dayStart = inputData.getDayStart() != null ? inputData.getDayStart().toString() : dayStart;
            dayEnd = inputData.getDayEnd() != null ? inputData.getDayEnd().toString() : dayEnd;
            timeStart = inputData.getTimeStart() != null ? inputData.getTimeStart().toString() : timeStart;
            timeEnd = inputData.getTimeEnd() != null ? inputData.getTimeEnd().toString() : timeEnd;

            // Send updated data to the presenter
            presenter.prepareSuccessView(new EditEventOutputData(
                    eventName, eventType, dayStart, dayEnd, timeStart, timeEnd,
                    "Successfully updated event."
            ));
        }
        catch (IndexOutOfBoundsException | ClassCastException e) {
            presenter.prepareFailView("Error retrieving or processing event details.");
        }
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
