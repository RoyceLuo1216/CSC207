package interface_adapter.edit;

import interface_adapter.ViewModel;

public class EditViewModel extends ViewModel<EditState> {

    public EditViewModel(String viewName) {
        super("edit");
        setState(new EditState());
    }
}
