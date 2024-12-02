package view;

import java.awt.*;

import javax.swing.*;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventViewModel;

/**
 * Delete Event View.
 */
public class DeleteEventView extends JDialog {
    private static final String CANCEL_BUTTON_TEXT = "Cancel";
    private static final String DELETE_BUTTON_TEXT = "Delete";
    private static final int EVENT_NAME_FONT_SIZE = 14;
    private static final String FONT_NAME = "Arial";
    private static final String INSTRUCTION_TEXT = "Are you sure you want to delete this event?";
    private static final Color MESSAGE_FONT_COLOR = Color.RED;
    private static final int MESSAGE_FONT_SIZE = 12;
    private static final String TITLE_TEXT = "Delete Event";
    private static final int TITLE_FONT_SIZE = 18;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 250;

    private final DeleteEventViewModel viewModel;
    private JLabel messageLabel;
    private DeleteEventController controller;

    public DeleteEventView(DeleteEventViewModel viewModel, JFrame parentFrame) {
        super(parentFrame, TITLE_TEXT, true);
        this.viewModel = viewModel;

        setupUi();
        setupListeners();
        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(parentFrame);
    }

    public void setController(DeleteEventController controller) {
        this.controller = controller;
    }

    private void setupUi() {
        final JPanel contentPanel = new JPanel(new BorderLayout());
        this.setContentPane(contentPanel);

        // Title
        final JLabel titleLabel = new JLabel(TITLE_TEXT, SwingConstants.CENTER);
        titleLabel.setFont(new Font(FONT_NAME, Font.BOLD, TITLE_FONT_SIZE));
        contentPanel.add(titleLabel, BorderLayout.NORTH);

        // Instruction Panel
        final JPanel instructionPanel = new JPanel();
        instructionPanel.setLayout(new BoxLayout(instructionPanel, BoxLayout.Y_AXIS));
        final JLabel instructionLabel = new JLabel(INSTRUCTION_TEXT);
        instructionLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.add(instructionLabel);

        final JLabel eventNameLabel = new JLabel("Event: " + viewModel.getState().getEventName());
        eventNameLabel.setFont(new Font(FONT_NAME, Font.BOLD, EVENT_NAME_FONT_SIZE));
        eventNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        instructionPanel.add(eventNameLabel);

        contentPanel.add(instructionPanel, BorderLayout.CENTER);

        // Message Label
        messageLabel = new JLabel("", SwingConstants.CENTER);
        messageLabel.setFont(new Font(FONT_NAME, Font.ITALIC, MESSAGE_FONT_SIZE));
        messageLabel.setForeground(MESSAGE_FONT_COLOR);
        contentPanel.add(messageLabel, BorderLayout.SOUTH);

        // Button Panel
        final JPanel buttonPanel = new JPanel();
        final JButton deleteButton = new JButton(DELETE_BUTTON_TEXT);
        final JButton backButton = new JButton(CANCEL_BUTTON_TEXT);

        deleteButton.addActionListener(e -> {
            if (controller != null) {
                controller.execute(viewModel.getState().getEventName());
            }
        });

        backButton.addActionListener(e -> this.dispose());

        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        contentPanel.add(buttonPanel, BorderLayout.SOUTH);
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
