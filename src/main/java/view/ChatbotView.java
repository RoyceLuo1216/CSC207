package view;

import interface_adapter.chatbot_event_conflict.ChatbotState;
import interface_adapter.chatbot_event_conflict.ChatbotViewModel;
import interface_adapter.chatbot_event_conflict.EventConflictController;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * The View for when the user is using the chatbot.
 */
public class ChatbotView extends JPanel implements ActionListener, PropertyChangeListener {
    // Initialize controller
    private EventConflictController eventConflictController;

    // Setup Components
    private final String viewName = "chatbot";

    private final ChatbotViewModel chatbotViewModel;

    private static final JTextArea chatArea = new JTextArea(15, 30);
    private static final JTextField askField = new JTextField(45);
    final JLabel askLabel = new JLabel(ChatbotViewModel.ASK_LABEL);
    final JLabel askError = new JLabel();

    private static final JButton askButton = new JButton(ChatbotViewModel.ASK_BUTTON_LABEL);
    private static final JButton backButton = new JButton(ChatbotViewModel.BACK_BUTTON_LABEL);
    private static final JLabel backLabel = new JLabel();


    public ChatbotView(ChatbotViewModel chatbotViewModel) {
        this.chatbotViewModel = chatbotViewModel;
        chatbotViewModel.addPropertyChangeListener(this);

        // Create fixed frame (main)
        // JFrame chatbotFrame = new JFrame(ChatbotViewModel.TITLE_LABEL);
        // chatbotFrame.setLayout(new BorderLayout(10, 10));
        // chatbotFrame.setSize(500, 500);
        // chatbotFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        final JLabel title = new JLabel(ChatbotViewModel.TITLE_LABEL);

        // Main panel (Center)
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Chat Pane
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        chatArea.setMargin(new Insets(10, 10, 10, 10)); // Add margin inside the text box
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addChats(ChatbotViewModel.CHAT_INTRO); // Add chat intro to chatArea

        // Bottom panel (question bar and buttons)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new BoxLayout(bottomPanel, BoxLayout.Y_AXIS));

        // AskLabel Panel
        final JPanel askLabelPanel = new JPanel();
        askLabelPanel.setLayout(new BorderLayout());
        askLabelPanel.add(askLabel, BorderLayout.WEST);

        // AskField margins
        Border originalBorder = askField.getBorder();
        Border marginBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        askField.setBorder(BorderFactory.createCompoundBorder(originalBorder, marginBorder));

        // Error message panel
        JPanel errorPanel = new JPanel(new BorderLayout());

        // Fix the height of the error panel
        errorPanel.setPreferredSize(new Dimension(askField.getPreferredSize().width, 20));
        errorPanel.setMaximumSize(new Dimension(askField.getPreferredSize().width, 20));
        errorPanel.setMinimumSize(new Dimension(askField.getPreferredSize().width, 20));
        askError.setForeground(Color.RED);
        askError.setText("");
        errorPanel.add(askError, BorderLayout.CENTER);

        // Ask Panel
        JPanel askPanel = new JPanel();
        askPanel.setLayout(new BoxLayout(askPanel, BoxLayout.Y_AXIS));
        askPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        askPanel.add(askLabelPanel);
        askPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Spacing
        askPanel.add(askField);
        askPanel.add(Box.createRigidArea(new Dimension(0, 5)));  // Spacing
        askPanel.add(errorPanel);

        bottomPanel.add(askPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 0));
        buttonPanel.add(askButton);
        buttonPanel.add(backButton);
        buttonPanel.add(backLabel);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        // Add components to mainPanel
        mainPanel.add(chatScrollPane, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);


        // ActionListeners for entering into askField
        askField.addActionListener(evt -> handleAskAction());
        askButton.addActionListener(evt -> handleAskAction());

        // ActionListener for Back button to return to the main schedule page
        backButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {

                        backLabel.setText("Pressed!");
                        eventConflictController.backToMainView();
                    }
                }
        );

        addQuestionListener();

        this.setLayout(new BorderLayout(10, 10));
        this.setSize(500, 500);

        this.add(title);
        this.add(mainPanel, BorderLayout.CENTER);

    }

    /**
     * Shared function for submitting a question when ENTER or Ask button is pressed
     */
    private void handleAskAction() {
        final ChatbotState currentState = chatbotViewModel.getState();
        askError.setText("");   // Clear askError if any previous errors

        // Check if askField is not empty
        if (!askField.getText().trim().isEmpty()) {
            String question = currentState.getQuestion();

            chatArea.append(ChatbotViewModel.USER_NAME_LABEL + question + "\n\n");  // Append to chat area
            askField.setText("");                                                   // Clear input field

            eventConflictController.execute(question);

            if (currentState.getResponseError() != null) {
                addChat(currentState.getResponseError());
                currentState.setResponseError(null);
            } else {
                addChat(currentState.getResponse());
            }
        } else {
            // Set error message if the input is empty
            askError.setText(ChatbotViewModel.EMPTY_QUESTION_ERROR);
        }
    }

    /**
     * Print a single chatbot line in chatArea.
     * @param output into chatArea
     */
    private void addChat(String output) {
        chatArea.append(ChatbotViewModel.CHATBOT_NAME_LABEL + output + "\n\n");
        // auto scroll to the bottom of chat
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    /**
     * Print multiple chatbot lines in chatArea.
     * @param output into chatArea
     */
    private void addChats(String[] output) {
        for (String line: output){
            chatArea.append(ChatbotViewModel.CHATBOT_NAME_LABEL + line + "\n\n");
        }
        // auto scroll to the bottom of chat
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void addQuestionListener() {
        askField.getDocument().addDocumentListener(new DocumentListener() {

            private void documentListenerHelper() {
                final ChatbotState currentState = chatbotViewModel.getState();
                currentState.setQuestion(askField.getText());
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
        final ChatbotState state = (ChatbotState) evt.getNewValue();
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
        if (!askField.getText().trim().isEmpty()) { // Check if askField is not empty
            ask();
        } else {
            // TODO: set text to whatever error COHERE finds
            askError.setText(ChatbotViewModel.EMPTY_QUESTION_ERROR);
        }
    }

    private void ask() {
        String question = askField.getText();
        askError.setText("");   // Clear askError if any previous errors

        // eventConflictController.askQuestion(question);
        // eventConflictController.printQuestion();

        // Make question appear in chat area
        chatArea.append(ChatbotViewModel.USER_NAME_LABEL + question + "\n\n"); // Append to chat area
        askField.setText(""); // Clear input field

        // TODO: Connect to COHERE and display chat response in chat area after question
        // eventConflictController.execute(question);

        // String[] dummyChat = {eventConflictController.execute(question)};
        // addChat(dummyChat);
    }
}
