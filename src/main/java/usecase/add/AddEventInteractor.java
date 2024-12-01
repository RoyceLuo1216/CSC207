package usecase.add;

import entities.EventEntity.Event;
import entities.EventEntity.EventFactory;
import java.time.DayOfWeek;
import java.time.LocalTime;

/**
 * Interactor for adding events to the schedule.
 * Handles validation and delegates failures to the presenter.
 */
public class AddEventInteractor implements AddEventInputBoundary {

    private final EventDataAccessInterface dataAccessObject;
    private final AddEventOutputBoundary presenter;
    private final EventFactory eventFactory;

    /**
     * Constructor for AddEventInteractor.
     *
     * @param dataAccessObject the data access interface for event management
     * @param presenter        the presenter to handle failure views
     * @param eventFactory     the factory used to create event instances
     */
    public AddEventInteractor(EventDataAccessInterface dataAccessObject,
                              AddEventOutputBoundary presenter,
                              EventFactory eventFactory) {
        this.dataAccessObject = dataAccessObject;
        this.presenter = presenter;
        this.eventFactory = eventFactory;
    }

    /**
     * Executes the use case to add an event to the schedule.
     *
     * @param inputData the input data containing event details
     */
    @Override
    public void execute(AddEventInputData inputData) {
        final String eventName = inputData.getEventName();
        final DayOfWeek startDay = inputData.getDayStart();
        final DayOfWeek endDay = inputData.getDayEnd();
        final LocalTime startTime = inputData.getTimeStart();
        final LocalTime endTime = inputData.getTimeEnd();

        // Check if an event with the same name already exists
        if (dataAccessObject.eventExists(eventName)) {
            presenter.presentFailure("The event '" + eventName + "' already exists.");
            return;
        }

        // Check if start time is before end time on the same day
        if (startDay.equals(endDay) && startTime.isAfter(endTime)) {
            presenter.presentFailure("The start time must be before the end time.");
            return;
        }

        // Check if start day is before or the same as the end day
        if (startDay.compareTo(endDay) > 0) {
            presenter.presentFailure("The start day must not be after the end day.");
            return;
        }

        // Create the event using the event factory
        Event event = eventFactory.createFixedEvent(eventName, startDay, endDay, startTime, endTime);

        // Add the event to the schedule
        boolean success = dataAccessObject.addEvent(event);

        // Handle failure if adding the event fails
        if (!success) {
            presenter.presentFailure("Failed to add the event '" + eventName + "'.");
        }
    }
}
