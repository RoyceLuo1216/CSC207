package view;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventState;

import javax.swing.*;
import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Represents a page for confirming event deletion.
 */
public class DeleteEventView extends JPanel implements PropertyChangeListener {
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
        if (controller != null) {
            controller.fetchEventDetails();
        }
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
        JLabel titleLabel = new JLabel("Delete Event", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout());
        JLabel instructionLabel = new JLabel("Are you sure you want to delete this event?");
        instructionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        centerPanel.add(instructionLabel, BorderLayout.NORTH);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        centerPanel.add(messageLabel, BorderLayout.CENTER);

        this.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        deleteButton.addActionListener(e -> {
            if (controller != null) {
                controller.deleteEvent();
            }
        });

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> {
            if (controller != null) {
                controller.editView();
            }
        });

        buttonPanel.add(deleteButton);
        buttonPanel.add(cancelButton);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("eventDetails".equals(evt.getPropertyName())) {
            DeleteEventState state = (DeleteEventState) evt.getNewValue();
            messageLabel.setText("Event: " + state.getEventName());
        }
        else if ("deleteSuccess".equals(evt.getPropertyName()) || "deleteFail".equals(evt.getPropertyName())) {
            DeleteEventState state = (DeleteEventState) evt.getNewValue();
            JOptionPane.showMessageDialog(this, state.getMessage());
        }
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

    /**
     * Main method for testing DeleteEventView independently.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame("Delete Event Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);

        // Simulate DeleteEventController
        DeleteEventController dummyController = new DeleteEventController(null) {

            @Override
            public void execute() {
                System.out.println("Event Deleted:");
            }

            @Override
            public void scheduleView() {
                System.out.println("Switched to schedule view.");
            }

            @Override
            public void editView() {
                System.out.println("Switched to edit view.");
            }
        };

        DeleteEventView deleteView = new DeleteEventView();
        deleteView.setController(dummyController);
        deleteView.setEventDetails("Test Event");

        frame.add(deleteView);
        frame.setVisible(true);
    }
}
