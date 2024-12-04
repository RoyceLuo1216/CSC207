package interface_adapter.edit;

import interface_adapter.ViewManagerModel;
import interface_adapter.delete.DeleteEventViewModel;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.edit.EditEventOutputBoundary;
import usecase.edit.EditEventOutputData;

import java.util.List;

/**
 * Presenter for the Edit Use Case.
 */
public class EditEventPresenter implements EditEventOutputBoundary {
    private final EditViewModel editViewModel;
    private final ScheduleViewModel scheduleViewModel;
    private final DeleteEventViewModel deleteEventViewModel;
    private final ViewManagerModel viewManagerModel;

    public EditEventPresenter(ViewManagerModel viewManagerModel, EditViewModel editViewModel,
                              ScheduleViewModel viewModel1, DeleteEventViewModel deleteEventViewModel1) {
        this.editViewModel = editViewModel;
        this.viewManagerModel = viewManagerModel;
        this.scheduleViewModel = viewModel1;
        this.deleteEventViewModel = deleteEventViewModel1;
    }

    @Override
    public void prepareSuccessView(EditEventOutputData outputData) {
        // output data doesn't need to change, just need to let the view know,
        // so it can alert the user that their updated information was saved.
        EditState state = editViewModel.getState();
        state.setEventName(outputData.getEventName());
        state.setEventType(outputData.getEventType());
        state.setDayStart(outputData.getDayStart());
        state.setDayEnd(outputData.getDayEnd());
        state.setTimeStart(outputData.getTimeStart());
        state.setTimeEnd(outputData.getTimeEnd());
        state.setOutputMessage(outputData.getOutputMessage());
        editViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareFailView(String errorMessage) {
        // returns an error message if the event was not updated properly
        final EditState editState = editViewModel.getState();
        editState.setOutputMessage(errorMessage);
        editViewModel.firePropertyChanged("edit");
    }

    @Override
    public void prepareRawEventFields(List<Object> eventFields, String successMessage) {
        prepareSuccessView(new EditEventOutputData(
                (String) eventFields.get(0),
                (String) eventFields.get(1),
                eventFields.get(2).toString(),
                eventFields.get(3).toString(),
                eventFields.get(4).toString(),
                eventFields.get(5).toString(),
                successMessage
        ));
    }

    /**
     * swap to schedule view.
     */
    @Override
    public void scheduleView() {
        System.out.println("Edit to Schedule");
        viewManagerModel.setState("schedule");
        viewManagerModel.firePropertyChanged();
    }

    /**
     * swap to delete view.
     */
    @Override
    public void deleteView() {
        System.out.println("Edit to Delete");
        viewManagerModel.setState("delete");
        viewManagerModel.firePropertyChanged();
    }
}
