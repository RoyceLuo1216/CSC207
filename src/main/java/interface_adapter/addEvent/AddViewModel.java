package interface_adapter.addEvent;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Event Add Use Case.
 */
public class AddViewModel extends ViewModel<AddEventState> {

    public AddViewModel(String viewName) {
        super("event");
        setState(new AddEventState());
    }
}
