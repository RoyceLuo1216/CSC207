package interface_adapter.edit;

import interface_adapter.ViewManagerModel;
import usecase.edit.EditEventOutputBoundary;
import usecase.edit.EditEventOutputData;

import java.util.List;

/**
 * Presenter for the Edit Use Case.
 */
public class EditEventPresenter implements EditEventOutputBoundary {
    private final EditViewModel editViewModel;

    public EditEventPresenter(ViewManagerModel viewManagerModel, EditViewModel editViewModel) {
        this.editViewModel = editViewModel;
    }

    @Override
    public void prepareSuccessView(EditEventOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        final EditState editState = editViewModel.getState();
        editState.setOutputMessage(outputData.getOutputMessage());
        editViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final EditState editState = editViewModel.getState();
        editState.setOutputMessage(errorMessage);
        editViewModel.firePropertyChanged();
    }

    @Override
    public void prepareRawEventFields(List<Object> eventFields, String successMessage) {
        // Construct the output data object from the raw fields
        final EditEventOutputData outputData = new EditEventOutputData(
                (String) eventFields.get(0),
                false,
                successMessage
        );

        // Notify the view through the output data
        prepareSuccessView(outputData);
    }

}
