package interface_adapter.repeat;

import interface_adapter.ViewModel;

public class RepeatViewModel extends ViewModel<RepeatState> {

    public RepeatViewModel(String viewName) {
        super("edit");
        setState(new RepeatState());
    }
}
