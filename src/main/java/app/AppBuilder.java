package app;

import data_access.InMemoryDataAccessObject;
import entities.EventEntity.EventFactory;
import interface_adapter.ViewManagerModel;
import interface_adapter.chatbot_event_conflict.ChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;
import interface_adapter.chatbot_event_conflict.EventConflictPresenter;
import usecase.chatbot_event_conflict.EventConflictInputBoundary;
import usecase.chatbot_event_conflict.EventConflictInteractor;
import usecase.chatbot_event_conflict.EventConflictOutputBoundary;
import view.ChatbotView;
import view.ViewManager;

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

    private ChatbotView chatbotView;
    private ChatbotViewModel chatbotViewModel;

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
