package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import interface_adapter.chatbot_event_conflict.EventConflictChatbotState;
import interface_adapter.chatbot_event_conflict.EventConflictChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;

/**
 * The View for when the user is using the chatbot.
 */
public class EventConflictChatbotView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final int DIMENSION_10 = 10;
    private static final int DIMENSION_20 = 20;
    private static final int DIMENSION_5 = 5;
    private static final int DIMENSION_500 = 500;

    private static final JTextArea CHAT_AREA = new JTextArea(15, 30);
    private static final JTextField ASK_FIELD = new JTextField(45);
    private static final JButton ASK_BUTTON = new JButton(EventConflictChatbotViewModel.ASK_BUTTON_LABEL);
    private static final JButton BACK_BUTTON = new JButton(EventConflictChatbotViewModel.BACK_BUTTON_LABEL);
    private static final JLabel BACK_LABEL = new JLabel();
    final JLabel askLabel = new JLabel(EventConflictChatbotViewModel.ASK_LABEL);
    final JLabel askError = new JLabel();
    // Setup Components
    private final String viewName = "eventConflictChatbot";
    private final EventConflictChatbotViewModel chatbotViewModel;
    // Initialize controller
    private EventConflictController eventConflictController;

    public EventConflictChatbotView(EventConflictChatbotViewModel chatbotViewModel) {
        this.chatbotViewModel = chatbotViewModel;

        // Create the fixed frame (main)
//        final JFrame chatbotFrame = new JFrame(EventConflictChatbotViewModel.TITLE_LABEL);
//        chatbotFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        chatbotFrame.setSize(DIMENSION_500, DIMENSION_500);
//        chatbotFrame.setLayout(new BoxLayout(chatbotFrame.getContentPane(), BoxLayout.Y_AXIS));

        chatbotViewModel.addPropertyChangeListener(this);
        final JLabel title = new JLabel(EventConflictChatbotViewModel.TITLE_LABEL);

        // Main panel (Center)
        final JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(DIMENSION_10, DIMENSION_10, DIMENSION_10, DIMENSION_10));

        // Chat Pane
        CHAT_AREA.setLineWrap(true);
        CHAT_AREA.setWrapStyleWord(true);
        CHAT_AREA.setEditable(false);
        // Add margin inside the text box
        CHAT_AREA.setMargin(new Insets(DIMENSION_10, DIMENSION_10, DIMENSION_10, DIMENSION_10));
        final JScrollPane chatScrollPane = new JScrollPane(CHAT_AREA);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        // Add chat intro to chatArea
        addChats(EventConflictChatbotViewModel.CHAT_INTRO);

        // Bottom panel (question bar and buttons)
        final JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // AskLabel Panel
        final JPanel askLabelPanel = new JPanel();
        askLabelPanel.setLayout(new BorderLayout());
        askLabelPanel.add(askLabel, BorderLayout.WEST);

        // AskField margins
        final Border originalBorder = ASK_FIELD.getBorder();
        final Border marginBorder = BorderFactory.createEmptyBorder(DIMENSION_5, DIMENSION_5, DIMENSION_5,
                DIMENSION_5);
        ASK_FIELD.setBorder(BorderFactory.createCompoundBorder(originalBorder, marginBorder));

        // Error message panel
        final JPanel errorPanel = new JPanel(new BorderLayout());

        // Fix the height of the error panel
        errorPanel.setPreferredSize(new Dimension(ASK_FIELD.getPreferredSize().width, DIMENSION_20));
        errorPanel.setMaximumSize(new Dimension(ASK_FIELD.getPreferredSize().width, DIMENSION_20));
        errorPanel.setMinimumSize(new Dimension(ASK_FIELD.getPreferredSize().width, DIMENSION_20));
        askError.setForeground(Color.RED);
        askError.setText("");
        errorPanel.add(askError, BorderLayout.CENTER);

        // Ask Panel
        final JPanel askPanel = new JPanel();
        askPanel.setLayout(new BoxLayout(askPanel, BoxLayout.Y_AXIS));
        askPanel.setBorder(BorderFactory.createEmptyBorder(DIMENSION_10, 0, 0, 0));

        askPanel.add(askLabelPanel);
        // Spacing
        askPanel.add(Box.createRigidArea(new Dimension(0, DIMENSION_10)));
        askPanel.add(ASK_FIELD);
        askPanel.add(Box.createRigidArea(new Dimension(0, DIMENSION_5)));
        askPanel.add(errorPanel);

        bottomPanel.add(askPanel, BorderLayout.CENTER);

        // Button Panel
        final JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, DIMENSION_5, 0));
        buttonPanel.add(ASK_BUTTON);
        buttonPanel.add(BACK_BUTTON);
        buttonPanel.add(BACK_LABEL);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to mainPanel
        mainPanel.add(chatScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        // ActionListeners for entering into askField
        ASK_FIELD.addActionListener(evt -> handleAskAction());
        ASK_BUTTON.addActionListener(evt -> handleAskAction());

        // ActionListener for Back button to return to the main schedule page
        BACK_BUTTON.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {

                        // BACK_LABEL.setText("Pressed!");
                        eventConflictController.backToScheduleView();
                    }
                }
        );

        addQuestionListener();
        this.setLayout(new BorderLayout(DIMENSION_10, DIMENSION_10));
        this.setSize(DIMENSION_500, DIMENSION_500);

        this.add(title);
        this.add(mainPanel, BorderLayout.CENTER);

