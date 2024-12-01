package view;

import static interface_adapter.repeat.RepeatViewModel.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import interface_adapter.repeat.RepeatController;
import interface_adapter.repeat.RepeatState;
import interface_adapter.repeat.RepeatViewModel;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class RepeatView extends JPanel implements ActionListener, PropertyChangeListener {
    // Initialise the controller

    private static final int DIMENSION_500 = 500;

    private final String viewName = "repeat";
    private final RepeatViewModel repeatViewModel;
    private RepeatController repeatController;

    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(EVENT_TYPES);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(DAYS_OF_WEEK);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(DAYS_OF_WEEK);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(TIMES);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(TIMES);

    private final JLabel saveLabel = new JLabel();
    private final JButton saveButton = new JButton("Save");
    private final JLabel backLabel = new JLabel();
    private final JButton backButton = new JButton("Back");

    // Data
    public RepeatView(RepeatViewModel repeatViewModel) {
        this.repeatViewModel = repeatViewModel;

        repeatViewModel.addPropertyChangeListener(this);

        // Create the fixed frame (main)
        final JFrame eventFrame = new JFrame("Create Event Page");
        eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventFrame.setSize(DIMENSION_500, DIMENSION_500);
        eventFrame.setLayout(new BoxLayout(eventFrame.getContentPane(), BoxLayout.Y_AXIS));

        // LAYOUT
        final JPanel eventNamePanel = createPanel("Event Name:", eventNameField);
        final JPanel eventTypePanel = createPanel("Event Type:", eventTypeComboBox);
        final JPanel dayStartPanel = createPanel("Day Start:", dayStartComboBox);
        final JPanel dayEndPanel = createPanel("Day End:", dayEndComboBox);
        final JPanel timeStartPanel = createPanel("Time Start:", timeStartComboBox);
        final JPanel timeEndPanel = createPanel("Time End:", timeEndComboBox);

        final JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        final ArrayList<JCheckBox> checkBoxes = new ArrayList<>();

        // Create a checkbox for each option and add it to the panel
        for (String option : DAYS_OF_WEEK) {
            final JCheckBox checkBox = new JCheckBox(option);
            checkBoxes.add(checkBox);
            checkboxPanel.add(checkBox);
        }

        final JPanel repeatDaysPanel = createPanel("Repeat Days:", checkboxPanel);

        // Save + Back Button
        final JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.add(saveLabel);
        savePanel.add(backButton);
        savePanel.add(backLabel);

        // Add panels to frame
        eventFrame.add(eventNamePanel);
        eventFrame.add(eventTypePanel);
        eventFrame.add(dayStartPanel);
        eventFrame.add(dayEndPanel);
        eventFrame.add(timeStartPanel);
        eventFrame.add(timeEndPanel);
        eventFrame.add(repeatDaysPanel);
        eventFrame.add(savePanel);

        // Display the frame
        eventFrame.setVisible(true);

        // Action Listener for Back
        backButton.addActionListener(
                evt -> {
                    backLabel.setText("Pressed!");
                    repeatController.backToMainView();
                }
        );

        // ActionListener for save button
        saveButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(saveButton)) {
                        final ArrayList<String> selectedItems = new ArrayList<>();
                        for (JCheckBox checkBox : checkBoxes) {
                            if (checkBox.isSelected()) {
                                selectedItems.add(checkBox.getText());
                            }
                        }

                        final RepeatState currentState = repeatViewModel.getState();
                        currentState.setEventName(eventNameField.getText());
                        currentState.setDayStart(dayStartComboBox.getSelectedItem().toString());
                        currentState.setDayEnd(dayEndComboBox.getSelectedItem().toString());
                        currentState.setTimeStart(timeStartComboBox.getSelectedItem().toString());
                        currentState.setTimeEnd(timeEndComboBox.getSelectedItem().toString());
                        currentState.setDaysRepeated(selectedItems);

                        repeatController.execute(currentState.getEventName(),
                                currentState.getDayStart(),
                                currentState.getDayEnd(),
                                currentState.getTimeStart(),
                                currentState.getTimeEnd(),
                                currentState.getDaysRepeated());
                    }
                }
        );
    }

    public String getViewName() {
        return viewName;
    }

    private JPanel createPanel(String label, JComponent component) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JOptionPane.showMessageDialog(this, "Cancel not implemented yet.");
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final RepeatState state = (RepeatState) evt.getNewValue();
        if (state.getRepeatError() != null) {
            JOptionPane.showMessageDialog(this, state.getRepeatError());
        }
    }

    public void setRepeatController(RepeatController controller) {
        this.repeatController = controller;
    }
}
