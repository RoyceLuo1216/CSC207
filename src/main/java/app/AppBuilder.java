package app;

import data_access.InMemoryDataAccessObject;
import factory.EventFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationChatbotViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationController;
import interface_adapter.chatbotTimeEstimation.TimeEstimationPresenter;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import interface_adapter.edit.EditViewModel;
import interface_adapter.repeat.RepeatViewModel;
import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.EventConflictInteractor;
import usecase.chatbot_event_conflict.EventConflictOutputBoundary;
import usecase.chatbot_time_estimation.TimeEstimationInputBoundary;
import usecase.chatbot_time_estimation.TimeEstimationInteractor;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;
import view.*;
import view.EventConflictChatbotView;

import javax.swing.*;
import java.awt.*;

/**
 * The AppBuilder class is responsible for putting together the pieces of
 * our CA architecture; piece by piece.
 * <p/>
 * This is done by adding each View and then adding related Use Cases.
 */
// Checkstyle note: you can ignore the "Class Data Abstraction Coupling"
//                  and the "Class Fan-Out Complexity" issues.
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final EventFactory eventFactory = new EventFactory();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();
    private final ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final InMemoryDataAccessObject inMemoryDataAccessObjectDataObject = new InMemoryDataAccessObject();

    private EventConflictChatbotView eventConflictChatbotView;
    private EventConflictChatbotViewModel eventConflictChatbotViewModel;
    private TimeEstimationChatbotView timeEstimationChatbotView;
    private TimeEstimationChatbotViewModel timeEstimationChatbotViewModel;
    private DeleteEventView deleteEventView;
    private DeleteEventViewModel deleteEventViewModel;
    private EventView eventView;
    // TODO: FIND WHERE EVENT VIEW MODEL IS
    private ScheduleView scheduleView;
//    private EditView
    private EditViewModel editViewModel;
    private RepeatViewModel repeatViewModel;
//    private RepeatView
    //private ScheduleViewModel scheduleViewModel;
    // TODO: ADD EDIT (NOT MERGED YET)

    // TODO: FIND WHERE SCHEDULE VIEW MODEL IS

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Event Conflict Chatbot View to the application.
     *
     * @return this builder
     */
    public AppBuilder addEventConflictChatbotView() {
        eventConflictChatbotViewModel = new EventConflictChatbotViewModel();
        eventConflictChatbotView = new EventConflictChatbotView(eventConflictChatbotViewModel);
        cardPanel.add(eventConflictChatbotView, eventConflictChatbotView.getViewName());
        return this;
    }

    /**
     * Adds the Event Conflict Chatbot View to the application.
     *
     * @return this builder
     */
//    public AppBuilder editEventView() {
//        editViewModel = new EditViewModel();
//        editView = new EditEventView(editViewModel);
//        cardPanel.add(eventConflictChatbotView, eventConflictChatbotView.getViewName());
//        return this;
//    }

    /**
     * Adds the Delete View to the application.
     *
     * @return this builder
     */
//    public AppBuilder addDeleteEventView() {
//        // TODO: FIND OUT WHAT THE VIEWNAME SHOULD BE HERE
//        deleteEventViewModel = new DeleteEventViewModel("delete");
//        deleteEventView = new TimeEstimationChatbotView(deleteEventViewModel);
//        // TODO: FIND APPROPRIATE REPLACEMENT FOR DELETE ( deleteEventView.getViewName())
//        cardPanel.add(deleteEventView, "delete");
//        return this;
//    }

    /**
     * Adds the Time Estimation Chatbot View to the application.
     *
     * @return this builder
     */
    public AppBuilder addTimeEstimationChatbotView() {
        timeEstimationChatbotViewModel = new TimeEstimationChatbotViewModel();
        timeEstimationChatbotView = new TimeEstimationChatbotView(timeEstimationChatbotViewModel);
        cardPanel.add(timeEstimationChatbotView, timeEstimationChatbotView.getViewName());
        return this;
    }

    /**
     * Adds the Chatbot Event Conflict Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addEventConflictUseCase() {
        final EventConflictOutputBoundary eventConflictOutputBoundary = new EventConflictPresenter(
                viewManagerModel, eventConflictChatbotViewModel);
        final EventConflictInputBoundary eventConflictInteractor = new EventConflictInteractor(
                inMemoryDataAccessObjectDataObject, eventConflictOutputBoundary, eventFactory);

        final EventConflictController controller = new EventConflictController(eventConflictInteractor);
        eventConflictChatbotView.setChatbotController(controller);
        return this;
    }

    /**
     * Adds the Chatbot Event Conflict Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addTimeEstimationUseCase() {
        final TimeEstimationOutputBoundary timeEstimationOutputBoundary = new TimeEstimationPresenter(
                viewManagerModel, timeEstimationChatbotViewModel);
        final TimeEstimationInputBoundary timeEstimationInteractor = new TimeEstimationInteractor(timeEstimationOutputBoundary);

        final TimeEstimationController controller = new TimeEstimationController(timeEstimationInteractor);
        timeEstimationChatbotView.setChatbotController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the ChatbotView to be displayed.
     *
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Chatbot Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(eventConflictChatbotView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
