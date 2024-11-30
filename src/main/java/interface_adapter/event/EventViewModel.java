package interface_adapter.event;

import interface_adapter.ViewModel;

public class EventViewModel extends ViewModel<EventState> {

    public EventViewModel(String viewName) {
        super("edit");
        setState(new EventState());
    }
}
