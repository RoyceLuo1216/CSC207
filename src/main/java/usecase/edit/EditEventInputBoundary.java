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
     * Populates the event fields.
     */
    void populateEventFields();
}
