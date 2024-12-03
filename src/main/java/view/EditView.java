package view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import interface_adapter.delete.DeleteEventController;
import interface_adapter.delete.DeleteEventState;
import interface_adapter.delete.DeleteEventViewModel;
import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;
import interface_adapter.eventInformation.EventInformationState;

/**
 * The View for when the user is adding an event (i.e. its details) into the program.
 */
public class EditView extends JPanel implements PropertyChangeListener {
    private static final int DIMENSION_20 = 20;
    private static final int DIMENSION_500 = 500;

    private final String viewName = "edit";
    private final EditViewModel editViewModel;
    private final DeleteEventViewModel deleteEventViewModel;

    private EditController editController;
    private DeleteEventController deleteEventController;


    private JFrame eventFrame;

    private final String[] eventTypes = {"Fixed", "Repeat"};
    private final String[] daysOfWeek = {"MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY"};
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
    private final JButton updateButton = new JButton("Update");
    private final JButton deleteButton = new JButton("Delete");

    private final ArrayList<JCheckBox> checkBoxes = initialiseCheckBoxes();

    public EditView(EditViewModel editViewModel, DeleteEventViewModel deleteEventViewModel) {

        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);
        this.deleteEventViewModel = deleteEventViewModel;
        this.deleteEventViewModel.addPropertyChangeListener(this);

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

        // ActionListener for delete button
        deleteButton.addActionListener(
                evt -> {
                    if (evt.getSource().equals(deleteButton)) {
                        final DeleteEventState currentState = deleteEventViewModel.getState();

                        deleteEventController.execute(currentState.getEventName());
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
        else if (evt.getPropertyName().equals("delete")) {
            final DeleteEventState state = (DeleteEventState) evt.getNewValue();
            JOptionPane.showMessageDialog(null, state.getMessage());
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
        eventFrame = new JFrame("Event Page");

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

        // Side by side day and time
        final JPanel dayPanel = new JPanel();
        dayPanel.setLayout(new BoxLayout(dayPanel, BoxLayout.Y_AXIS));
        dayPanel.add(dayStartPanel);
        dayPanel.add(dayEndPanel);

        final JPanel timePanel = new JPanel();
        timePanel.setLayout(new BoxLayout(timePanel, BoxLayout.Y_AXIS));
        timePanel.add(timeStartPanel);
        timePanel.add(timeEndPanel);

        final JPanel dayTimePanel = new JPanel();
        dayTimePanel.setLayout(new BoxLayout(dayTimePanel, BoxLayout.X_AXIS));
        dayTimePanel.add(dayPanel);
        dayTimePanel.add(timePanel);

        // Update Button
        final JPanel updatePanel = new JPanel();
        updatePanel.add(updateButton);

        // Delete Button
        final JPanel deletePanel = new JPanel();
        deletePanel.add(deleteButton);

        // Buttons
        final JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.X_AXIS));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, DIMENSION_20, 0));
        buttonsPanel.add(updateButton);
        buttonsPanel.add(Box.createRigidArea(new Dimension(DIMENSION_20, 0)));
        buttonsPanel.add(deleteButton);

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
        // Side by side Day and Time
        // eventFrame.add(dayTimePanel);
        eventFrame.add(buttonsPanel);
        return eventFrame;
    }

    private JPanel createPanel(String label, JComponent component) {
        final JPanel panel = new JPanel();
        panel.add(new JLabel(label));
        panel.add(component);
        return panel;
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

    public String getViewName() {
        return viewName;
    }

    public void setEditController(EditController editController) {
        this.editController = editController;
    }

    public void setDeleteController(DeleteEventController deleteController) {
        this.deleteEventController = deleteController;
    }

//    /**
//     * Main function for seeing the view layout
//     * @param args
//     */
//    public static void main(String[] args) {
//        // Create an instance of EditViewModel with a dummy "edit" state
//        EditViewModel editViewModel = new EditViewModel("edit");
//        DeleteEventViewModel deleteViewModel = new DeleteEventViewModel("delete");
//
//        // Create an instance of EditView
//        EditView editView = new EditView(editViewModel, deleteViewModel);
//
//        // Setup the UI with a dummy event type (e.g., "Repeat" or "Fixed")
//        JFrame frame = editView.setupUi("Fixed");
//
//        // Make the frame visible
//        frame.setVisible(true);
//    }

}
