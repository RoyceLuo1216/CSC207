package view;

/**
 * The Controller for the add to schedule use case (Team story, basic functionality).
 */
public class EventViewController {
    private String eventName;
    private String eventType;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;

    /**
     * Method that takes user input and assigns each to its corresponding instance variable.
     * @param name     the name of the event
     * @param type     the type of the event (fixed, repeat)
     * @param startDay      the start day of the event
     * @param endDay        the end day of the event
     * @param startTime     the start time of the event
     * @param endTime   the end time of the event
     */
    public void saveEvent(String name, String type, String startDay, String endDay,
                         String startTime, String endTime) {
        eventName = name;
        eventType = type;
        dayStart = startDay;
        dayEnd = endDay;
        timeStart = startTime;
        timeEnd = endTime;
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

    /**
     * Prints all instance variables.
     */
    public void getAll() {
        System.out.println(getEventName());
        System.out.println(getEventType());
        System.out.println(getDayStart());
        System.out.println(getDayEnd());
        System.out.println(getTimeStart());
        System.out.println(getTimeEnd());
    }
}
