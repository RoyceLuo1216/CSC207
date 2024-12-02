package interface_adapter.edit;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Edit Use Case.
 */
public class EditViewModel extends ViewModel<EditState> {

    public EditViewModel() {
        super("edit");
        setState(new EditState());
    }
}
