package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditDeleteView extends JPanel {
    private final String eventName;
    private final int startTime;
    private final int endTime;
    private final ActionListener backToScheduleListener;
    private final ActionListener deleteEventListener;

    public EditDeleteView(String eventName, int startTime, int endTime,
                          ActionListener backToScheduleListener,
                          ActionListener deleteEventListener) {
        this.eventName = eventName;
        this.startTime = startTime;
        this.endTime = endTime;
        this.backToScheduleListener = backToScheduleListener;
        this.deleteEventListener = deleteEventListener;

        setupUI();
    }

    private void setupUI() {
        this.setLayout(new BorderLayout());

        // Title section
        JLabel titleLabel = new JLabel("Edit Event: " + eventName, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        this.add(titleLabel, BorderLayout.NORTH);

        // Form for editing event details
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        // Event Name
        JLabel nameLabel = new JLabel("Event Name:");
        JTextField nameField = new JTextField(eventName, 20);
        c.gridx = 0;
        c.gridy = 0;
        formPanel.add(nameLabel, c);
        c.gridx = 1;
        formPanel.add(nameField, c);

        // Start Time
        JLabel startLabel = new JLabel("Start Time:");
        JTextField startField = new JTextField(Integer.toString(startTime), 10);
        c.gridx = 0;
        c.gridy = 1;
        formPanel.add(startLabel, c);
        c.gridx = 1;
        formPanel.add(startField, c);

        // End Time
        JLabel endLabel = new JLabel("End Time:");
        JTextField endField = new JTextField(Integer.toString(endTime), 10);
        c.gridx = 0;
        c.gridy = 2;
        formPanel.add(endLabel, c);
        c.gridx = 1;
        formPanel.add(endField, c);

        this.add(formPanel, BorderLayout.CENTER);

        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton saveButton = new JButton("Save");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");

        // Save Button Action
        saveButton.addActionListener(e -> {
            String updatedName = nameField.getText();
            int updatedStartTime = Integer.parseInt(startField.getText());
            int updatedEndTime = Integer.parseInt(endField.getText());

            // TODO: Save changes to the schedule data
            System.out.println("Updated Event: " + updatedName + " | " + updatedStartTime + " - " + updatedEndTime);
        });

        // Delete Button Action
        deleteButton.addActionListener(deleteEventListener);

        // Back Button Action
        backButton.addActionListener(backToScheduleListener);

        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);

        this.add(buttonPanel, BorderLayout.SOUTH);
    }
}
