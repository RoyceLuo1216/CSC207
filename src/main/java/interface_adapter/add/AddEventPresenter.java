package interface_adapter.add ;

import interface_adapter.edit.EditState;
import usecase.add.AddEventOutputBoundary;
import usecase.add.AddEventOutputData;

/**
 * Presenter for the AddEvent use case, updates the ViewModel based on the interactor's response.
 */
public class AddEventPresenter implements AddEventOutputBoundary {
    private final AddEventViewModel viewModel;

    public AddEventPresenter(AddEventViewModel viewModel) {
        this.viewModel = viewModel;
    }

    /**
     * Prepares the success view by updating the ViewModel with the success message.
     *
     * @param outputData the output data containing the details of the added event.
     * @return the same output data, indicating the operation was successful.
     */
    @Override
    public AddEventOutputData prepareSuccessView(AddEventOutputData outputData) {
        final EditState editState = editViewModel.getState();
        editState.setOutputMessage(outputData.getOutputMessage());
        AddEventViewModel.firePropertyChanged("edit");
    }

    /**
     * Prepares the failure view by updating the ViewModel with the error message.
     *
     * @param errorMessage the error message describing why the operation failed.
     * @return an instance of AddEventOutputData containing the error message.
     */
    @Override
    public AddEventOutputData prepareFailView(String errorMessage) {
        AddEventState currentState = viewModel.getState();
        currentState.setErrorMessage(errorMessage);
        viewModel.setState(currentState);

        return new AddEventOutputData("", null, null, null, null);
    }
}
