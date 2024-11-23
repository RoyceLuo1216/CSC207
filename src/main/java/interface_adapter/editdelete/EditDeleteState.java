package interface_adapter.editdelete;

/**
 * The EditDeleteState contains data to render the EditDeleteView.
 */
public class EditDeleteState {
    private final String name;
    private final String startTime;
    private final String endTime;

    public EditDeleteState(String name, String startTime, String endTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getName() {
        return name;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }
}
