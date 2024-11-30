package usecase.delete;

public interface DeleteEventOutputBoundary {

    /**
     * Prepares the success view for Delete Event.
     * @param outputData the ouptut data.
     */
    void presentSuccess(DeleteEventOutputData outputData);

    /**
     *  Prepares the failure view for Delete Event.
     * @param errorMessage error message.
     */
    void presentFailure(String errorMessage);
}
