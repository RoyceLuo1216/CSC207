package interface_adapter.schedule;

import interface_adapter.ViewManagerModel;
import interface_adapter.addEvent.AddEventViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import usecase.schedule.ScheduleOutputBoundary;
import usecase.schedule.ScheduleOutputData;
import view.AddEventView;
import view.EventConflictChatbotView;
import view.TimeEstimationChatbotView;

/**
 * Presenter for the Schedule Use Case.
 */
public class SchedulePresenter implements ScheduleOutputBoundary {

    private final ScheduleViewModel viewModel;
    private final AddEventViewModel addEventViewModel;
    private final TimeEstimationChatbotViewModel timeEstimationChatbotViewModel;
    private final EventConflictChatbotViewModel eventConflictChatbotViewModel;
    private final ViewManagerModel viewManagerModel;

    /**
     * Initializes the SchedulePresenter with the associated ViewModel.
     *
     * @param viewModel the ViewModel for the schedule
     * @param addEventViewModel the ViewModel for the add event use case
     * @param timeEstimationChatbotViewModel the ViewModel for the time estimation chatbot
     * @param eventConflictChatbotViewModel the ViewModel for the event conflict chatbot
     * @param viewManagerModel the viewManagerModel
     */
    public SchedulePresenter(ScheduleViewModel viewModel, AddEventViewModel addEventViewModel,
                             TimeEstimationChatbotViewModel timeEstimationChatbotViewModel,
                             EventConflictChatbotViewModel eventConflictChatbotViewModel,
                             ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.addEventViewModel = addEventViewModel;
        this.timeEstimationChatbotViewModel = timeEstimationChatbotViewModel;
        this.eventConflictChatbotViewModel = eventConflictChatbotViewModel;
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

        // Clear previous state
        state.getEventButtonMap().clear();

        // Populate the state with new event data
        outputData.getEventNames().forEach(eventName -> {
            String buttonId = "btn_" + eventName; // Generate unique button ID
            state.addEventButton(buttonId, eventName);
        });

        // Notify the ViewModel of the updated state
        viewModel.setState(state);
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

}
