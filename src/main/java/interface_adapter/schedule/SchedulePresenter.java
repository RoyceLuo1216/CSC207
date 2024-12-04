package interface_adapter.schedule;

import interface_adapter.ViewManagerModel;
import interface_adapter.addEvent.AddEventViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import interface_adapter.edit.EditViewModel;
import usecase.schedule.ScheduleOutputBoundary;
import usecase.schedule.ScheduleOutputData;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

/**
 * Presenter for the Schedule Use Case.
 */
public class SchedulePresenter implements ScheduleOutputBoundary {

    private final ScheduleViewModel viewModel;
    private final AddEventViewModel addEventViewModel;
    private final TimeEstimationChatbotViewModel timeEstimationChatbotViewModel;
    private final EventConflictChatbotViewModel eventConflictChatbotViewModel;
    private final EditViewModel editViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Initializes the SchedulePresenter with the associated ViewModel.
     *
     * @param viewModel the ViewModel for the schedule
     * @param addEventViewModel the ViewModel for the add event use case
     * @param timeEstimationChatbotViewModel the ViewModel for the time estimation chatbot
     * @param eventConflictChatbotViewModel the ViewModel for the event conflict chatbot
     * @param viewManagerModel the viewManagerModel
     * @param editViewModel view model for edit.
     */
    public SchedulePresenter(ScheduleViewModel viewModel, AddEventViewModel addEventViewModel,
                             TimeEstimationChatbotViewModel timeEstimationChatbotViewModel,
                             EventConflictChatbotViewModel eventConflictChatbotViewModel,
                             ViewManagerModel viewManagerModel, EditViewModel editViewModel) {
        this.viewModel = viewModel;
        this.addEventViewModel = addEventViewModel;
        this.timeEstimationChatbotViewModel = timeEstimationChatbotViewModel;
        this.eventConflictChatbotViewModel = eventConflictChatbotViewModel;
        this.editViewModel = editViewModel;
        this.viewManagerModel = viewManagerModel;

    }

    /**
     * Prepares the data for the ViewModel and updates the state.
     *
     * @param outputData the output data from the use case
     */
    @Override
    public void presentView(ScheduleOutputData outputData) {
        ScheduleState state = viewModel.getState();
        System.out.println("hello");

        // Clear previous state
        state.getEventButtonMap().clear();

        // Populate the state with event data
        outputData.getEventNames().forEach(event -> {
            state.setEventDetails(event.getEventName(), event.getDayStart(), event.getTimeStart(), event.getDayEnd(), event.getTimeEnd());
            System.out.println(event);
            // Add placeholder for any necessary button/event logic
        });

        // Notify the ViewModel of the updated state
        viewModel.setState(state);
        viewModel.firePropertyChanged();
    }

    /**
     * Executes the pop-up event view use case.
     */
    @Override
    public void popUpAddEventView() {
        viewManagerModel.setState(addEventViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the pop-up time estimation view use case.
     */
    @Override
    public void popUpTimeEstimationChatbotView() {
        viewManagerModel.setState(timeEstimationChatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Executes the pop-up event conflict view use case.
     */
    @Override
    public void popUpEventConflictChatbotView() {
        viewManagerModel.setState(eventConflictChatbotViewModel.getViewName());
        System.out.println(eventConflictChatbotViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    /**
     * Edit view use case.
     */
    @Override
    public void editView(String eventName) {
        System.out.println("Schedule to edit Presenter");
        viewManagerModel.setState(editViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void updateScheduleWithEvents(Map<String, Map<String, Object>> eventDetailsMap) {
        // Convert event details map into a ScheduleState or equivalent format
        viewModel.setSuppressEvents(true);

        System.out.println("Upating schedule state" + eventDetailsMap);
        ScheduleState updatedState = convertToScheduleState(eventDetailsMap);

        // Only update if there is a meaningful change
        if (!viewModel.getState().equals(updatedState)) {
            viewModel.setState(updatedState);
        }

        // Reactivate property change events
        viewModel.setSuppressEvents(false);

    }

    private ScheduleState convertToScheduleState(Map<String, Map<String, Object>> eventDetailsMap) {
        ScheduleState state = new ScheduleState();

        eventDetailsMap.forEach((eventName, details) -> {
            DayOfWeek dayStart = (DayOfWeek) details.get("dayStart");
            LocalTime timeStart = (LocalTime) details.get("timeStart");
            DayOfWeek dayEnd = (DayOfWeek) details.get("dayEnd");
            LocalTime timeEnd = (LocalTime) details.get("timeEnd");

            state.setEventDetails(eventName, dayStart, timeStart, dayEnd, timeEnd);

            System.out.println(eventName + " " + dayStart + " " + timeStart + " " + dayEnd + " " + timeEnd);
        });

        return state;
    }

}
