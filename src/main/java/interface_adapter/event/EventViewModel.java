package interface_adapter.event;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Event Add Use Case.
 */
public class EventViewModel extends ViewModel<EventState> {

    public EventViewModel(String viewName) {
        super("event");
        setState(new EventState());
    }
}