//        chatbotFrame.add(mainPanel, BorderLayout.CENTER);
//        chatbotFrame.setVisible(true);
    }

    /**
     * Shared function for submitting a question when ENTER or Ask button is pressed.
     */
    private void handleAskAction() {
        final EventConflictChatbotState currentState = chatbotViewModel.getState();
        // Clear askError if any previous errors
        askError.setText("");

        // Check if askField is not empty
        if (!ASK_FIELD.getText().trim().isEmpty()) {
            final String question = currentState.getQuestion();

            // Append to chat area
            CHAT_AREA.append(EventConflictChatbotViewModel.USER_NAME_LABEL + question + "\n\n");
            // Clear input field
            ASK_FIELD.setText("");

            eventConflictController.execute(question);

            if (currentState.getResponseError() != null) {
                addChat(currentState.getResponseError());
                currentState.setResponseError(null);
            }
            else {
                addChat(currentState.getResponse());
            }
        }
        else {
            // Set error message if the input is empty
            askError.setText(EventConflictChatbotViewModel.EMPTY_QUESTION_ERROR);
        }
    }

    /**
     * Print a single chatbot line in chatArea.
     *
     * @param output into chatArea
     */
    private void addChat(String output) {
        CHAT_AREA.append(EventConflictChatbotViewModel.CHATBOT_NAME_LABEL + output + "\n\n");
        // auto scroll to the bottom of chat
        CHAT_AREA.setCaretPosition(CHAT_AREA.getDocument().getLength());
    }

    /**
     * Print multiple chatbot lines in chatArea.
     *
     * @param output into chatArea
     */
    private void addChats(String[] output) {
        for (String line : output) {
            CHAT_AREA.append(EventConflictChatbotViewModel.CHATBOT_NAME_LABEL + line + "\n\n");
        }
        // auto scroll to the bottom of chat
        CHAT_AREA.setCaretPosition(CHAT_AREA.getDocument().getLength());
    }

    private void addQuestionListener() {
        ASK_FIELD.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final EventConflictChatbotState currentState = chatbotViewModel.getState();
                currentState.setQuestion(ASK_FIELD.getText());
                chatbotViewModel.setState(currentState);
            }

            @Override
            public void insertUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                documentListenerHelper();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                documentListenerHelper();
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final EventConflictChatbotState state = (EventConflictChatbotState) evt.getNewValue();
        if (state.getQuestionError() != null) {
            JOptionPane.showMessageDialog(this, state.getQuestionError());
        }
    }

    public String getViewName() {
        return viewName;
    }

    public void setChatbotController(EventConflictController controller) {
        this.eventConflictController = controller;
    }

    //
    //
    // Additional functions from non CA implementation
    //
    //
    private void checkAskField() {
        // Check if askField is not empty
        if (!ASK_FIELD.getText().trim().isEmpty()) {
            ask();
        }
        else {
            // TODO: set text to whatever error COHERE finds
            askError.setText(EventConflictChatbotViewModel.EMPTY_QUESTION_ERROR);
        }
    }

    private void ask() {
        final String question = ASK_FIELD.getText();
        // Clear askError if any previous errors
        askError.setText("");

        // eventConflictController.askQuestion(question);
        // eventConflictController.printQuestion();

        // Make question appear in chat area
        // Append to chat area
        CHAT_AREA.append(EventConflictChatbotViewModel.USER_NAME_LABEL + question + "\n\n");
        // Clear input field
        ASK_FIELD.setText("");

        // TODO: Connect to COHERE and display chat response in chat area after question
        // eventConflictController.execute(question);

        // String[] dummyChat = {eventConflictController.execute(question)};
        // addChat(dummyChat);
    }
}
