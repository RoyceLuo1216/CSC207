package usecase.edit;

/**
 * Output Data for the Edit use case.
 */
public class EditEventOutputData {
    private final String eventName;
    private final String eventType;
    private final String dayStart;
    private final String dayEnd;
    private final String timeStart;
    private final String timeEnd;
    private final String outputMessage;

    public EditEventOutputData(String eventName, String eventType, String dayStart, String dayEnd,
                               String timeStart, String timeEnd, String outputMessage) {
        this.eventName = eventName;
        this.eventType = eventType;
        this.dayStart = dayStart;
        this.dayEnd = dayEnd;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.outputMessage = outputMessage;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventType() {
        return eventType;
    }

    public String getDayStart() {
        return dayStart;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public String getOutputMessage() {
        return outputMessage;
    }
}
