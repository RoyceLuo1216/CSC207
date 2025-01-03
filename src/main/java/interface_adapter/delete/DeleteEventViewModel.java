package interface_adapter.delete;

import java.util.List;
import java.util.Map;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Delete Event Use Case.
 */
public class DeleteEventViewModel extends ViewModel<DeleteEventState> {
    
    public DeleteEventViewModel() {
        super("delete");
        setState(new DeleteEventState());
    }

}
