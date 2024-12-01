package interface_adapter.schedule;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Schedule Use Case.
 */
public class ScheduleViewModel extends ViewModel<ScheduleState> {

    /**
     * Constructs the ScheduleViewModel with the "schedule" view name.
     */
    public ScheduleViewModel() {
        super("schedule");
        setState(new ScheduleState());
    }
}
