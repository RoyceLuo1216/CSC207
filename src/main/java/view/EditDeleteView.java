package view;

import interface_adapter.editdelete.EditDeleteController;
import interface_adapter.editdelete.EditDeletePresenter;
import interface_adapter.editdelete.EditDeleteViewModel;
import entities.EventEntity.Event;
import entities.EventEntity.FixedEvent;
import entities.ScheduleEntity.Schedule;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDateTime;


import interface_adapter.editdelete.EditDeleteState;

/**
 * The EditDeleteView renders the event editing interface and delegates logic to the ViewModel.
 */
public class EditDeleteView extends JPanel {
    private final EditDeleteViewModel viewModel;
    private final Runnable backToScheduleCallback;
    private final Runnable refreshScheduleCallback;

    public EditDeleteView(EditDeleteViewModel viewModel, Runnable backToScheduleCallback,
                          Runnable refreshScheduleCallback) {
        this.viewModel = viewModel;
        this.backToScheduleCallback = backToScheduleCallback;
        this.refreshScheduleCallback = refreshScheduleCallback;

        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("Edit Event", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titleLabel, BorderLayout.NORTH);

        // Fetch the current event state from the ViewModel
        EditDeleteState state = viewModel.getEditDeleteState();

        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(10, 10, 10, 10);

        // Input Fields
        JTextField nameField = new JTextField(state.getName(), 20);
        JTextField startTimeField = new JTextField(state.getStartTime(), 20);
        JTextField endTimeField = new JTextField(state.getEndTime(), 20);

        addField("Event Name:", nameField, formPanel, c, 0);
        addField("Start Time:", startTimeField, formPanel, c, 1);
        addField("End Time:", endTimeField, formPanel, c, 2);

        this.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        // Save Button Action
        saveButton.addActionListener(e -> {
            boolean success = viewModel.updateEvent(
                    nameField.getText(),
                    startTimeField.getText(),
                    endTimeField.getText()
            );
            if (success) {
                JOptionPane.showMessageDialog(this, "Event updated successfully!");
                refreshScheduleCallback.run(); // Refresh the schedule view
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update event. Check input format.");
            }
        });

        // Delete Button Action
        deleteButton.addActionListener(e -> {
            boolean success = viewModel.deleteEvent();
            if (success) {
                JOptionPane.showMessageDialog(this, "Event deleted successfully!");
                refreshScheduleCallback.run(); // Refresh the schedule view
                backToScheduleCallback.run();  // Navigate back to the schedule view
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete event.");
            }
        });



        // Back Button Action
        backButton.addActionListener(e -> backToScheduleCallback.run());

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void addField(String label, JTextField field, JPanel panel, GridBagConstraints c, int row) {
        c.gridx = 0;
        c.gridy = row;
        panel.add(new JLabel(label), c);

        c.gridx = 1;
        panel.add(field, c);
    }

    /**
     * Main method for testing EditDeleteView.
     */
    public static void main(String[] args) {
        // Create a fake schedule and event for testing
        Schedule schedule = new Schedule();
        Event testEvent = new FixedEvent(
                LocalDateTime.of(2024, 12, 25, 10, 0),
                LocalDateTime.of(2024, 12, 25, 12, 0),
                "Christmas Brunch",
                1
        );

        schedule.addEvent(testEvent);

        // Set up controller, presenter, and view model
        EditDeleteController controller = new EditDeleteController(schedule, testEvent);
        EditDeletePresenter presenter = new EditDeletePresenter();
        EditDeleteViewModel viewModel = new EditDeleteViewModel(controller, presenter);

        // Create the EditDeleteView
        EditDeleteView editDeleteView = new EditDeleteView(
                viewModel,
                () -> System.out.println("Back button pressed!"), // Dummy callback for "Back"
                () -> System.out.println("Schedule refreshed!")   // Dummy callback for "Refresh"
        );

        // Set up a JFrame to display the view
        JFrame frame = new JFrame("Edit/Delete Event");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(editDeleteView);
        frame.setSize(600, 400);
        frame.setVisible(true);
    }

}



