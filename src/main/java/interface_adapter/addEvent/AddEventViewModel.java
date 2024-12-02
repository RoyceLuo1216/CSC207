package interface_adapter.addEvent;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Event Add Use Case.
 */
public class AddEventViewModel extends ViewModel<AddEventState> {

    public AddEventViewModel() {
        super("add");
        setState(new AddEventState());
    }
}
