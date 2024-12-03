package view;

import static interface_adapter.edit.EditViewModel.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;

/**
 * Represents the user interface for editing an event.
 * The view displays event details and allows the user to modify and save updates.
 */
public class EditView extends JPanel implements PropertyChangeListener {

    /**
     * Unique identifier for this view.
     */
    private final String viewName = "edit";

    /**
     * ViewModel associated with the edit view.
     */
    private final EditViewModel editViewModel;

    /**
     * Controller for handling edit-related actions.
     */
    private EditController editController;

    /**
     * Main frame for the event editing UI.
     */
    private JFrame eventFrame;

    /**
     * Input fields and UI components for event attributes.
     */
    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);
    private final JButton updateButton = new JButton("Update");

    /**
     * Checkboxes for selecting repeated days.
     */
    private final ArrayList<JCheckBox> checkBoxesMain = initialiseCheckBoxes();

    /**
     * Constructs the EditView with the associated ViewModel.
     *
     * @param editViewModel The ViewModel for the edit view.
     */
    public EditView(EditViewModel editViewModel) {
        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);
        initializeUI();
        addEventListeners();
    }

    /**
     * Initializes the main UI components for the edit view.
     */
    private void initializeUI() {
        eventFrame = new JFrame("Edit Event");
        eventFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        eventFrame.setSize(500, 500);
        eventFrame.setLayout(new BoxLayout(eventFrame.getContentPane(), BoxLayout.Y_AXIS));

        eventFrame.add(createPanel("Event Name:", eventNameField));
        eventFrame.add(createPanel("Event Type:", eventTypeComboBox));
        eventFrame.add(createPanel("Day Start:", dayStartComboBox));
        eventFrame.add(createPanel("Day End:", dayEndComboBox));
        eventFrame.add(createPanel("Time Start:", timeStartComboBox));
        eventFrame.add(createPanel("Time End:", timeEndComboBox));
        eventFrame.add(createCheckboxPanel());
        eventFrame.add(createUpdatePanel());
    }

    /**
     * Adds event listeners to UI components.
     */
    private void addEventListeners() {
        updateButton.addActionListener(evt -> {
            List<String> selectedDays = new ArrayList<>();
            for (JCheckBox checkBox : checkBoxesMain) {
                if (checkBox.isSelected()) {
                    selectedDays.add(checkBox.getText());
                }
            }
            editController.execute(
                    eventNameField.getText(),
                    (String) eventTypeComboBox.getSelectedItem(),
                    (String) dayStartComboBox.getSelectedItem(),
                    (String) dayEndComboBox.getSelectedItem(),
                    (String) timeStartComboBox.getSelectedItem(),
                    (String) timeEndComboBox.getSelectedItem(),
                    selectedDays
            );
        });
    }

    /**
     * Handles property changes from the ViewModel.
     *
     * @param evt The property change event.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("edit".equals(evt.getPropertyName())) {
            EditState state = (EditState) evt.getNewValue();
            if (state.getOutputMessage() != null) {
                JOptionPane.showMessageDialog(eventFrame, state.getOutputMessage());
            }
            populateFields(state);
        }
    }

    /**
     * Populates UI fields based on the provided state.
     *
     * @param state The current state containing event details.
     */
    public void populateFields(EditState state) {
        eventNameField.setText(state.getEventName());
        eventTypeComboBox.setSelectedItem(state.getEventType());
        dayStartComboBox.setSelectedItem(state.getDayStart());
        dayEndComboBox.setSelectedItem(state.getDayEnd());
        timeStartComboBox.setSelectedItem(state.getTimeStart());
        timeEndComboBox.setSelectedItem(state.getTimeEnd());

        for (JCheckBox checkBox : checkBoxesMain) {
            checkBox.setSelected(state.getDaysRepeated().contains(checkBox.getText()));
        }
    }

    /**
     * Creates a labeled panel for a specific UI component.
     *
     * @param label The label for the component.
     * @param component The UI component to include.
     * @return A JPanel containing the label and component.
     */
    private JPanel createPanel(String label, JComponent component) {
        JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
    }

    /**
     * Creates the checkbox panel for selecting repeated days.
     *
     * @return The JPanel containing checkboxes for repeated days.
     */
    private JPanel createCheckboxPanel() {
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        for (JCheckBox checkBox : checkBoxesMain) {
            checkboxPanel.add(checkBox);
        }
        return createPanel("Repeat Days:", checkboxPanel);
    }

    /**
     * Creates the update button panel.
     *
     * @return The JPanel containing the update button.
     */
    private JPanel createUpdatePanel() {
        JPanel updatePanel = new JPanel();
        updatePanel.add(updateButton);
        return updatePanel;
    }

    /**
     * Initializes checkboxes for the days of the week.
     *
     * @return A list of checkboxes for each day of the week.
     */
    private ArrayList<JCheckBox> initialiseCheckBoxes() {
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for (String day : daysOfWeek) {
            checkBoxes.add(new JCheckBox(day));
        }
        return checkBoxes;
    }

    /**
     * Returns the unique name of this view.
     *
     * @return The name of this view.
     */
    public String getViewName() {
        return viewName;
    }

    /**
     * Sets the controller for the edit view.
     *
     * @param editController The controller to handle edit actions.
     */
    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    /**
     * Displays the edit view.
     */
    public void showView() {
        eventFrame.setVisible(true);
    }

    /**
     * Hides the edit view.
     */
    public void hideView() {
        eventFrame.setVisible(false);
    }

    /**
     * Main method for standalone testing of the EditView.
     */
    public static void main(String[] args) {
        EditViewModel editViewModel = new EditViewModel();
        EditState editState = new EditState();

        // Set the fields of EditState
        editState.setEventName("Test Event");
        editState.setEventType("Repeat");
        editState.setDayStart("MONDAY");
        editState.setDayEnd("TUESDAY");
        editState.setTimeStart("12:00");
        editState.setTimeEnd("14:00");
        editState.setDaysRepeated(List.of("MONDAY", "FRIDAY"));
        editState.setOutputMessage("Edit event initialized.");

        // Assign the state to the ViewModel
        editViewModel.setState(editState);

        // Display the EditView
        JFrame frame = new JFrame("Test Edit View");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setContentPane(new EditView(editViewModel));
        frame.setVisible(true);
    }



}
