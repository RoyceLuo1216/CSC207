package interface_adapter.eventInformation;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;

import usecase.eventInformation.EventInformationOutputBoundary;
import usecase.eventInformation.EventInformationOutputData;

/**
 * Presenter for the Event Information Use Case.
 */
public class EventInformationPresenter implements EventInformationOutputBoundary {
    private final EventInformationViewModel eventInformationViewModel;

    public EventInformationPresenter(EventInformationViewModel eventInformationViewModel) {
        this.eventInformationViewModel = eventInformationViewModel;
    }

    /**
     * Prepares the view for the Event Information use case.
     *
     * @param outputData the output data
     */
    @Override
    public void prepareView(EventInformationOutputData outputData) {
        final EventInformationState eventInformationState = eventInformationViewModel.getState();
        eventInformationState.setEventName(outputData.getEventName());
        eventInformationState.setEventType(outputData.getEventType());
        eventInformationState.setDayStart(outputData.getDayStart().toString());
        eventInformationState.setDayEnd(outputData.getDayEnd().toString());
        eventInformationState.setTimeStart(outputData.getTimeStart().toString());
        eventInformationState.setTimeEnd(outputData.getTimeEnd().toString());

        if ("Repeat".equals(outputData.getEventType())) {
            final List<String> daysRepeatedString = new ArrayList<>();
            for (DayOfWeek day : outputData.getDaysRepeated()) {
                daysRepeatedString.add(day.toString().toUpperCase());
                eventInformationState.setDaysRepeated(daysRepeatedString);
            }
        }

        eventInformationViewModel.firePropertyChanged("eventInformation");
    }

    /**
     * Switch to edit event view.
     */
    @Override
    public void switchToEditEventView() {
        final EventInformationState eventState = eventInformationViewModel.getState();
        eventInformationViewModel.setState(eventState);
        eventInformationViewModel.firePropertyChanged("state");
    }

    /**
     * prepare failView.
     *
     * @param message error message.
     */
    @Override
    public void prepareFailView(String message) {
        final EventInformationState eventState = eventInformationViewModel.getState();
        eventState.setResponseError(message);
        eventInformationViewModel.firePropertyChanged();
    }

}
