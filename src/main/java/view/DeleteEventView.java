package view;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventViewModel;

import javax.swing.*;
import java.awt.*;

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

    private final DeleteEventViewModel viewModel;
    private JLabel messageLabel;
    private DeleteEventController controller;

    public DeleteEventView(DeleteEventViewModel viewModel) {
        this.viewModel = viewModel;
        setupUi();
        setupListeners();
    }

    public void setController(DeleteEventController controller) {
        this.controller = controller;
    }

    private void setupUi() {
        this.setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        JLabel instructionLabel = new JLabel(INSTRUCTION_TEXT);
        instructionPanel.add(instructionLabel);

        JLabel eventNameLabel = new JLabel("Event: " + viewModel.getState().getEventName());
        eventNameLabel.setFont(new Font(FONT_NAME, Font.BOLD, EVENT_NAME_FONT_SIZE));
        instructionPanel.add(eventNameLabel);

        this.add(instructionPanel, BorderLayout.CENTER);

        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.ITALIC, MESSAGE_FONT_SIZE));
        messageLabel.setForeground(MESSAGE_FONT_COLOR);
        this.add(messageLabel, BorderLayout.SOUTH);

        JPanel buttonPanel = new JPanel();
        JButton deleteButton = new JButton(DELETE_BUTTON_TEXT);
        JButton backButton = new JButton(CANCEL_BUTTON_TEXT);

        deleteButton.addActionListener(e -> {
            if (controller != null) {
                controller.execute(viewModel.getState().getEventName());
            }
        });

        backButton.addActionListener(e -> System.out.println("Back to schedule triggered by presenter."));

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
    }

    public String getViewName() {
        return "delete";
    }
}
