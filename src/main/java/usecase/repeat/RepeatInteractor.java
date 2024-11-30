package usecase.repeat;

import entities.ScheduleEntity.Schedule;
import entities.EventEntity.Event;
import usecase.edit.EditOutputData;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

public class RepeatInteractor implements RepeatInputBoundary {
    private final Schedule userSchedule;
    private final RepeatOutputBoundary presenter;

    public RepeatInteractor(Schedule userSchedule, RepeatOutputBoundary editOutputBoundary) {
        this.userSchedule = userSchedule;
        this.presenter = editOutputBoundary;
    }

    @Override
    public void execute(RepeatInputData repeatInputData) {
        final List<DayOfWeek> numberDays = repeatInputData.getDaysRepeated();
        String eventName = repeatInputData.getEventName();

        if (numberDays.isEmpty()) {
            // event is not present, tell user that the event does not exist
            presenter.prepareFailView("Please enter more than 0 repeat days");

        } else {
            userSchedule.createRepeatEvent(repeatInputData.getEventName(), repeatInputData.getDayStart(),
                    repeatInputData.getDayEnd(), repeatInputData.getTimeStart(), repeatInputData.getTimeEnd(),
                    repeatInputData.getDaysRepeated());

            final RepeatOutputData repeatOutputData = new RepeatOutputData(eventName, false);

            presenter.prepareSuccessView(repeatOutputData);
        }
    }
}
