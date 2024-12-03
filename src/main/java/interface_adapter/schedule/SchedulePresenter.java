package interface_adapter.schedule;

import interface_adapter.ViewManagerModel;
import interface_adapter.addEvent.AddEventViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;
import usecase.schedule.ScheduleOutputBoundary;
import usecase.schedule.ScheduleOutputData;

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
     * @param editViewModel the viewmodel for edit view.
     */
    public SchedulePresenter(ScheduleViewModel viewModel, AddEventViewModel addEventViewModel,
                             TimeEstimationChatbotViewModel timeEstimationChatbotViewModel,
                             EventConflictChatbotViewModel eventConflictChatbotViewModel,
                             ViewManagerModel viewManagerModel, EditViewModel editViewModel) {
        this.viewModel = viewModel;
        this.addEventViewModel = addEventViewModel;
        this.timeEstimationChatbotViewModel = timeEstimationChatbotViewModel;
        this.eventConflictChatbotViewModel = eventConflictChatbotViewModel;
        this.viewManagerModel = viewManagerModel;
        this.editViewModel = editViewModel;
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
        viewManagerModel.setState(editViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }

}
