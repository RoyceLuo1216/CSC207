package view;

import static interface_adapter.edit.EditViewModel.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

import interface_adapter.delete.DeleteEventState;
import interface_adapter.delete.DeleteEventViewModel;
import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;
import interface_adapter.delete.DeleteEventController;

/**
 * Represents the user interface for editing an event.
 * The view displays event details and allows the user to modify and save updates.
 */
public class EditView extends JPanel implements PropertyChangeListener {

    private final String viewName = "edit";
    private final EditViewModel editViewModel;
    private EditController editController;

    private final DeleteEventViewModel deleteEventViewModel;
    private DeleteEventController deleteEventController;

    private final JTextField eventNameField = new JTextField(20);
    private final JComboBox<String> eventTypeComboBox = new JComboBox<>(eventTypes);
    private final JComboBox<String> dayStartComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> dayEndComboBox = new JComboBox<>(daysOfWeek);
    private final JComboBox<String> timeStartComboBox = new JComboBox<>(times);
    private final JComboBox<String> timeEndComboBox = new JComboBox<>(times);
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");

    private final ArrayList<JCheckBox> checkBoxesMain = initialiseCheckBoxes();

    /**
     * Constructs the EditView with the associated ViewModel.
     *
     * @param editViewModel        The ViewModel for the edit view.
     * @param deleteEventViewModel
     */
    public EditView(EditViewModel editViewModel, DeleteEventViewModel deleteEventViewModel) {
        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);
        this.deleteEventViewModel = deleteEventViewModel;
        this.deleteEventViewModel.addPropertyChangeListener(this);
        initializeUI();
        addEventListeners();
    }

    /**
     * Initializes the main UI components for the edit view.
     */
    private void initializeUI() {
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        this.add(createPanel("Event Name:", eventNameField));
        this.add(createPanel("Event Type:", eventTypeComboBox));
        this.add(createPanel("Day Start:", dayStartComboBox));
        this.add(createPanel("Day End:", dayEndComboBox));
        this.add(createPanel("Time Start:", timeStartComboBox));
        this.add(createPanel("Time End:", timeEndComboBox));
        this.add(createCheckboxPanel());
        this.add(createActionPanel());
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
            editController.scheduleView();
        });

        // ActionListener for delete button
        deleteButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(deleteButton)) {
                        System.out.println("swapping to delete view from edit.");
                        editController.deleteView();
                    }
                }
        );
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
                JOptionPane.showMessageDialog(this, state.getOutputMessage());
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
     * Creates the action panel containing update and delete buttons.
     *
     * @return The JPanel containing the action buttons.
     */
    private JPanel createActionPanel() {
        JPanel actionPanel = new JPanel();
        actionPanel.add(updateButton);
        actionPanel.add(deleteButton);
        return actionPanel;
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

}