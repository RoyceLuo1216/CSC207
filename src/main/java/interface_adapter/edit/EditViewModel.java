package interface_adapter.edit;

import interface_adapter.ViewModel;

/**
 * The ViewModel for the Edit Use Case.
 */
public class EditViewModel extends ViewModel<EditState> {
    public static final String[] eventTypes = {"Fixed", "Repeat"};
    public static final String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
    public static final String[] times = {"12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM",
                                        "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM",
                                        "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM",
                                        "9:00 PM", "10:00 PM", "11:00 PM"};

    public EditViewModel() {
        super("edit");
        setState(new EditState());
        System.out.println("Property changed: edit");
    }

}
