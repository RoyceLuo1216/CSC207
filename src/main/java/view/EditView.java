package view;

import static interface_adapter.edit.EditViewModel.*;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

import javax.swing.*;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import interface_adapter.edit.EditController;
import interface_adapter.edit.EditState;
import interface_adapter.edit.EditViewModel;

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

    private final ArrayList<JCheckBox> checkBoxesMain = initialiseCheckBoxes();

    public EditView(EditViewModel editViewModel) {
        System.out.println("EditView Constructor called");

        this.editViewModel = editViewModel;
        this.editViewModel.addPropertyChangeListener(this);

        setupUi();
        setupListeners();

    }

    /**
     * Sets up te listeners for the edit view.
     */
    private void setupListeners() {
        // Listener for the Update button
        updateButton.addActionListener(evt -> {
            final ArrayList<String> selectedDays = new ArrayList<>();
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
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("Property changed: " + evt.getPropertyName());
        if ("edit".equals(evt.getPropertyName())) {
            EditState state = (EditState) evt.getNewValue();
            System.out.println("EditState received: " + state);
            if (state.getOutputMessage() != null) {
                JOptionPane.showMessageDialog(eventFrame, state.getOutputMessage());
            }
            setFields(state);
            showView();
        }
    }

    /**
     * Setting each of the fields with current event information.
     * @param state   the state from which to get the information from (must be EventInformationState)
     */
    /**
     * Setting each of the fields with current event information.
     * @param state the state from which to get the information from (must be EditState)
     */
    public void setFields(EditState state) {
        eventNameField.setText(state.getEventName());
        eventTypeComboBox.setSelectedItem(state.getEventType());
        dayStartComboBox.setSelectedItem(state.getDayStart());
        dayEndComboBox.setSelectedItem(state.getDayEnd());
        timeStartComboBox.setSelectedItem(state.getTimeStart());
        timeEndComboBox.setSelectedItem(state.getTimeEnd());

        // Update checkboxes for repeat days
        for (JCheckBox checkBox : checkBoxesMain) {
            checkBox.setSelected(state.getDaysRepeated().contains(checkBox.getText()));
        }
    }


    private JFrame setupUi() {
        // Create the fixed frame (main)
        System.out.println("EditView setupUi called");

        eventFrame = new JFrame("Event Page");

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

//        // checkbox panel
//        if ("Repeat".equals(eventType)) {
//            final JPanel checkboxPanel = new JPanel();
//            checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
//
//            for (JCheckBox checkBox : checkBoxesMain) {
//                checkboxPanel.add(checkBox);
//            }
//            final JPanel repeatDaysPanel = createPanel("Repeat Days:", checkboxPanel);
//            eventFrame.add(repeatDaysPanel);
//        }

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
  
    /**
     * Initialise checkboxes with labels of daysOfWeek.
     * @return the list of checkboxes
     */
    private ArrayList<JCheckBox> initialiseCheckBoxes() {
        ArrayList<JCheckBox> checkBoxes = new ArrayList<>();
        for (String day : daysOfWeek) {
            checkBoxes.add(new JCheckBox(day));
        }
        return checkBoxes;
    }

    private JPanel createCheckboxPanel() {
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));

        for (JCheckBox checkBox : checkBoxesMain) {
            checkboxPanel.add(checkBox);
        }
        return checkboxPanel;
    }

    /**
     * Creats the panel for the updates button.
     * @return JPanel to build on.
     */
    private JPanel createUpdatePanel() {
        final JPanel updatePanel = new JPanel();
        updatePanel.add(updateButton);
        return updatePanel;
    }

    public String getViewName() {
        return viewName;
    }

    public void setEditController (EditController editController) {
        this.editController = editController;
    }

    /**
     * shows view.
     */
    public void showView() {
        eventFrame.setVisible(true);
    }

    /**
     * Hides view.
     */
    public void hideView() {
        eventFrame.setVisible(false);
    }

}
