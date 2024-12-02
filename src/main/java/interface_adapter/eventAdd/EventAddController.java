<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventController.java
package interface_adapter.addEvent;
========
package interface_adapter.eventAdd;
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddController.java

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventController.java
import usecase.event.AddEventInputBoundary;
import usecase.event.AddEventInputData;
========
import usecase.event.EventAddInputBoundary;
import usecase.event.EventAddInputData;
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddController.java

/**
 * Controller for the Add Event Use Case.
 */
<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventController.java
public class AddEventController {
    private final AddEventInputBoundary eventInteractor;

    public AddEventController(AddEventInputBoundary eventInteractor) {
========
public class EventAddController {
    private final EventAddInputBoundary eventInteractor;

    public EventAddController(EventAddInputBoundary eventInteractor) {
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddController.java
        this.eventInteractor = eventInteractor;
    }

    /**
     * Executes the Edit use case.
     * @param eventName the name of the event
     * @param dayStartString the start day of the event
     * @param dayEndString the end day of the event
     * @param timeStartString the start time of the event
     * @param timeEndString the end time of the event
     */
    public void execute(String eventName, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString) {

        final DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        final DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        final LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        final LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

<<<<<<<< HEAD:src/main/java/interface_adapter/addEvent/AddEventController.java
        final AddEventInputData eventInputData = new AddEventInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);
========
        final EventAddInputData eventAddInputData = new EventAddInputData(eventName, dayStart, dayEnd, timeStart, timeEnd);
>>>>>>>> 0786677a92101c9e249ab29a838fede2122e7ab6:src/main/java/interface_adapter/eventAdd/EventAddController.java

        eventInteractor.execute(eventAddInputData);
    }

    /**
     * Returns the back to main schedule view.
     */
    public void backToMainView() {
        eventInteractor.backToMainView();
    }
}
