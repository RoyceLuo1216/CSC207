package view;

import static interface_adapter.eventAdd.EventAddViewModel.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.*;

import interface_adapter.eventAdd.EventAddController;
import interface_adapter.eventAdd.EventAddState;
import interface_adapter.eventAdd.EventAddViewModel;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class EventAddView extends JPanel implements ActionListener, PropertyChangeListener {
    // Initialise the controller

    private static final int DIMENSION_500 = 500;

    private final String viewName = "event";
    private final EventAddViewModel eventAddViewModel;
    private EventAddController eventController;

    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(DAYS_OF_WEEK);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(DAYS_OF_WEEK);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(TIMES);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(TIMES);

    private final JLabel saveLabel = new JLabel();
    private final JButton saveButton = new JButton("Save");
    private final JLabel backLabel = new JLabel();
    private final JButton backButton = new JButton("Back");

    // Data
    public EventAddView(EventAddViewModel eventAddViewModel) {
        this.eventAddViewModel = eventAddViewModel;
        eventAddViewModel.addPropertyChangeListener(this);

        // Create the fixed frame (main)
        final JFrame eventFrame = new JFrame("Create Event Page");
        eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventFrame.setSize(DIMENSION_500, DIMENSION_500);
        eventFrame.setLayout(new BoxLayout(eventFrame.getContentPane(), BoxLayout.Y_AXIS));

        // LAYOUT
        final JPanel eventNamePanel = createPanel("Event Name:", eventNameField);
        final JPanel dayStartPanel = createPanel("Day Start:", dayStartComboBox);
        final JPanel dayEndPanel = createPanel("Day End:", dayEndComboBox);
        final JPanel timeStartPanel = createPanel("Time Start:", timeStartComboBox);
        final JPanel timeEndPanel = createPanel("Time End:", timeEndComboBox);

        // Save + Back Button
        final JPanel savePanel = new JPanel();
        savePanel.add(saveButton);
        savePanel.add(saveLabel);
        savePanel.add(backButton);
        savePanel.add(backLabel);

        // Add panels to frame
        eventFrame.add(eventNamePanel);
        eventFrame.add(dayStartPanel);
        eventFrame.add(dayEndPanel);
        eventFrame.add(timeStartPanel);
        eventFrame.add(timeEndPanel);
        eventFrame.add(savePanel);

        // Display the frame
        eventFrame.setVisible(true);

        // Action Listener for Back
        backButton.addActionListener(
                evt -> {
                    backLabel.setText("Pressed!");
                    eventController.backToMainView();
                }
        );

        // ActionListener for save button
        saveButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(saveButton)) {
                        final EventAddState currentState = eventAddViewModel.getState();
                        currentState.setEventName(eventNameField.getText());
                        currentState.setDayStart(dayStartComboBox.getSelectedItem().toString());
                        currentState.setDayEnd(dayEndComboBox.getSelectedItem().toString());
                        currentState.setTimeStart(timeStartComboBox.getSelectedItem().toString());
                        currentState.setTimeEnd(timeEndComboBox.getSelectedItem().toString());

                        eventController.execute(currentState.getEventName(),
                                currentState.getDayStart(),
                                currentState.getDayEnd(),
                                currentState.getTimeStart(),
                                currentState.getTimeEnd());
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
        final EventAddState state = (EventAddState) evt.getNewValue();
        if (state.getEventError() != null) {
            JOptionPane.showMessageDialog(this, state.getEventError());
        }
    }

    public void setEventController(EventAddController controller) {
        this.eventController = controller;
    }
}
