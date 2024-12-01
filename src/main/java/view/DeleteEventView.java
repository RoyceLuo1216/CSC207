package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventViewModel;

/**
 * The DeleteEventView renders the event deletion interface and delegates logic to the Controller and ViewModel.
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

    private final Runnable backToScheduleCallback;
    private final DeleteEventController controller;
    private JLabel messageLabel;
    private final Runnable refreshScheduleCallback;
    private final DeleteEventViewModel viewModel;

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
        final JLabel titleLabel = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        this.add(titleLabel, BorderLayout.NORTH);

        // Instruction Panel
        final JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        final JLabel instructionLabel = new JLabel(INSTRUCTION_TEXT);
        instructionPanel.add(instructionLabel);

        final JLabel eventNameLabel = new JLabel("Event: " + viewModel.getState().getEventName());
        eventNameLabel.setFont(new Font(FONT_NAME, Font.BOLD, EVENT_NAME_FONT_SIZE));
        instructionPanel.add(eventNameLabel);

        this.add(instructionPanel, BorderLayout.CENTER);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.ITALIC, MESSAGE_FONT_SIZE));
        messageLabel.setForeground(MESSAGE_FONT_COLOR);
        this.add(messageLabel, BorderLayout.SOUTH);

        // Button Panel
        final JPanel buttonPanel = new JPanel();
        final JButton deleteButton = new JButton(DELETE_BUTTON_TEXT);
        final JButton backButton = new JButton(CANCEL_BUTTON_TEXT);

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            final String eventName = viewModel.getState().getEventName();
            controller.execute(eventName);
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
        final String message = viewModel.getState().getMessage();
        messageLabel.setText(message);

        if (viewModel.getState().getMessage().contains("successfully")) {
            refreshScheduleCallback.run();
            backToScheduleCallback.run();
        }
    }

}
