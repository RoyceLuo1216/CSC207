package interface_adapter.schedule;

import interface_adapter.ViewModel;

import java.util.List;
import java.util.Map;

/**
 * ViewModel for the Schedule Use Case.
 */
public class ScheduleViewModel extends ViewModel<ScheduleState> {
    private ScheduleState state;

    /**
     * Constructs the ScheduleViewModel with the "schedule" view name.
     */
    public ScheduleViewModel() {
        super("schedule");
        setState(new ScheduleState());
    }

}
