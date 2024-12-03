package usecase.edit;

import java.util.List;

/**
 * The Edit Use Case.
 */
public interface EditEventInputBoundary {
    /**
     * Execute the Edit Use Case.
     *
     * @param editEventInputData the input data for this use case
     */
    void execute(EditEventInputData editEventInputData);

    /**
     * Returns the current event name.
     * @return string of current event name.
     */
    String getCurrentEventName();

    /**
     * Returns a list of the fields associated with the event.
     * @return list of all event items needed.
     */
    List<Object> getEventFields();

    /**
     * Populates the event fields.
     */
    void populateEventFields();
}
