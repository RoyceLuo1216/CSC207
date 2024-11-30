package view;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * The DeleteEventView renders the event deletion interface and delegates logic to the Controller and ViewModel.
 */
public class DeleteEventView extends JPanel {
    private final DeleteEventViewModel viewModel;
    private final DeleteEventController controller;
    private final Runnable backToScheduleCallback;
    private final Runnable refreshScheduleCallback;

    private JLabel messageLabel;

    public DeleteEventView(DeleteEventController controller, DeleteEventViewModel viewModel,
                           Runnable backToScheduleCallback, Runnable refreshScheduleCallback) {
        this.controller = controller;
        this.viewModel = viewModel;
        this.backToScheduleCallback = backToScheduleCallback;
        this.refreshScheduleCallback = refreshScheduleCallback;

        setupUI();
        setupListeners();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Delete Event", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titleLabel, BorderLayout.NORTH);

        // Instruction Panel
        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        JLabel instructionLabel = new JLabel("Are you sure you want to delete this event?");
        instructionPanel.add(instructionLabel);

        JLabel eventNameLabel = new JLabel("Event: " + viewModel.getState().getEventName());
        eventNameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        instructionPanel.add(eventNameLabel);

        this.add(instructionPanel, BorderLayout.CENTER);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        messageLabel.setForeground(Color.RED);
        this.add(messageLabel, BorderLayout.SOUTH);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Cancel");

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            String eventName = viewModel.getState().getEventName();
            controller.execute(eventName); // Trigger the deletion process
        });

        // Back Button Action
        backButton.addActionListener(e -> backToScheduleCallback.run());

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void setupListeners() {
        viewModel.addPropertyChangeListener(evt -> {
            if ("state".equals(evt.getPropertyName())) {
                updateView();
            }
        });
    }

    private void updateView() {
        String message = viewModel.getState().getMessage();
        messageLabel.setText(message);

        if (viewModel.getState().getMessage().contains("successfully")) {
            refreshScheduleCallback.run(); // Refresh the schedule on success
            backToScheduleCallback.run(); // Navigate back to the schedule view
        }
    }

    /**
     * Main method for testing DeleteEventView.
     */
    public static void main(String[] args) {
        // Mock ViewModel and Controller
        DeleteEventViewModel viewModel = new DeleteEventViewModel("delete");
        viewModel.getState().setEventName("Christmas Brunch");

        DeleteEventController controller = new DeleteEventController(inputData -> {
            if (inputData.getEventName().equals("Christmas Brunch")) {
                viewModel.getState().setMessage("Event \"Christmas Brunch\" deleted successfully.");
            } else {
                viewModel.getState().setMessage("Failed to delete event.");
            }
            viewModel.firePropertyChanged("state");
        });

        DeleteEventView deleteEventView = new DeleteEventView(
                controller,
                viewModel,
                () -> System.out.println("Back to schedule"), // Dummy callback
                () -> System.out.println("Schedule refreshed") // Dummy callback
        );

        // Set up a JFrame for testing
        JFrame frame = new JFrame("Delete Event");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(deleteEventView);
        frame.setSize(400, 200);
        frame.setVisible(true);
    }
}
