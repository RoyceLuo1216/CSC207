package view;

import usecase.chatbot_event_conflict.EventConflictInteractor;

import javax.swing.*;
import java.awt.*;

/**
 * The View for when the user is using the chatbot.
 */
public class ChatbotView extends JPanel {
    // Initialize controller
    private EventConflictController eventConflictController = new EventConflictController();;

    // Setup Components
    private static final String[] chatIntro =
            {
                "Welcome to the schedule chatbot! Ask me about your schedule.",
                "I can help you schedule events," +
                    " estimate time for a task, and inform you about scheduling conflicts!",
                "Press ENTER or the 'Ask' button to ask a question. :)"
            };
    private static final JLabel askLabel = new JLabel("Ask me about your schedule:");
    private static final JTextField askField = new JTextField(30);
    private static final JLabel askError = new JLabel();
    private static final JTextArea chatArea = new JTextArea(15, 30);
    private static final JButton backButton = new JButton("Back");
    private static final JLabel backLabel = new JLabel();

    public ChatbotView() {
        // Create fixed frame (main)
        JFrame chatbotFrame = new JFrame("Schedule Chatbot Page");
        chatbotFrame.setLayout(new BorderLayout(10, 10));
        chatbotFrame.setSize(500, 500);
        chatbotFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // CREATE PANELS
        // Main panel (Center)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        // Chat Panel
        chatArea.setLineWrap(true);
        chatArea.setWrapStyleWord(true);
        chatArea.setEditable(false);
        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        chatScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        addChat(chatIntro); // Add chat intro to chatArea

        // Ask Panel
        JPanel askPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        askPanel.add(askLabel);
        askPanel.add(askField);
        askPanel.add(askError);
        askError.setForeground(Color.red);  // Set error text to red

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton askButton = new JButton("Ask!");
        buttonPanel.add(askButton);
        buttonPanel.add(backButton);
        buttonPanel.add(backLabel);

        // Add components to mainPanel
        mainPanel.add(chatScrollPane);
        mainPanel.add(askPanel);
        mainPanel.add(buttonPanel);

        // Add mainPanel to frame
        chatbotFrame.add(mainPanel, BorderLayout.CENTER);

        // Display the frame
        chatbotFrame.setVisible(true);

        // ActionListeners for entering into askField
        askField.addActionListener(evt -> checkAskField());
        askButton.addActionListener(evt -> checkAskField());

        // ActionListener for Back button
        backButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(backButton)) {
                        back();
                    }
                }
        );
    }

    private void checkAskField() {
        if (!askField.getText().trim().isEmpty()) { // Check if askField is not empty
            ask();
        } else {
            // TODO: set text to whatever error COHERE finds
            askError.setText("[Please type a question.]");
        }
    }

    // Print chatbot lines in chatArea
    private void addChat(String[] output) {
        for (String line: output){
            chatArea.append("Chatbot: " + line + "\n");
        }
        // auto scroll to the bottom of chat
        chatArea.setCaretPosition(chatArea.getDocument().getLength());
    }

    private void ask() {
        String question = askField.getText();
        askError.setText("");   // Clear askError if any previous errors

        // eventConflictController.askQuestion(question);
        // eventConflictController.printQuestion();

        // Make question appear in chat area
        chatArea.append("You: " + question + "\n"); // Append to chat area
        askField.setText(""); // Clear input field

        // TODO: Connect to COHERE and display chat response in chat area after question
        // eventConflictController.execute(question);

        String[] dummyChat = {eventConflictController.execute(question)};
        addChat(dummyChat);
    }

    // Return to main calendar page
    private void back() {
        // TODO: implement return to main calendar page
        backLabel.setText("Pressed!");
        eventConflictController.backToMainView();
    }

    public void setChatbotController(EventConflictController controller) {
        this.eventConflictController = controller;
    }
}
