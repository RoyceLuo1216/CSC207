package app;

import java.awt.*;

import javax.swing.*;

import data_access.InMemoryAddDataAccessObject;
import factory.EventFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chatbot_event_conflict.ChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;
import interface_adapter.eventAdd.EventAddController;
import interface_adapter.eventAdd.EventAddPresenter;
import interface_adapter.eventAdd.EventAddViewModel;
import interface_adapter.repeat.RepeatController;
import interface_adapter.repeat.RepeatPresenter;
import interface_adapter.repeat.RepeatViewModel;
import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.EventConflictInteractor;
import usecase.chatbot_event_conflict.EventConflictOutputBoundary;
import usecase.event.EventAddInputBoundary;
import usecase.event.EventAddInteractor;
import usecase.event.EventAddOutputBoundary;
import usecase.repeat.RepeatInputBoundary;
import usecase.repeat.RepeatInteractor;
import usecase.repeat.RepeatOutputBoundary;
import view.ChatbotView;
import view.EventAddView;
import view.RepeatView;
import view.ViewManager;

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

    private final InMemoryAddDataAccessObject inMemoryDataAccessObjectDataObject = new InMemoryAddDataAccessObject();

    private ChatbotView chatbotView;
    private ChatbotViewModel chatbotViewModel;

    private EventAddView eventAddView;
    private EventAddViewModel eventAddViewModel;

    private RepeatView repeatView;
    private RepeatViewModel repeatViewModel;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
    }

    /**
     * Adds the Chatbot View to the application.
     *
     * @return this builder
     */
    public AppBuilder addChatbotView() {
        chatbotViewModel = new ChatbotViewModel();
        chatbotView = new ChatbotView(chatbotViewModel);
        cardPanel.add(chatbotView, chatbotView.getViewName());
        return this;
    }

    /**
     * Adds the Event Add View to the application.
     *
     * @return this builder
     */
    public AppBuilder addEventView() {
        eventAddViewModel = new EventAddViewModel();
        eventAddView = new EventAddView(eventAddViewModel);
        cardPanel.add(eventAddView, eventAddView.getViewName());
        return this;
    }

    /**
     * Adds the Repeat Event Add View to the application.
     *
     * @return this builder
     */
    public AppBuilder addRepeatView() {
        repeatViewModel = new RepeatViewModel();
        repeatView = new RepeatView(repeatViewModel);
        cardPanel.add(repeatView, repeatView.getViewName());
        return this;
    }

    /**
     * Adds the Chatbot Event Conflict Use Case to the application.
     *
     * @return this builder
     */
    public AppBuilder addEventConflictUseCase() {
        final EventConflictOutputBoundary eventConflictOutputBoundary = new EventConflictPresenter(
                viewManagerModel, chatbotViewModel);
        final EventConflictInputBoundary eventConflictInteractor = new EventConflictInteractor(
                inMemoryDataAccessObjectDataObject, eventConflictOutputBoundary, eventFactory);

        final EventConflictController controller = new EventConflictController(eventConflictInteractor);
        chatbotView.setChatbotController(controller);
        return this;
    }

    /**
     * Adds the Add Event Use Case.
     *
     * @return this builder
     */
    public AppBuilder addEventUseCase() {
        final EventAddOutputBoundary eventAddOutputBoundary = new EventAddPresenter(
                eventAddViewModel, viewManagerModel);
        final EventAddInputBoundary eventInteractor = new EventAddInteractor(
                inMemoryDataAccessObjectDataObject, eventAddOutputBoundary);

        final EventAddController controller = new EventAddController(eventInteractor);
        eventAddView.setEventController(controller);
        return this;
    }

    /**
     * Adds the Add Repeat Event Use Case.
     *
     * @return this builder
     */
    public AppBuilder addRepeatUseCase() {
        final RepeatOutputBoundary repeatOutputBoundary = new RepeatPresenter(
                repeatViewModel, viewManagerModel);
        final RepeatInputBoundary repeatInteractor = new RepeatInteractor(
                inMemoryDataAccessObjectDataObject, repeatOutputBoundary);

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
        final JFrame application = new JFrame("Chatbot Example");
        application.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        application.add(cardPanel);

        viewManagerModel.setState(chatbotView.getViewName());
        viewManagerModel.firePropertyChanged();

        return application;
    }
}
