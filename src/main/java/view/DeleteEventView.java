package view;

import interface_adapter.delete.DeleteEventController;

import javax.swing.*;
import java.awt.*;

/**
 * Represents a page for confirming event deletion.
 */
public class DeleteEventView extends JPanel {
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String DELETE_BUTTON_TEXT = "Delete";
    private static final int EVENT_NAME_FONT_SIZE = 14;
    private static final String FONT_NAME = "Arial";
    private static final String INSTRUCTION_TEXT = "Are you sure you want to delete this event?";
    private static final Color MESSAGE_FONT_COLOR = Color.RED;
    private static final int MESSAGE_FONT_SIZE = 12;
    private static final String TITLE_TEXT = "Delete Event";
    private static final int TITLE_FONT_SIZE = 18;

    private JLabel messageLabel;
    private DeleteEventController controller;
    private String eventName;

    /**
     * Constructs the delete confirmation page.
     */
    public DeleteEventView() {
        setupUi();
    }

    /**
     * Sets the controller for handling deletion logic.
     *
     * @param controller The controller for deletion logic.
     */
    public void setController(DeleteEventController controller) {
        this.controller = controller;
    }

    /**
     * Dynamically sets the event name for deletion and updates the UI.
     *
     * @param eventName The name of the event to be deleted.
     */
    public void setEventDetails(String eventName) {
        this.eventName = eventName;
        updateView();
    }

    private void setupUi() {
        this.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        this.add(titleLabel, BorderLayout.NORTH);

        // Instruction Panel
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        JLabel instructionLabel = new JLabel(INSTRUCTION_TEXT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.add(instructionLabel);

        JLabel eventNameLabel = new JLabel();
        eventNameLabel.setFont(new Font(FONT_NAME, Font.BOLD, EVENT_NAME_FONT_SIZE));
        eventNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.add(eventNameLabel);

        this.add(instructionPanel, BorderLayout.CENTER);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.ITALIC, MESSAGE_FONT_SIZE));
        messageLabel.setForeground(MESSAGE_FONT_COLOR);
        this.add(messageLabel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton(DELETE_BUTTON_TEXT);
        JButton backButton = new JButton(CANCEL_BUTTON_TEXT);

        deleteButton.addActionListener(e -> {
            if (controller != null && eventName != null) {
                controller.execute(eventName);
            } else {
                JOptionPane.showMessageDialog(
                        this,
                        "Unable to delete event. Ensure the controller and event name are set.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                );
            }
        });

        backButton.addActionListener(e -> {
            CardLayout cl = (CardLayout) this.getParent().getLayout();
            cl.show(this.getParent(), "edit");
        });

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void updateView() {
        if (eventName != null) {
            messageLabel.setText("Event: " + eventName);
        } else {
            messageLabel.setText("No event selected.");
        }
    }

    /**
     * Returns the unique name of this view.
     *
     * @return The name of this view.
     */
    public String getViewName() {
        return "delete";
    }
}

