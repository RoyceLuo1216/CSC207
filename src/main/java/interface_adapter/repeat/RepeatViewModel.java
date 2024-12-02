package interface_adapter.repeat;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Repeat Add Use Case.
 */
public class RepeatViewModel extends ViewModel<RepeatState> {
    // Setup Components
    public static final String[] EVENT_TYPES = {"Fixed", "Flexible", "Repeat"};
    public static final String[] DAYS_OF_WEEK = {"Monday", "Tuesday", "Wednesday", "Thursday",
                                                    "Friday", "Saturday", "Sunday"};
    public static final String[] TIMES = {"12:00 AM", "1:00 AM", "2:00 AM",
        "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM", "7:00 AM", "8:00 AM",
        "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM", "2:00 PM",
        "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM", "9:00 PM",
        "10:00 PM", "11:00 PM"};

    public RepeatViewModel() {
        super("repeat");
        setState(new RepeatState());
    }
}
