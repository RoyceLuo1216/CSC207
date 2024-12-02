package app;

import data_access.InMemoryDataAccessObject;
import data_access.JSONScheduleDataAccessObject;
import entities.eventEntity.EventFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.addEvent.AddEventController;
import interface_adapter.addEvent.AddEventPresenter;
import interface_adapter.addEvent.AddEventViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationChatbotViewModel;
import interface_adapter.chatbotTimeEstimation.TimeEstimationController;
import interface_adapter.chatbotTimeEstimation.TimeEstimationPresenter;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotChatbotPresenter;
import interface_adapter.delete.DeleteEventViewModel;
import interface_adapter.edit.EditController;
import interface_adapter.edit.EditEventPresenter;
import interface_adapter.edit.EditViewModel;
import interface_adapter.repeat.RepeatController;
import interface_adapter.repeat.RepeatPresenter;
import interface_adapter.repeat.RepeatViewModel;
import interface_adapter.schedule.ScheduleController;
import interface_adapter.schedule.SchedulePresenter;
import interface_adapter.schedule.ScheduleViewModel;
import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.EventConflictInteractor;
import usecase.chatbot_event_conflict.EventConflictChatbotOutputBoundary;
import usecase.chatbot_time_estimation.TimeEstimationInputBoundary;
import usecase.chatbot_time_estimation.TimeEstimationInteractor;
import usecase.chatbot_time_estimation.TimeEstimationOutputBoundary;
import usecase.edit.EditEventInputBoundary;
import usecase.edit.EditEventInteractor;
import usecase.edit.EditEventOutputBoundary;
import usecase.event.AddEventInputBoundary;
import usecase.event.AddEventInteractor;
import usecase.event.AddEventOutputBoundary;
import usecase.repeat.RepeatInputBoundary;
import usecase.repeat.RepeatInteractor;
import usecase.repeat.RepeatOutputBoundary;
import usecase.schedule.ScheduleInputBoundary;
import usecase.schedule.ScheduleInteractor;
import usecase.schedule.ScheduleOutputBoundary;
import view.*;
import view.EventConflictChatbotView;

import javax.swing.*;
import java.awt.*;

import java.awt.event.WindowAdapter;

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

    private InMemoryDataAccessObject inMemoryDataAccessObjectDataObject;
    private JSONScheduleDataAccessObject jsonScheduleDataAccessObject = new JSONScheduleDataAccessObject();

    private EventConflictChatbotView eventConflictChatbotView;
    private EventConflictChatbotViewModel eventConflictChatbotViewModel;
    private TimeEstimationChatbotView timeEstimationChatbotView;
    private TimeEstimationChatbotViewModel timeEstimationChatbotViewModel;
    private DeleteEventView deleteEventView;
    private DeleteEventViewModel deleteEventViewModel;
    private AddEventView addEventView;
    private AddEventViewModel addEventViewModel;
    private ScheduleView scheduleView;
    private ScheduleViewModel scheduleViewModel;
    private EditView editView;
    private EditViewModel editViewModel;
    private RepeatView repeatView;
    private RepeatViewModel repeatViewModel;
