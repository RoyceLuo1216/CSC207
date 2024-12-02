package usecase.delete;

/**
 *  DeleteEvent Output Boundary.
 */
public interface DeleteEventOutputBoundary {

    /**
     * Prepares the success view for Delete Event.
     *
     * @param outputData the ouptut data.
     */
    void presentSuccess(DeleteEventOutputData outputData);

    /**
     * Prepares the failure view for Delete Event.
     *
     * @param errorMessage error message.
     */
    void presentFailure(String errorMessage);

    /**
     * Transitions back to the main view.
     */
    void backToMainView();

}
