package interface_adapter.delete;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Delete Event Use Case.
 */
public class DeleteEventViewModel extends ViewModel<interface_adapter.editdelete.DeleteEventState> {

    public DeleteEventViewModel(String viewName) {
        super("delete");
        setState(new interface_adapter.editdelete.DeleteEventState());
    }

}