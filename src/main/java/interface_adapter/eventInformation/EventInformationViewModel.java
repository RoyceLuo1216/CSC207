package interface_adapter.eventInformation;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Edit Use Case.
 */
public class EventInformationViewModel extends ViewModel<EventInformationState> {

    public EventInformationViewModel(String viewName) {
        super(viewName);
        setState(new EventInformationState());
    }
}

