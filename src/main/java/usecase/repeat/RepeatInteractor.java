package usecase.repeat;

import java.time.DayOfWeek;
import java.util.List;
import java.util.Optional;

import entities.eventEntity.Event;
import factory.EventFactory;

/**
 *  Interactor for Repeat Use Case.
 */
public class RepeatInteractor implements RepeatInputBoundary {
    private final RepeatEventDataAccessInterface repeatEventDataAccessObject;
    private final RepeatOutputBoundary presenter;

    public RepeatInteractor(RepeatEventDataAccessInterface userSchedule, RepeatOutputBoundary editOutputBoundary) {
        this.repeatEventDataAccessObject = userSchedule;
        this.presenter = editOutputBoundary;
    }

    @Override
    public void execute(RepeatInputData repeatInputData) {
        final List<DayOfWeek> numberDays = repeatInputData.getDaysRepeated();
        final String eventName = repeatInputData.getEventName();
        final Optional<Event> optionalEvent = repeatEventDataAccessObject.getEventByName(eventName);

        if (!repeatEventDataAccessObject.getAllEvents().isEmpty() & optionalEvent.isPresent()) {
            // event already exists, cannot add
            presenter.prepareFailView("Event already exists");

        }

        else if (numberDays.isEmpty()) {
            // user entered no repeat days
            presenter.prepareFailView("Please enter more than 0 repeat days");

        }
        else {

            if (repeatInputData.getDayStart().compareTo(repeatInputData.getDayEnd()) > 0) {
                // event fails for some reason, like duplicate event or incompatible times
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }

            else if (repeatInputData.getDayEnd().compareTo(repeatInputData.getDayStart()) == 0
                    && repeatInputData.getTimeStart().isAfter(repeatInputData.getTimeEnd())) {
                presenter.prepareFailView("Event can't be added, due to incompatible times");
            }
            else {
                final EventFactory factory = new EventFactory();
                repeatEventDataAccessObject.addEvent(
                        factory.createRepeatEvent(repeatInputData.getEventName(),
                                repeatInputData.getDayStart(),
                                repeatInputData.getDayEnd(),
                                repeatInputData.getTimeStart(),
                                repeatInputData.getTimeEnd(),
                                repeatInputData.getDaysRepeated()));

                final RepeatOutputData repeatOutputData = new RepeatOutputData(eventName, false);

                presenter.prepareSuccessView(repeatOutputData);
            }
        }
    }

    @Override
    public void backToMainView() {
        presenter.backToMainView();
    }
}
