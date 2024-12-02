<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventState.java
package interface_adapter.addEvent;
========
package interface_adapter.eventAdd;
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddState.java

/**
 * The state for the Edit View Model.
 */
<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventState.java
public class AddEventState {
========
public class EventAddState {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddState.java
    private String eventName;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;

    private String eventError;

    public String getEventName() {
        return eventName;
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

    public String getEventError() {
        return eventError;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public void setEventError(String eventError) {
        this.eventError = eventError;
    }
}
