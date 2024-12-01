package interface_adapter.repeat;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Repeat Add Use Case.
 */
public class RepeatViewModel extends ViewModel<RepeatState> {

    public RepeatViewModel(String viewName) {
        super("repeat");
        setState(new RepeatState());
    }
}
