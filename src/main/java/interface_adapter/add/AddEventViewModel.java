package interface_adapter.add;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * ViewModel for the AddEvent use case. Maintains the state and notifies listeners of changes.
 */
public class AddEventViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private AddEventState state = new AddEventState();

    public AddEventState getState() {
        return state;
    }

    public void setState(AddEventState state) {
        AddEventState oldState = this.state;
        this.state = state;
        support.firePropertyChange("state", oldState, state);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }
}