//    private RepeatView
    // TODO: ADD EDIT (NOT MERGED YET)
    // TODO: FIND WHERE SCHEDULE VIEW MODEL IS

    public AppBuilder() {
        this.jsonScheduleDataAccessObject = new JSONScheduleDataAccessObject();
        this.inMemoryDataAccessObjectDataObject = jsonScheduleDataAccessObject.getSchedule();
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
        final EventConflictChatbotOutputBoundary eventConflictChatbotOutputBoundary =
                new EventConflictChatbotChatbotPresenter(
                viewManagerModel, scheduleViewModel, eventConflictChatbotViewModel);
        final EventConflictInputBoundary eventConflictInteractor = new EventConflictInteractor(
                inMemoryDataAccessObjectDataObject, eventConflictChatbotOutputBoundary, eventFactory);

        final EventConflictController controller = new EventConflictController(eventConflictInteractor);
        eventConflictChatbotView.setChatbotController(controller);
        return this;
    }

    public AppBuilder addScheduleView() {
        scheduleViewModel = new ScheduleViewModel();
        scheduleView = new ScheduleView(scheduleViewModel);
        cardPanel.add(scheduleView, scheduleView.getViewName());
        return this;
    }

    public AppBuilder addScheduleUseCase() {
        final ScheduleOutputBoundary scheduleOutputBoundary = new SchedulePresenter(
                scheduleViewModel,
                addEventViewModel,
                timeEstimationChatbotViewModel,
                eventConflictChatbotViewModel,
                viewManagerModel
        );
        final ScheduleInputBoundary scheduleInteractor = new ScheduleInteractor(
                inMemoryDataAccessObjectDataObject, scheduleOutputBoundary);

        final ScheduleController controller = new ScheduleController(scheduleInteractor);
        scheduleView.setScheduleController(controller);
        return this;
    }

    /**
     * Adds the Chatbot Event Conflict Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addTimeEstimationUseCase() {
        final TimeEstimationOutputBoundary timeEstimationOutputBoundary = new TimeEstimationPresenter(
                viewManagerModel, scheduleViewModel, timeEstimationChatbotViewModel);
        final TimeEstimationInputBoundary timeEstimationInteractor = new TimeEstimationInteractor(timeEstimationOutputBoundary);

        final TimeEstimationController controller = new TimeEstimationController(timeEstimationInteractor);
        timeEstimationChatbotView.setChatbotController(controller);
        return this;
    }

//    /**
// * Adds the DeleteEvent View to the application.
// * @return this builder
// */
//    public AppBuilder addDeleteEventView() {
//        deleteEventViewModel = new DeleteEventViewModel();
//        deleteEventView = new DeleteEventView(deleteEventViewModel);
//        cardPanel.add(deleteEventView, deleteEventView.getViewName());
//        return this;
//    }
//
///**
// * Adds the DeleteEvent Use Case to the application.
// * @return this builder
// */
//public AppBuilder addDeleteEventUseCase() {
//    final DeleteEventOutputBoundary deleteEventOutputBoundary = new DeleteEventPresenter(viewManagerModel,
//            deleteEventViewModel, loginViewModel);
//    final DeleteEventInputBoundary deleteEventInteractor = new DeleteEventInteractor(
//            userDataAccessObject, deleteEventOutputBoundary, userFactory);
//
//    final DeleteEventController controller = new DeleteEventController(deleteEventInteractor);
//    deleteEventView.setDeleteEventController(controller);
//    return this;
//}
    public AppBuilder addEventView() {
        addEventViewModel = new AddEventViewModel();
        addEventView = new AddEventView(addEventViewModel);
        cardPanel.add(addEventView, addEventView.getViewName());
        return this;
    }
//
    public AppBuilder addEventUseCase() {
        final AddEventOutputBoundary addEventOutputBoundary = new AddEventPresenter(addEventViewModel,
                viewManagerModel);
        final AddEventInputBoundary addEventInteractor = new AddEventInteractor(
                inMemoryDataAccessObjectDataObject, addEventOutputBoundary, eventFactory);

        final AddEventController controller = new AddEventController(addEventInteractor);
        addEventView.setAddEventController(controller);
        return this;
    }

    public AppBuilder addEditView() {
        editViewModel = new EditViewModel();
        editView = new EditView(editViewModel);
        cardPanel.add(editView, editView.getViewName());
        return this;
    }

    public AppBuilder addEditUseCase() {
        final EditEventOutputBoundary editOutputBoundary = new EditEventPresenter(
                viewManagerModel, editViewModel);
        final EditEventInputBoundary editInteractor = new EditEventInteractor(
                inMemoryDataAccessObjectDataObject, editOutputBoundary);

        final EditController controller = new EditController(editInteractor);
        editView.setEditController(controller);
        return this;
    }

    public AppBuilder addRepeatView() {
        repeatViewModel = new RepeatViewModel();
        repeatView = new RepeatView(repeatViewModel);
        cardPanel.add(repeatView, repeatView.getViewName());
        return this;
    }

    public AppBuilder addRepeatUseCase() {
        final RepeatOutputBoundary repeatOutputBoundary = new RepeatPresenter(repeatViewModel, viewManagerModel
                );
        final RepeatInputBoundary repeatInteractor = new RepeatInteractor(
                inMemoryDataAccessObjectDataObject, repeatOutputBoundary, eventFactory);

        final RepeatController controller = new RepeatController(repeatInteractor);
        repeatView.setRepeatController(controller);
        return this;
    }

    /**
     * Creates the JFrame for the application and initially sets the ChatbotView to be displayed.
     *
     * @return the application
     */
    public JFrame build() {
        final JFrame application = new JFrame("Weekly Planner");
//        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(scheduleView.getViewName());
        viewManagerModel.firePropertyChanged();

        application.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                jsonScheduleDataAccessObject.saveSchedule(inMemoryDataAccessObjectDataObject);
                System.exit(0);
            }
        });

        return application;
    }
}
