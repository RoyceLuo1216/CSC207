package view;

import java.time.LocalDateTime;

public class EventViewData {
    private String eventName;
    private String eventType;
    private String dayStart;
    private String dayEnd;
    private String timeStart;
    private String timeEnd;
    private String priority;

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setDayStart(String dayStart) {
        this.dayStart = dayStart;
    }

    public String getDayStart() {
        return dayStart;
    }

    public void setDayEnd(String dayEnd) {
        this.dayEnd = dayEnd;
    }

    public String getDayEnd() {
        return dayEnd;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setEventPriority(String priority) {
        this.priority = priority;
    }

    public String getPriority() {
        return priority;
    }

    public void getAll(){
        System.out.println(getEventName());
        System.out.println(getEventType());
        System.out.println(getDayStart());
        System.out.println(getDayEnd());
        System.out.println(getTimeStart());
        System.out.println(getTimeEnd());
        System.out.println(getPriority());
    }

//    public EventViewData(String eventName, String eventType, String dayStart, String dayEnd, String timeStart, String timeEnd, String priority) {
//        this.eventName = eventName;
//        this.eventType = eventType;
//        this.dayStart = dayStart;
//        this.dayEnd = dayEnd;
//        this.timeStart = timeStart;
//        this.timeEnd = timeEnd;
//        this.priority = priority;

        // TODO: Format dayStart/End and timeStart/End to LocalDateTime and then put that logic in a new class
    // }
}
