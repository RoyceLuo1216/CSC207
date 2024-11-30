package interface_adapter.repeat;

import usecase.edit.EditInputBoundary;
import usecase.edit.EditInputData;
import usecase.repeat.RepeatInputBoundary;
import usecase.repeat.RepeatInputData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Edit Use Case.
 */
public class RepeatController {
    private final RepeatInputBoundary repeatInteractor;

    public RepeatController(RepeatInputBoundary repeatInteractor) {
        this.repeatInteractor = repeatInteractor;
    }

    /**
     * Executes the Edit use case.
     * @param eventName the name of the event
     * @param dayStartString the start day of the event
     * @param dayEndString the end day of the event
     * @param timeStartString the start time of the event
     * @param timeEndString the end time of the event
     * @param daysRepeated the days repeated
     */
    public void execute(String eventName, String eventType, String dayStartString, String dayEndString,
                        String timeStartString, String timeEndString, List<String> daysRepeated) {

        DayOfWeek dayStart = DayOfWeek.valueOf(dayStartString.toUpperCase());
        DayOfWeek dayEnd = DayOfWeek.valueOf(dayEndString.toUpperCase());

        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("h:mm a");
        LocalTime timeStart = LocalTime.parse(timeStartString, timeFormatter);
        LocalTime timeEnd = LocalTime.parse(timeEndString, timeFormatter);

        List<DayOfWeek> daysRepeatedInput = new ArrayList<DayOfWeek>();
        for (String day : daysRepeated) {
            DayOfWeek dayOfWeek = DayOfWeek.valueOf(day.toUpperCase());
            daysRepeatedInput.add(dayOfWeek);
        }

        final RepeatInputData repeatInputData = new RepeatInputData(eventName, dayStart, dayEnd, timeStart, timeEnd, daysRepeatedInput);

        repeatInteractor.execute(repeatInputData);
    }
}

