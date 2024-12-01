package view;

import static interface_adapter.edit.EditViewModel.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;
import interface_adapter.eventInformation.EventInformationState;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class EditView extends JPanel implements PropertyChangeListener {
    private final String viewName = "edit";
    private final EditViewModel editViewModel;

    private EditController editController;

    private JFrame eventFrame;
    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);
    private final JButton updateButton = new JButton("Update");

    private final ArrayList<JCheckBox> checkBoxes = initialiseCheckBoxes();

    // Data
    public EditView(EditViewModel editViewModel) {

        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);

        // ActionListener for save button
        updateButton.addActionListener(
            evt -> {
                if (evt.getSource().equals(updateButton)) {
                    final ArrayList<String> selectedItems = new ArrayList<>();
                    for (JCheckBox checkBox : checkBoxes) {
                        if (checkBox.isSelected()) {
                            selectedItems.add(checkBox.getText());
                        }
                    }

                    final EditState currentState = editViewModel.getState();

                    editController.execute(currentState.getEventName(), currentState.getEventType(),
                            currentState.getDayStart(), currentState.getDayEnd(), currentState.getTimeStart(),
                            currentState.getTimeEnd(), currentState.getDaysRepeated());
                }
            }
        );
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("edit")) {
            final EditState state = (EditState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, state.getOutputMessage());
        }
        else if (evt.getPropertyName().equals("eventInformation")) {
            final EventInformationState state = (EventInformationState) evt.getNewValue();
            setFields(state);
            setupUi(state.getEventType());
        }
        eventFrame.setVisible(true);
    }

    /**
     * Setting each of the fields with current event information.
     * @param state   the state from which to get the information from (must be EventInformationState)
     */
    public void setFields(EventInformationState state) {
        eventNameField.setText(state.getEventName());
        eventTypeComboBox.setSelectedItem(state.getEventType());
        dayStartComboBox.setSelectedItem(state.getDayStart());
        dayEndComboBox.setSelectedItem(state.getDayEnd());
        timeStartComboBox.setSelectedItem(state.getTimeStart());
        timeEndComboBox.setSelectedItem(state.getTimeEnd());

        if ("Repeat".equals(state.getEventType())) {
            for (JCheckBox checkBox : checkBoxes) {
                final String checkBoxName = checkBox.getText();
                if (state.getDaysRepeated().contains(checkBoxName)) {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private JFrame setupUi(String eventType) {
        // Create the fixed frame (main)
        eventFrame = new JFrame("Create Event Page");
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

        // Update Button
        final JPanel updatePanel = new JPanel();
        updatePanel.add(updateButton);

        // checkbox panel
        if ("Repeat".equals(eventType)) {
            final JPanel checkboxPanel = new JPanel();
            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

            for (JCheckBox checkBox : checkBoxes) {
                checkboxPanel.add(checkBox);
            }
            final JPanel repeatDaysPanel = createPanel("Repeat Days:", checkboxPanel);
            eventFrame.add(repeatDaysPanel);
        }

        // Add panels to frame
        eventFrame.add(eventNamePanel);
        eventFrame.add(eventTypePanel);
        eventFrame.add(dayStartPanel);
        eventFrame.add(dayEndPanel);
        eventFrame.add(timeStartPanel);
        eventFrame.add(timeEndPanel);
        eventFrame.add(updatePanel);
        return eventFrame;
    }

    private JPanel createPanel(String label, JComponent component) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    public String getViewName() {
        return viewName;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    /**
     * Initialise checkboxes with labels of daysOfWeek.
     * @return the list of checkboxes
     */
    private ArrayList<JCheckBox> initialiseCheckBoxes() {
        final ArrayList<JCheckBox> newCheckBoxes = new ArrayList<>();
        for (String day : daysOfWeek) {
            final JCheckBox checkBox = new JCheckBox(day);
            newCheckBoxes.add(checkBox);
        }
        return newCheckBoxes;
    }
}
