package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;
import org.jetbrains.annotations.NotNull;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class AddView extends JPanel implements PropertyChangeListener {
    private final String viewName = "edit";
    private final EditViewModel editViewModel;

    private EditController editController;

    // Setup Components
    private final String[] eventTypes = {"Fixed", "Repeat"};
    private final String[] daysOfWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
    private final String[] times = {"12:00 AM", "1:00 AM", "2:00 AM", "3:00 AM", "4:00 AM", "5:00 AM", "6:00 AM",
            "7:00 AM", "8:00 AM", "9:00 AM", "10:00 AM", "11:00 AM", "12:00 PM", "1:00 PM",
            "2:00 PM", "3:00 PM", "4:00 PM", "5:00 PM", "6:00 PM", "7:00 PM", "8:00 PM",
            "9:00 PM", "10:00 PM", "11:00 PM"};

    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);
    private final JLabel saveLabel = new JLabel();
    private final JButton saveButton = new JButton("Save");

    private final JLabel successLabel = new JLabel();

    // Data

    public AddView(EditViewModel editViewModel) {

        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);

        final JFrame eventFrame = setupUI();
        eventFrame.setVisible(true);

        // ActionListener for save button
        saveButton.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        if (evt.getSource().equals(saveButton)) {
                            final EditState currentState = editViewModel.getState();

                            editController.execute(currentState.getEventName(), currentState.getEventType(),
                                    currentState.getDayStart(), currentState.getDayEnd(), currentState.getTimeStart(),
                                                            currentState.getTimeEnd(), currentState.getDaysRepeated());
                        }
                    }
                }
        );
    }

    private @NotNull JFrame setupUI() {
        // Create the fixed frame (main)
        final JFrame eventFrame = new JFrame("Create Event Page");
        eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventFrame.setSize(500, 500);
        eventFrame.setLayout(new BoxLayout(eventFrame.getContentPane(), BoxLayout.Y_AXIS));

        // LAYOUT
        final JPanel eventNamePanel = createPanel("Event Name:", eventNameField);
        final JPanel eventTypePanel = createPanel("Event Type:", eventTypeComboBox);
        final JPanel dayStartPanel = createPanel("Day Start:", dayStartComboBox);
        final JPanel dayEndPanel = createPanel("Day End:", dayEndComboBox);
        final JPanel timeStartPanel = createPanel("Time Start:", timeStartComboBox);
        final JPanel timeEndPanel = createPanel("Time End:", timeEndComboBox);

        // Save Button
        final JPanel savePanel = new JPanel();
        savePanel.add(saveLabel);
        savePanel.add(saveButton);
        savePanel.add(successLabel);

        // Add panels to frame
        eventFrame.add(eventNamePanel);
        eventFrame.add(eventTypePanel);
        eventFrame.add(dayStartPanel);
        eventFrame.add(dayEndPanel);
        eventFrame.add(timeStartPanel);
        eventFrame.add(timeEndPanel);
        eventFrame.add(savePanel);
        return eventFrame;
    }

    private JPanel createPanel(String label, JComponent component) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final EditState state = (EditState) evt.getNewValue();
        setFields(state);
    }

    private void setFields(EditState state) {
        successLabel.setText("success!");
    }

    public String getViewName() {
        return viewName;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

}